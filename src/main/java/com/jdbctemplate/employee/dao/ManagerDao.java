package com.jdbctemplate.employee.dao;

import com.jdbctemplate.employee.entity.*;
import com.jdbctemplate.employee.model.ManagerModel;
import com.jdbctemplate.employee.util.ManagerRowMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;



@Repository
public class ManagerDao implements ManagerModel
{
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public User saveUser(User manager)
    {
        String query = "insert into user (user_name,user_email,user_password) values (?,?,?)";
        jdbcTemplate.update(query,manager.getUserName(),manager.getUserEmail(),manager.getUserPassword());
        return manager;
    }

    @Override
    public User findUserById(int userId) {
        String query = "select * from user where user_id=?";
        return jdbcTemplate.queryForObject(query,new Object[]{userId},new ManagerRowMapper());
    }

    @Override
    public List<User> findAll() {
        String query = "select * from user";
        return jdbcTemplate.query(query,new ManagerRowMapper());
    }

    @Override
    public void updateUser(User user, int userId)
    {
        String query = "update user set user_name=?,user_email=?,user_password=? where user_id=?";
        jdbcTemplate.update(query,user.getUserName(),user.getUserEmail(),user.getUserPassword(),userId);
    }

    @Override
    public void deleteUser(int userId)
    {
        String query = "delete from user where user_id=?";
        jdbcTemplate.update(query,userId);
    }


    public User findUserByEmail(String userEmail) {
        String query = "select * from user where user_email=?";
        return jdbcTemplate.queryForObject(query,new Object[]{userEmail},new ManagerRowMapper());
    }

    public boolean isUserExists(String userName) {
        String sql = "SELECT COUNT(*) FROM user WHERE user_name = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, userName);
        return count > 0;
    }
    public void createUser(User user) {
        String sql = "INSERT INTO user (user_name,user_email, user_password) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, user.getUserName(), user.getUserEmail(),user.getUserPassword());
    }



    @Override
    public User findByEmail(String userEmail) {
        String sql = "SELECT * FROM user WHERE user_email = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{userEmail}, (rs, rowNum) -> {
            User user = new User();
            user.setUserName(rs.getString("user_name"));
            user.setUserEmail(rs.getString("user_email"));
            user.setUserPassword(rs.getString("user_password"));
            user.setUserId(rs.getInt("user_id"));
            return user;
        });
    }

    @Override
    public void saveEmployee(Employee employee) {
        String sql = "INSERT INTO employee (name, location, about, designation, department) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, employee.getName(), employee.getLocation(), employee.getAbout(),
                employee.getDesignation(), employee.getDepartment());
    }

    @Override
    public List<Employee> getAllEmployees() {
        String sql = "SELECT * FROM employee";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Employee employee = new Employee();
            employee.setEmployee_id(rs.getInt("employee_id"));
            employee.setName(rs.getString("name"));
            employee.setLocation(rs.getString("location"));
            employee.setAbout(rs.getString("about"));
            employee.setDesignation(rs.getString("designation"));
            employee.setDepartment(rs.getString("department"));
            employee.setImage(rs.getString("image"));
            return employee;
        });
    }

    @Override
    public void updateEmployee(Employee employee) {
        String sql = "UPDATE employee SET name=?, location=?, About=?, Designation=?, Department=?, image=? " +
                "WHERE employee_id=?";
        jdbcTemplate.update(sql, employee.getName(), employee.getLocation(), employee.getAbout(),
                employee.getDesignation(), employee.getDepartment(),employee.getImage(),
                employee.getEmployee_id());
    }



