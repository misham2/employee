package com.jdbctemplate.employee.entity;

public class Attendance {
    private int id;
    private String TimeIn;
    private String TimeOut;
    private String Options;
    private String Date;

    private  int UserId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTimeIn() {
        return TimeIn;
    }

    public void setTimeIn(String timeIn) {
        TimeIn = timeIn;
    }

    public String getTimeOut() {
        return TimeOut;
    }

    public void setTimeOut(String timeOut) {
        TimeOut = timeOut;
    }

    public String getOptions() {
        return Options;
    }

    public void setOptions(String options) {
        Options = options;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }
}
