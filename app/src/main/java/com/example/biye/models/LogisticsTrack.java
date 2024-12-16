package com.example.biye.models;

/**
 * 物流轨迹数据模型
 * 用于存储单个物流节点的信息
 */
public class LogisticsTrack {
    private String status;      // 物流状态（如：已签收、运输中等）
    private String description; // 详细描述（如：包裹已签收，签收人：张三）
    private String time;        // 状态更新时间

    /**
     * 构造函数
     * @param status 物流状态
     * @param description 详细描述
     * @param time 更新时间
     */
    public LogisticsTrack(String status, String description, String time) {
        this.status = status;
        this.description = description;
        this.time = time;
    }

    // Getter方法
    public String getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public String getTime() {
        return time;
    }
} 