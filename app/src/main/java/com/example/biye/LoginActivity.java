package com.example.biye;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.biye.utils.DatabaseManager;
import com.example.biye.utils.SessionManager;

/**
 * 登录界面Activity
 * 实现用户登录功能，包括：
 * 1. 用户名和密码的输入验证
 * 2. 登录请求处理
 * 3. 跳转到注册界面的功能
 */
public class LoginActivity extends AppCompatActivity {
    //sb俄罗斯，我tm直接霸体＋螺旋丸塞尼🐎🖊里
    private static final String TAG = "LoginActivity";  // 添加TAG常量

    // 界面控件
    private EditText usernameInput;
    private EditText passwordInput;
    // 密码最小长度常量
    private static final int MIN_PASSWORD_LENGTH = 6;
    // 添加DatabaseHelper成员变量
    private DatabaseManager dbManager;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbManager = new DatabaseManager(this);
        dbManager.checkAdminAccount();
        sessionManager = SessionManager.getInstance(this);

        // 检查是否已经登录
        if (sessionManager.isLoggedIn()) {
            startMainActivity();
            return;
        }

        initViews();
        setupClickListeners();
    }
    
    private void initViews() {
        usernameInput = findViewById(R.id.usernameEditText);
        passwordInput = findViewById(R.id.passwordEditText);
    }
    
    private void setupClickListeners() {
        TextView registerTextView = findViewById(R.id.registerTextView);
        registerTextView.setOnClickListener(v -> 
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class))
        );

        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(v -> validateAndLogin());
    }
    
    private void validateAndLogin() {
        String username = usernameInput.getText().toString().trim();
        String password = passwordInput.getText().toString();

        if (!validateInput(username, password)) {
            return;
        }

        dbManager.validateUser(username, password, (userId, userType, success) -> {
            runOnUiThread(() -> {
                if (success && userId != null) {
                    sessionManager.createLoginSession(userId, username, userType);
                    Toast.makeText(this, R.string.msg_login_success, Toast.LENGTH_SHORT).show();
                    startMainActivity();
                } else {
                    onLoginFailure();
                }
            });
        });
    }
    
    private boolean validateInput(String username, String password) {
        if (username.isEmpty()) {
            showError(getString(R.string.msg_input_required));
            return false;
        }
        
        if (password.isEmpty()) {
            showError(getString(R.string.msg_input_required));
            return false;
        }
        
        if (password.length() < MIN_PASSWORD_LENGTH) {
            showError("Password must be at least 6 characters");
            return false;
        }
        
        return true;
    }
    
    private void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void onLoginFailure() {
        Toast.makeText(this, R.string.msg_login_failed, Toast.LENGTH_SHORT).show();
        passwordInput.setText("");
        passwordInput.requestFocus();
    }
}