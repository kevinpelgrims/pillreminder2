package com.kevinpelgrims.pillreminder2.models;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Reminder {
    private String userId;
    private Integer hour;
    private Integer minute;
    private String name;
    private String note;

    public Reminder() {
    }

    public Reminder(String userId, Integer hour, Integer minute, String name, String note) {
        this.userId = userId;
        this.hour = hour;
        this.minute = minute;
        this.name = name;
        this.note = note;
    }

    public String getUserId() {
        return userId;
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

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("userId", userId);
        result.put("hour", hour);
        result.put("minute", minute);
        result.put("name", name);
        result.put("note", note);

        return result;
    }
}
