package com.jdbctemplate.employee.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table (name = "userSignin")
public class UserSignin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    private int userId;

    private LocalDateTime signinTime;
    private LocalDateTime signoutTime;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDateTime getSigninTime() {
        return signinTime;
    }

    public void setSigninTime(LocalDateTime signinTime) {
        this.signinTime = signinTime;
    }

    public LocalDateTime getSignoutTime() {
        return signoutTime;
    }

    public void setSignoutTime(LocalDateTime signoutTime) {
        this.signoutTime = signoutTime;
    }
}
