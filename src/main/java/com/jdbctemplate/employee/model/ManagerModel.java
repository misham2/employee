package com.jdbctemplate.employee.model;

import com.jdbctemplate.employee.entity.*;

import java.util.List;

public interface ManagerModel
{
    public User saveUser(User user);

    public User findUserById(int userId);

    public List<User> findAll();

    public void updateUser(User user, int userId);

    public void deleteUser(int userId);




    User findByEmail(String userEmail);

    void saveEmployee(Employee employee);

    List<Employee> getAllEmployees();

    void updateEmployee(Employee employee);

    void save(Attendance attendance);

    void saveAllotedLeave(AllotedLeave allotedLeave);

    List<AllotedLeave> getAllotedLeave();

    void saveData(ApplyLeave applyLeave);
}
