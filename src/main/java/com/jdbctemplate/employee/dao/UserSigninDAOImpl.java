package com.jdbctemplate.employee.dao;

import com.jdbctemplate.employee.entity.UserSignin;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class UserSigninDAOImpl implements UserSigninDAO{


        private  JdbcTemplate jdbcTemplate;

         public UserSigninDAOImpl(JdbcTemplate jdbcTemplate)
         {
            this.jdbcTemplate = jdbcTemplate;
         }

        @Override
        public void save(UserSignin userSignin) {
            String sql = "INSERT INTO user_signin ( signin_time, signout_time) VALUES (?, ?)";
            jdbcTemplate.update(sql, userSignin.getUserId(), userSignin.getSigninTime(), userSignin.getSignoutTime());
        }

        @Override
        public List<UserSignin> findAll() {
            String sql = "SELECT * FROM user_signin";
            return jdbcTemplate.query(sql, (rs, rowNum) -> {
                UserSignin userSignin = new UserSignin();

                userSignin.setUserId(rs.getInt("user_id"));
                userSignin.setSigninTime(rs.getTimestamp("signin_time").toLocalDateTime());
                userSignin.setSignoutTime(rs.getTimestamp("signout_time").toLocalDateTime());
                return userSignin;
            });
        }


}
