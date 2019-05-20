package com.example.pwmmobile;

public class PartyDate {

    private String month;
    private String day;
    private String year;

    public PartyDate(String month, String day, String year) {
        this.month = month;
        this.day = day;
        this.year = year;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String toString() {
        return month + "/" + day + "/" + year;
    }
}
