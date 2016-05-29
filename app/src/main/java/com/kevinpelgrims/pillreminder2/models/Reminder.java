package com.kevinpelgrims.pillreminder2.models;

public class Reminder {
    private String userId;
    private Integer hour;
    private Integer minute;
    private String name;
    private String note;

    public Reminder(String userId, Integer hour, Integer minute, String name, String note) {
        this.userId = userId;
        this.hour = hour;
        this.minute = minute;
        this.name = name;
        this.note = note;
    }

    public Integer getHour() {
        return hour;
    }

    public Integer getMinute() {
        return minute;
    }

    public String getName() {
        return name;
    }

    public String getNote() {
        return note;
    }
}
