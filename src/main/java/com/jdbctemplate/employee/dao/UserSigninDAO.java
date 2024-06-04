package com.jdbctemplate.employee.dao;

import com.jdbctemplate.employee.entity.UserSignin;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserSigninDAO {
    void save(UserSignin userSignin);
    List<UserSignin> findAll();
}
