package com.example.biye.config;

public class AppConfig {
    // 数据库配置
    public static final String DB_HOST = "10.0.2.2";  // 使用Android模拟器访问本机MySQL
    public static final String DB_PORT = "3306";
    public static final String DB_NAME = "wuliu";
    public static final String DB_USER = "root";      // 你的数据库用户名
    public static final String DB_PASS = "123456";    // 你的数据库密码

    // API配置
    public static final String API_BASE_URL = "http://your-api-endpoint";
    
    // 其他配置常量
    public static final int NETWORK_TIMEOUT = 15; // 秒
} 