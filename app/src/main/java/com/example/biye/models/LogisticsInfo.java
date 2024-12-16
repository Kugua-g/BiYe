package com.example.biye.models;

/**
 * 物流信息数据模型
 */
public class LogisticsInfo {
    private String trackingNumber; // 物流单号
    private String status;         // 物流状态
    private String currentLocation;// 当前位置
    private String updateTime;     // 更新时间

    public LogisticsInfo(String trackingNumber, String status, String currentLocation) {
        this.trackingNumber = trackingNumber;
        this.status = status;
        this.currentLocation = currentLocation;
        this.updateTime = System.currentTimeMillis() + "";
    }

    // Getter和Setter方法
    public String getTrackingNumber() {
        return trackingNumber;
    }

    public String getStatus() {
        return status;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public String getUpdateTime() {
        return updateTime;
    }
} 