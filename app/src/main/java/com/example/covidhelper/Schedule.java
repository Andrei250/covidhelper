package com.example.covidhelper;

public class Schedule {
    private int opening_hour;
    private int opening_minutes;
    private int closing_hour;
    private int closing_minutes;

    public Schedule() {
    }

    public Schedule(int opening_hour, int opening_minutes, int closing_hour, int closing_minutes) {
        this.opening_hour = opening_hour;
        this.opening_minutes = opening_minutes;
        this.closing_hour = closing_hour;
        this.closing_minutes = closing_minutes;
    }

    public int getOpening_hour() {
        return opening_hour;
    }

    public void setOpening_hour(int opening_hour) {
        this.opening_hour = opening_hour;
    }

    public int getOpening_minutes() {
        return opening_minutes;
    }

    public void setOpening_minutes(int opening_minutes) {
        this.opening_minutes = opening_minutes;
    }

    public int getClosing_hour() {
        return closing_hour;
    }

    public void setClosing_hour(int closing_hour) {
        this.closing_hour = closing_hour;
    }

    public int getClosing_minutes() {
        return closing_minutes;
    }

    public void setClosing_minutes(int closing_minutes) {
        this.closing_minutes = closing_minutes;
    }
}
