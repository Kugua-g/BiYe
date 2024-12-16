package com.example.biye.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.biye.R;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setupToolbar();
        initializeSettings();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("设置");
    }

    private void initializeSettings() {
        // 推送通知开关
        SwitchMaterial notificationSwitch = findViewById(R.id.notificationSwitch);
        notificationSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // TODO: 保存推送通知设置
        });

        // 声音提醒开关
        SwitchMaterial soundSwitch = findViewById(R.id.soundSwitch);
        soundSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // TODO: 保存声音提醒设置
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
} 