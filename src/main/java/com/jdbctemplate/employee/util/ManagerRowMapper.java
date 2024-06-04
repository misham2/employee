package com.jdbctemplate.employee.util;

import com.jdbctemplate.employee.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

    public class ManagerRowMapper implements RowMapper<User>
    {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException
        {
            User manager = new User();
            manager.setUserId(rs.getInt("user_id"));
            manager.setUserName(rs.getString("user_name"));
            manager.setUserEmail(rs.getString("user_email"));
            manager.setUserPassword(rs.getString("user_password"));
            return manager;
        }





}
