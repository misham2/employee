package com.jdbctemplate.employee.service;

import com.jdbctemplate.employee.Repo.UserSigninRepository;
import com.jdbctemplate.employee.entity.UserSignin;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface UserSigninService {

    void save(UserSignin userSignin);
    List<UserSignin> findAll();


}
