package com.jdbctemplate.employee.Repo;

import com.jdbctemplate.employee.entity.UserSignin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserSigninRepository extends JpaRepository<UserSignin, Long>

{
    List<UserSignin> findByUserId(Long userId);
}
