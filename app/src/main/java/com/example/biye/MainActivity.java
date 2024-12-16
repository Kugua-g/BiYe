package com.example.biye;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.example.biye.fragments.HomeFragment;
import com.example.biye.fragments.LogisticsFragment;
import com.example.biye.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * 主界面Activity
 * 实现功能：
 * 1. 底部导航栏管理
 * 2. 各个功能页面的切换
 * 3. 用户状态管理
 */
public class MainActivity extends AppCompatActivity {
    // 底部导航栏控件
    private BottomNavigationView bottomNav;
    //真是艹了
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            
            if (item.getItemId() == R.id.nav_home) {
                selectedFragment = new HomeFragment();
                setTitle(R.string.nav_home);
            } else if (item.getItemId() == R.id.nav_logistics) {
                selectedFragment = new LogisticsFragment();
                setTitle(R.string.nav_logistics);
            } else if (item.getItemId() == R.id.nav_profile) {
                selectedFragment = new ProfileFragment();
                setTitle(R.string.nav_profile);
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, selectedFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
                return true;
            }
            return false;
        });

        // 设置默认显示的Fragment
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new HomeFragment())
                .commit();
            setTitle(R.string.nav_home);
        }
    }
} 