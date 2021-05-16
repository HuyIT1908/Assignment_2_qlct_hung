package com.example.assignment_2.Context;

public class Date_QLCT {
    private int year;
    private int month;
    private int day;

    public Date_QLCT(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public Date_QLCT() {
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
