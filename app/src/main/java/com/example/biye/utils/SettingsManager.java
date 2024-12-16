package com.example.biye.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 设置管理工具类
 * 负责应用设置的存储和读取
 */
public class SettingsManager {
    private static final String PREF_NAME = "app_settings";
    private static final String KEY_NOTIFICATION = "notification_enabled";
    private static final String KEY_DARK_MODE = "dark_mode_enabled";

    private SharedPreferences preferences;

    public SettingsManager(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void setNotificationEnabled(boolean enabled) {
        preferences.edit().putBoolean(KEY_NOTIFICATION, enabled).apply();
    }

    public boolean isNotificationEnabled() {
        return preferences.getBoolean(KEY_NOTIFICATION, true);
    }

    public void setDarkModeEnabled(boolean enabled) {
        preferences.edit().putBoolean(KEY_DARK_MODE, enabled).apply();
    }

    public boolean isDarkModeEnabled() {
        return preferences.getBoolean(KEY_DARK_MODE, false);
    }
} 