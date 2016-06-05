package com.kevinpelgrims.pillreminder2.models;

import java.util.List;

public class User {
    private String email;
    private String displayName;
    private String photoUrl;

    private List<Reminder> reminders;

    public User() {
    }

    public User(String email, String displayName, String photoUrl) {
        this.email = email;
        this.displayName = displayName;
        this.photoUrl = photoUrl;
    }

    public String getEmail() {
        return email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public List<Reminder> getReminders() {
        return reminders;
    }

    public void setReminders(List<Reminder> reminders) {
        this.reminders = reminders;
    }
}
