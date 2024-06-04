package com.jdbctemplate.employee.controller;


import com.jdbctemplate.employee.dao.ManagerDao;
import com.jdbctemplate.employee.entity.*;
import com.jdbctemplate.employee.service.ManagerService;
import com.jdbctemplate.employee.service.UserSigninService;
import com.jdbctemplate.employee.service.UserSigninServiceImpl;
import com.jdbctemplate.employee.util.JwtUtils;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/man")
public class ManagerController
{


    @Autowired
    ManagerService service;
    @Autowired
    ManagerDao dao;

    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UserSigninService userSigninService;

    @Autowired
    private UserSigninServiceImpl signInOutService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/authenticate")
    public ResponseEntity privateApi(@RequestHeader(value = "authorization",defaultValue = "123") String auth)
            throws Exception {
//        String authorization = "eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJzYW1AZ21haWwuY29tIiwiaWF0IjoxNzEzMjU2NTQ2LCJleHAiOjE3MTMyNTY2NjYsIm5hbWUiOiJzYW0iLCJlbWFpbCI6InNhbUBnbWFpbC5jb20ifQ.87kbTSmvg6B2y2VBzhxXRfi839Il2cg365Lcln1w7iVtPrrsWlWX3mH_A0TIm6rg4qTxa8t6Dfpgqd-LPovzgA";
        try {
            jwtUtils.verify(auth);
            return ResponseEntity.status(200).body("This is valid Token");
        } catch (SignatureException e) {
            return ResponseEntity.status(401).body("Invalid token signature");
        } catch (ExpiredJwtException e) {
            return ResponseEntity.status(401).body("Token has expired");
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid token");
        }
    }


    @PostMapping("/save")
    public ResponseEntity<User> saveUser(@RequestBody User manager)
    {
        User savedUser = dao.saveUser(manager);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }


    @PostMapping("/token")
    public ResponseEntity<String> generateToken(@RequestBody User manager) {
        String token = jwtUtils.generateJwt(manager);
        if (token != null && !token.isEmpty()) {
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to generate token");
        }
    }


    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody User user) {
        if (dao.isUserExists(user.getUserName())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already exists.");
        }
        dao.createUser(user);
        return ResponseEntity.status(HttpStatus.OK).body("Sign-up successful.");
    }

    @GetMapping("/getByEmail")
    public ResponseEntity<?> getByEmail(@RequestParam String email, @RequestHeader("Authorization") String authorization) {
        try {
            Claims claims = jwtUtils.verify(authorization);

            return ResponseEntity.ok().body("Data for email: " + email);
        } catch (SignatureException | ExpiredJwtException e) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
    }

    @PostMapping("/login1")
    public ResponseEntity<User> login(@RequestBody User user) {
        User existingUser = service.findByEmail(user.getUserEmail());
//        UserSignin userSignin = new UserSignin();
//        userSignin.setUserId(existingUser.getUserId());
//        userSignin.setSigninTime(LocalDateTime.now());
//        service.saveUserSignIn(existingUser.getUserId(),userSignin.getSigninTime());
        if (existingUser == null || !existingUser.getUserPassword().equals(user.getUserPassword())) {
            return null;
        }
        return ResponseEntity.status(HttpStatus.OK).body(existingUser);
    }


//    @PostMapping("/signout")
//    public ResponseEntity<String> signOut(@RequestBody UserSignin userSignin) {
//
//        userSigninService.save(userSignin);
//        User existingUser = userService.findByUserId(userSignin.getUserId());
//        UserSignin signout = new UserSignin();
//        signout.setUserId(existingUser.getUserId());
//        signout.setSignoutTime(LocalDateTime.now());
//        userSigninService.save(signout);
//        service.saveUserSignOut(existingUser.getUserId(), signout.getSignoutTime());
//        return ResponseEntity.status(HttpStatus.CREATED).body("Sign-out successful.");
//    }


