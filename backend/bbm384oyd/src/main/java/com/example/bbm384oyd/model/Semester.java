package com.example.bbm384oyd.Model;

public class Semester {
    private String startDate;
    private String endDate;

    public String getStartDate() {
        return startDate;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    public String getEndDate() {
        return endDate;
    }
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    public Semester(){
        
    }
    @Override
    public String toString() {
        return "Semester{" +
                "startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                "}";
    }
}