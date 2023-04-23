package com.example.bbm384oyd.model;

public class Course {
    private String name;
    private String department;
    private int credit;
    private String type;

    public Course() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Course{" +
                "name='" + name + '\'' +
                ", department='" + department + '\'' +
                ", credit=" + credit +
                ", type='" + type + '\'' +
                '}';
    }
}
