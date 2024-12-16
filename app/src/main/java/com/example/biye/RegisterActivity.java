package com.example.biye;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.biye.R;
import com.example.biye.utils.DatabaseManager;
import java.util.UUID;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";
    private EditText usernameInput;
    private EditText passwordInput;
    private EditText confirmPasswordInput;
    private EditText phoneInput;
    private EditText emailInput;
    private DatabaseManager dbManager;
//给尼🐎打绝育
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dbManager = new DatabaseManager(this);
        initViews();
        
        Button registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(v -> validateAndRegister());
    }

    private void initViews() {
        usernameInput = findViewById(R.id.usernameEditText);
        passwordInput = findViewById(R.id.passwordEditText);
        confirmPasswordInput = findViewById(R.id.confirmPasswordEditText);
        phoneInput = findViewById(R.id.phoneEditText);
        emailInput = findViewById(R.id.emailEditText);
    }

    private void validateAndRegister() {
        String username = usernameInput.getText().toString().trim();
        String password = passwordInput.getText().toString();
        String confirmPassword = confirmPasswordInput.getText().toString();
        String phone = phoneInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();

        if (!validateInput(username, password, confirmPassword, phone, email)) {
            return;
        }

        String userId = "U" + UUID.randomUUID().toString().substring(0, 8);
        
        dbManager.registerUser(userId, username, password, phone, email, success -> {
            runOnUiThread(() -> {
                if (success) {
                    Toast.makeText(this, R.string.msg_register_success, Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, R.string.msg_register_failed, Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private boolean validateInput(String username, String password, String confirmPassword, 
                                String phone, String email) {
        if (TextUtils.isEmpty(username)) {
            showError(getString(R.string.msg_input_required));
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            showError(getString(R.string.msg_input_required));
            return false;
        }

        if (password.length() < 6) {
            showError("Password must be at least 6 characters");
            return false;
        }

        if (!password.equals(confirmPassword)) {
            showError(getString(R.string.msg_password_mismatch));
            return false;
        }

        if (TextUtils.isEmpty(phone)) {
            showError(getString(R.string.msg_input_required));
            return false;
        }

        if (!phone.matches("^1[3-9]\\d{9}$")) {
            showError(getString(R.string.msg_invalid_phone));
            return false;
        }

        if (!TextUtils.isEmpty(email) && !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showError(getString(R.string.msg_invalid_email));
            return false;
        }

        return true;
    }

    private void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}