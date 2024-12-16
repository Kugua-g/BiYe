package com.example.biye.model;

public class UserInfo {
    private String userId;
    private String username;
    private String phone;
    private String email;
    private String userType;
    private int packageCount;

    // Getters and Setters
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

    public String getPhone() { 
        return phone; 
    }
    
    public void setPhone(String phone) { 
        this.phone = phone; 
    }

    public String getEmail() { 
        return email; 
    }
    
    public void setEmail(String email) { 
        this.email = email; 
    }

    public String getUserType() { 
        return userType; 
    }
    
    public void setUserType(String userType) { 
        this.userType = userType; 
    }

    public int getPackageCount() { 
        return packageCount; 
    }
    
    public void setPackageCount(int packageCount) { 
        this.packageCount = packageCount; 
    }
} 