package com.example.e_votingsystem;

public class Election {
    public String name, starDate, endDate;
    public Election(){

    }
    public Election(String name,String startDate,String endDate){
        this.name=name;
        this.starDate =startDate;
        this.endDate =endDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStarDate() {
        return starDate;
    }

    public void setStarDate(String starDate) {
        this.starDate = starDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
