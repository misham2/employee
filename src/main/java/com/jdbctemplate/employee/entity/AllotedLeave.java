package com.jdbctemplate.employee.entity;

public class AllotedLeave {
    private int leave_id;
    private String leave_type;
    private int no_of_Days;

    public int getLeave_id() {
        return leave_id;
    }

    public void setLeave_id(int leave_id) {
        this.leave_id = leave_id;
    }

    public String getLeave_type() {
        return leave_type;
    }

    public void setLeave_type(String leave_type) {
        this.leave_type = leave_type;
    }

    public int getNo_of_Days() {
        return no_of_Days;
    }

    public void setNo_of_Days(int no_of_Days) {
        this.no_of_Days = no_of_Days;
    }


}
