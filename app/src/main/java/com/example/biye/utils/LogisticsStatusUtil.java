package com.example.biye.utils;

import android.content.Context;
import androidx.core.content.ContextCompat;
import com.example.biye.R;

/**
 * 物流状态工具类
 * 功能：
 * 1. 定义物流状态常量
 * 2. 管理物流状态对应的颜色
 * 3. 提供状态相关的工具方法
 */
public class LogisticsStatusUtil {
    // 定义物流状态常量
    public static final String STATUS_DELIVERED = "Delivered";    // 最终状态
    public static final String STATUS_DELIVERING = "Delivery";   // 派送状态
    public static final String STATUS_PROCESSING = "In transit";   // 运输状态
    public static final String STATUS_PENDING = "Waiting for collection";      // 初始状态
    public static final String STATUS_CANCELLED = "Cancelled";    // 取消状态

    /**
     * 获取状态对应的显示颜色
     * @param context 上下文
     * @param status 物流状态
     * @return 对应的颜色值
     */
    public static int getStatusColor(Context context, String status) {
        if (status == null) {
            return ContextCompat.getColor(context, R.color.gray);
        }
        
        switch (status) {
            case STATUS_DELIVERED:
                return ContextCompat.getColor(context, R.color.status_delivered);
            case STATUS_DELIVERING:
                return ContextCompat.getColor(context, R.color.status_delivering);
            case STATUS_PROCESSING:
                return ContextCompat.getColor(context, R.color.status_processing);
            case STATUS_PENDING:
                return ContextCompat.getColor(context, R.color.status_pending);
            case STATUS_CANCELLED:
                return ContextCompat.getColor(context, R.color.status_cancelled);
            default:
                return ContextCompat.getColor(context, R.color.gray);
        }
    }

    /**
     * 获取时间线圆点的颜色
     */
    public static int getDotColor(Context context, String status) {
        return getStatusColor(context, status);
    }

    /**
     * 获取时间线的颜色
     * 统一使用浅灰色，保持视觉连贯性
     */
    public static int getLineColor(Context context, String status) {
        return ContextCompat.getColor(context, R.color.gray);
    }
} 