//    public String processImage(byte[] image) {
//        System.out.println("Received image: " + image);
//        return "Image received successfully";
//    }


    public void saveImage(String base64Image) {

        String sql = "UPDATE employee SET image = ? WHERE employee_id = 2";
        jdbcTemplate.update(sql, base64Image);
    }


    @Override
    public void save(Attendance attendance) {
        String sql = "INSERT INTO attendance (timeIn, timeOut,  options, user_id, date) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, attendance.getTimeIn(), attendance.getTimeOut(), attendance.getOptions(),
                attendance.getUserId(), attendance.getDate());
    }
    @Override
    public void saveAllotedLeave(AllotedLeave allotedLeave){
        String Data="INSERT INTO allotedLeave (leave_type, no_of_days) VALUES (?, ?)";
        jdbcTemplate.update(Data,allotedLeave.getLeave_type(),allotedLeave.getNo_of_Days() );
    }

    @Override
    public List<AllotedLeave> getAllotedLeave() {
        String sql = "SELECT * FROM allotedleave";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            AllotedLeave allotedleave = new AllotedLeave();
            allotedleave.setLeave_id(rs.getInt("leave_id"));
            allotedleave.setLeave_type(rs.getString("leave_type"));
            allotedleave.setNo_of_Days(rs.getInt("no_of_Days"));
            return allotedleave;
        });
    }

@Override
    public void saveData(ApplyLeave applyLeave) {
        String query = "SELECT status_id FROM status WHERE status_type = ?";

        Integer id = jdbcTemplate.queryForObject(query, Integer.class, applyLeave.getLeave_status());

        String sql = "INSERT INTO applyleave (user_id, from_date, to_date, leave_type_id, " +
                "approved_by, approved_date, leave_status, created_date, status_id )" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql, applyLeave.getUser_id(), applyLeave.getFrom_date(), applyLeave.getTo_date(), applyLeave.getLeave_type_id(),
                applyLeave.getApproved_by(), applyLeave.getApproved_date(), applyLeave.getLeave_status(), applyLeave.getCreated_date(), id);
    }



    public Employee getLeaveInfoForEmployee(int employeeId) {
        String sql = "SELECT sickLeave, casualLeave, restrictedLeave FROM employee WHERE employee_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{employeeId}, (rs, rowNum) -> {
            Employee leaveInfo = new Employee();
            leaveInfo.setSickLeave(rs.getString("sickLeave"));
            leaveInfo.setCasualLeave(rs.getString("casualLeave"));
            leaveInfo.setRestrictedLeave(rs.getString("restrictedLeave"));
            return leaveInfo;
        });
    }




    public String getLeaveBalance(int userId, String fromDate, String toDate, String date) {

        String leaveTypeQuery = "SELECT leave_type_id FROM applyleave WHERE leave_status = 'Approved' AND user_id = 2";

        int leaveTypeId = jdbcTemplate.queryForObject(leaveTypeQuery, Integer.class, userId);

        LocalDate startDate = LocalDate.parse(fromDate);
        LocalDate endDate = LocalDate.parse(toDate);
        long numberOfDays = endDate.toEpochDay() - startDate.toEpochDay() + 1;

        String updateBalanceQuery = "";
        switch (leaveTypeId) {
            case 1:
                updateBalanceQuery = "UPDATE employee SET sickLeave = sickLeave - numberOfDays WHERE employee_id = 2";
                break;
            case 2:
                updateBalanceQuery = "UPDATE employee SET casualLeave = casualLeave - numberOfDays WHERE employee_id = 2";
                break;
            case 3:
                updateBalanceQuery = "UPDATE employee SET restrictedLeave = restrictedLeave - numberOfDays WHERE employee_id = 2";
                break;
            default:
                break;
        }
        jdbcTemplate.update(updateBalanceQuery, numberOfDays, userId);

        return updateBalanceQuery;
    }



}















//        String balanceQuery = "SELECT * FROM employee WHERE employee_id = ?";
//        Employee employee = jdbcTemplate.queryForObject(balanceQuery, new Object[]{userId}, new BeanPropertyRowMapper<>(Employee.class));
//
//        int balance = 0;
//        switch (leaveType.getLeave_type()) {
//            case "Sick Leave":
//                balance = Integer.parseInt(employee.getSickLeave());
//                break;
//            case "Casual Leave":
//                balance = Integer.parseInt(employee.getCasualLeave());
//                break;
//            case "Restricted Leave":
//                balance = Integer.parseInt(employee.getRestrictedLeave());
//                break;
//            default:
//                break;
//        }