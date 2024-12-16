package com.example.biye.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 用户信息管理工具类
 * 负责用户信息的存储和读取
 */
public class UserManager {
    private static final String PREF_NAME = "user_info";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_IS_LOGIN = "is_login";

    private SharedPreferences preferences;

    public UserManager(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void saveUserInfo(String username) {
        preferences.edit()
            .putString(KEY_USERNAME, username)
            .putBoolean(KEY_IS_LOGIN, true)
            .apply();
    }

    public String getUsername() {
        return preferences.getString(KEY_USERNAME, "用户");
    }

    public boolean isLoggedIn() {
        return preferences.getBoolean(KEY_IS_LOGIN, false);
    }

    public void clearUserInfo() {
        preferences.edit().clear().apply();
    }
} 