//    @PostMapping("/signin")
//    public ResponseEntity<String> signIn() {
//        UserSignin signInOut = new UserSignin();
//        signInOut.setSigninTime(LocalDateTime.now());
//        signInOutService.saveSignInOut(signInOut);
//        return ResponseEntity.status(HttpStatus.CREATED).body("Sign in successful");
//    }
//
//    @PostMapping("/signout")
//    public ResponseEntity<String> signOut() {
//        UserSignin signInOut = new UserSignin();
//        signInOut.setSignoutTime(LocalDateTime.now());
//        signInOutService.saveSignInOut(signInOut);
//        return ResponseEntity.status(HttpStatus.OK).body("Sign out successful");
//    }



    @PostMapping("/signin")
    public ResponseEntity<String> signIn(@RequestBody UserSignin user) {

        String insertQuery = "INSERT INTO user_signin (signin_time, user_id) VALUES (?,?)";
        jdbcTemplate.update(insertQuery, LocalDateTime.now(), user.getUserId());
        return ResponseEntity.status(HttpStatus.OK).body("Sign in successful");
    }

    @PostMapping("/signout")
    public ResponseEntity<String> signOut(@RequestBody UserSignin user) {
        String updateQuery = "UPDATE user_signin SET signout_time=? WHERE user_id=? AND signout_time IS NULL";
        int rowsAffected = jdbcTemplate.update(updateQuery, LocalDateTime.now(), user.getUserId());

        if(rowsAffected > 0) {
            return ResponseEntity.status(HttpStatus.OK).body("Sign out successful");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No active sign-in record found for the user");
        }
    }

    @PostMapping("/Employee")
    public ResponseEntity<String> createEmployee(@RequestBody Employee employee) {
        dao.saveEmployee(employee);
        return new ResponseEntity<>("Employee created successfully", HttpStatus.CREATED);
    }

    @GetMapping("/getEmployee")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        try {
            List<Employee> employees = service.getAllEmployees();
            if (employees.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(employees);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{employeeId}")
    public void updateEmployee(@PathVariable int employeeId, @RequestBody Employee employee) {
        employee.setEmployee_id(employeeId);
       dao.updateEmployee(employee);
    }

//    @PostMapping("/upload")
//    public String processImage(@RequestBody Employee imageData) {
//        if (imageData != null && imageData.getProfile() != null) {
//            System.out.println("Received image data: " + imageData.getProfile());
//            return dao.processImage(imageData.getProfile());
//        } else {
//            return "Image data is null or empty";
//        }
//    }


    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestBody String base64Image) {
        try {
            dao.saveImage(base64Image);
            return ResponseEntity.ok("Image uploaded successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading image: " + e.getMessage());
        }
    }


    @PostMapping("/attendance")
    public ResponseEntity<String> saveAttendance(@RequestBody Attendance attendance) {
        try {
            dao.save(attendance);
            return new ResponseEntity<>("Attendance saved successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to save attendance: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/insertallotedleave")
    public ResponseEntity<String> savealloted(@RequestBody AllotedLeave allotedLeave){
        try {
            dao.saveAllotedLeave(allotedLeave);
            return new ResponseEntity<>("AllotedLeave saved successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to save AllotedLeave: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getallotedLeave")
    public ResponseEntity<List<AllotedLeave>> getAllotedleave() {
        try {
            List<AllotedLeave> allotedLeave = dao.getAllotedLeave();
            if (allotedLeave.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(allotedLeave);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @PostMapping("/leavePost")
    public ResponseEntity<String> saveAttendance(@RequestBody ApplyLeave applyLeave) {
        try {
            dao.saveData(applyLeave);
            return new ResponseEntity<>("Leave saved successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to save Leave: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @GetMapping("/pendingLeavesdata")
    public List<Map<String, Object>> getPendingLeavesForUser(@RequestParam("userId") int userId) {
        String sql = "SELECT * FROM applyleave WHERE user_id = ? AND leave_status = 'Pending'";
        return jdbcTemplate.queryForList(sql, userId);
    }



    @GetMapping("/employeeAllotedLeave")
    public List<Map<String, Object>> getemployeeAllotedLeave(@RequestParam("employee_Id") int employee_id) {
        String sql = "SELECT * FROM employee WHERE employee_id = ? AND leave_status = 'Approved'";
        return jdbcTemplate.queryForList(sql, employee_id);
    }


    @GetMapping("/leaveInfo")
    public Employee getLeaveInfoForEmployee(@RequestParam int employeeId) {
        return dao.getLeaveInfoForEmployee(employeeId);
    }

    @GetMapping("/ApprovedLeavesdata")
    public List<Map<String, Object>> getApprovedLeavesForUser(@RequestParam("userId") int userId) {
        String sql = "SELECT * FROM applyleave WHERE user_id = ? AND leave_status = 'Approved'";
        return jdbcTemplate.queryForList(sql, userId);
    }




    @PostMapping("/applyLeave")
    public ResponseEntity<String> applyLeave(@RequestBody ApplyLeave leaveRequest) {System.out.println("startDate");
        try {
            String response = dao.getLeaveBalance(
                    leaveRequest.getUser_id(),
                    leaveRequest.getLeave_type_id(),
                    leaveRequest.getFrom_date(),
                    leaveRequest.getTo_date()
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing leave request: " + e.getMessage());
        }
    }
    @GetMapping("/balance")
    public ResponseEntity<Integer> getLeaveBalance(@RequestParam int userId, @RequestParam String leaveTypeId ,@RequestParam String fromDate,@RequestParam String toDate) {
        try {
            int balance = Integer.parseInt(dao.getLeaveBalance(userId, leaveTypeId ,fromDate, toDate));
            return ResponseEntity.ok(balance);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(-1);
        }
    }




}

