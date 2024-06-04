package com.jdbctemplate.employee.service;

import com.jdbctemplate.employee.Repo.UserSigninRepository;
import com.jdbctemplate.employee.dao.UserSigninDAO;
import com.jdbctemplate.employee.entity.UserSignin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class UserSigninServiceImpl implements UserSigninService {
    @Autowired
    private  UserSigninDAO userSigninDAO;

    public UserSigninServiceImpl(UserSigninDAO userSigninDAO) {
        this.userSigninDAO = userSigninDAO;
    }

    @Override
    public void save(UserSignin userSignin) {
        userSigninDAO.save(userSignin);
    }

    @Override
    public List<UserSignin> findAll() {
        return userSigninDAO.findAll();
    }

    @Autowired
    private UserSigninRepository signInOutRepository;

    public void saveSignInOut(UserSignin signInOut) {
        signInOutRepository.save(signInOut);
    }





}
