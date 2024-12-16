package com.example.biye.model;

public class UserCredential {
    private String userId;
    private String username;
    private String password;
    private String userType;

    public static final String TYPE_ADMIN = "admin";
    public static final String TYPE_USER = "user";

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
} 