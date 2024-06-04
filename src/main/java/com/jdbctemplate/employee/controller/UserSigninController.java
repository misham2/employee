package com.jdbctemplate.employee.controller;

import com.jdbctemplate.employee.entity.UserSignin;
import com.jdbctemplate.employee.service.UserSigninService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserSigninController {
    private UserSigninService userSigninService;

    @Autowired
    public UserSigninController(UserSigninService userSigninService) {
        this.userSigninService = userSigninService;
    }

    @PostMapping("/signin")
    public ResponseEntity<String> signIn(@RequestBody UserSignin userSignin) {
        userSigninService.save(userSignin);
        return ResponseEntity.status(HttpStatus.CREATED).body("Sign-in successful.");
    }

    @PostMapping("/signout")
    public ResponseEntity<String> signOut(@RequestBody UserSignin userSignin) {
        userSigninService.save(userSignin);
        return ResponseEntity.status(HttpStatus.CREATED).body("Sign-out successful.");
    }

    @GetMapping("/userSignins")
    public ResponseEntity<List<UserSignin>> getAllUserSignins() {
        List<UserSignin> userSignins = userSigninService.findAll();
        return ResponseEntity.ok(userSignins);
    }
}
