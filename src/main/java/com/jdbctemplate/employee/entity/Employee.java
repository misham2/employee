package com.jdbctemplate.employee.entity;


import java.util.Arrays;

public class Employee {

    private int employee_id;
    private String name;

    private String image;

    private String location;

    private String About;

    private String Designation;
    private String Department;

    private String sickLeave;

    private String casualLeave;

    private String restrictedLeave;


    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAbout() {
        return About;
    }

    public void setAbout(String about) {
        About = about;
    }

    public String getDesignation() {
        return Designation;
    }

    public void setDesignation(String designation) {
        Designation = designation;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        Department = department;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSickLeave() {
        return sickLeave;
    }

    public void setSickLeave(String sickLeave) {
        this.sickLeave = sickLeave;
    }

    public String getCasualLeave() {
        return casualLeave;
    }

    public void setCasualLeave(String casualLeave) {
        this.casualLeave = casualLeave;
    }

    public String getRestrictedLeave() {
        return restrictedLeave;
    }

    public void setRestrictedLeave(String restrictedLeave) {
        this.restrictedLeave = restrictedLeave;
    }
}
