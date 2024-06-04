package com.jdbctemplate.employee.service;

import com.jdbctemplate.employee.Repo.UserSigninRepository;
import com.jdbctemplate.employee.dao.ManagerDao;
import com.jdbctemplate.employee.entity.*;
import com.jdbctemplate.employee.model.ManagerModel;
import com.jdbctemplate.employee.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
public class ManagerService
{

    @Autowired
    ManagerDao dao;
    @Autowired
    ManagerModel managerModel;
    @Autowired
    JdbcTemplate jdbcTemplate;


    @Autowired
    private UserSigninRepository userSigninRepository;


    @Autowired
    JwtUtils jwtUtils;



    public void signupUser(String userName,String userEmail,String userPassword)
    {
        User user = new User();
        user.setUserName(userName);
        user.setUserEmail(userEmail);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPwd = encoder.encode(userPassword);
        user.setUserPassword(encodedPwd);
        dao.saveUser(user);
    }

    public User saveUser(User manager)
    {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPwd = encoder.encode(manager.getUserPassword());
        manager.setUserPassword(encodedPwd);
        dao.saveUser(manager);
        return manager;
    }



    public ResponseEntity<User> findUserById(int userId) {
        User user = dao.findUserById(userId);

        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    public User AuthenticateManager(User user)
    {
        BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
        User exManager = dao.findUserById(user.getUserId());
        if(exManager != null) {
            if (bCrypt.matches(user.getUserPassword(),exManager.getUserPassword()))
            {
                return exManager;
            }
        }
        return null;
    }

    public boolean AuthenticateManagerPassword(String userEmail, String userPassword) {
        List<User> managers =dao.findAll();

        for (User user : managers) {
            if (user.getUserEmail().equals(userEmail)) {
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                if (encoder.matches(userPassword, user.getUserPassword())) {
                    return true;
                }
            }
        }
        return false;
    }

//    public User findUserByEmail(String userEmail)
//    {
//        return dao.findUserByEmail(userEmail);
//    }


//    public boolean login(String userEmail, String userPassword) {
//        User user = dao.findUserByEmail(userEmail);
//        if (user != null && user.getUserPassword().equals(userPassword)) {
//            return true;
//        }
//        return false;
//    }

    public User findByEmail(String userEmail) {
        return managerModel.findByEmail(userEmail);
    }

    public void saveUserSignIn(int userId, LocalDateTime signInTime) {
        UserSignin userSignin = new UserSignin();
        userSignin.setUserId(userId);
        userSignin.setSigninTime(signInTime);
        userSigninRepository.save(userSignin);
    }

    public void saveEmployee(Employee employee) {
        dao.saveEmployee(employee);
    }

    public List<Employee> getAllEmployees() {
        return dao.getAllEmployees();
    }







}
