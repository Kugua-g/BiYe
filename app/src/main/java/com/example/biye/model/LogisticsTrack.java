package com.example.biye.model;

public class LogisticsTrack {
    private String status;
    private String description;
    private String time;
    private String location;

    public LogisticsTrack(String status, String description, String time, String location) {
        this.status = status;
        this.description = description;
        this.time = time;
        this.location = location;
    }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
} 