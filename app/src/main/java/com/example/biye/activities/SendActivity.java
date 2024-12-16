package com.example.biye.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.biye.R;
import com.example.biye.model.Package;
import com.example.biye.model.UserInfo;
import com.example.biye.model.UserCredential;
import com.example.biye.utils.DatabaseManager;
import com.example.biye.utils.SessionManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.text.SimpleDateFormat;

public class SendActivity extends AppCompatActivity {
    private EditText receiverNameInput;
    private EditText receiverPhoneInput;
    private EditText receiverAddressInput;
    private EditText weightInput;
    private Spinner senderSpinner;
    private List<UserInfo> userList;
    private DatabaseManager dbManager;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);

        dbManager = new DatabaseManager(this);
        sessionManager = SessionManager.getInstance(this);
        setupViews();
        
        if (sessionManager.isAdmin()) {
            loadUsers();
        }
    }

    private void setupViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.send_package);

        receiverNameInput = findViewById(R.id.receiverNameInput);
        receiverPhoneInput = findViewById(R.id.receiverPhoneInput);
        receiverAddressInput = findViewById(R.id.receiverAddressInput);
        weightInput = findViewById(R.id.weightInput);

        Button submitButton = findViewById(R.id.submitButton);
        submitButton.setText(R.string.submit);
        submitButton.setOnClickListener(v -> validateAndSend());

        senderSpinner = findViewById(R.id.senderSpinner);
        if (sessionManager.isAdmin()) {
            senderSpinner.setVisibility(View.VISIBLE);
            TextView senderLabel = findViewById(R.id.senderLabel);
            senderLabel.setVisibility(View.VISIBLE);
        } else {
            senderSpinner.setVisibility(View.GONE);
            TextView senderLabel = findViewById(R.id.senderLabel);
            senderLabel.setVisibility(View.GONE);
        }
    }

    private void loadUsers() {
        dbManager.getAllUsers(users -> {
            runOnUiThread(() -> {
                userList = users;
                List<String> userNames = new ArrayList<>();
                for (UserInfo user : users) {
                    if (!UserCredential.TYPE_ADMIN.equals(user.getUserType())) {
                        userNames.add(user.getUsername());
                    }
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    this, 
                    android.R.layout.simple_spinner_item, 
                    userNames
                );
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                senderSpinner.setAdapter(adapter);
            });
        });
    }

    private void validateAndSend() {
        String receiverName = receiverNameInput.getText().toString().trim();
        String receiverPhone = receiverPhoneInput.getText().toString().trim();
        String receiverAddress = receiverAddressInput.getText().toString().trim();
        String weightStr = weightInput.getText().toString().trim();

        if (!validateInput(receiverName, receiverPhone, receiverAddress, weightStr)) {
            return;
        }

        double weight = Double.parseDouble(weightStr);
        String packageId = String.format("%s%04d",
            new SimpleDateFormat("yyMMdd", Locale.getDefault()).format(new Date()),
            new Random().nextInt(10000));
        
        String userId;
        if (sessionManager.isAdmin()) {
            if (senderSpinner.getSelectedItem() == null) {
                showError(getString(R.string.msg_select_sender));
                return;
            }
            String selectedUsername = senderSpinner.getSelectedItem().toString();
            userId = getUserIdByUsername(selectedUsername);
            if (userId == null) {
                showError(getString(R.string.msg_invalid_sender));
                return;
            }
        } else {
            userId = sessionManager.getUserId();
        }

        Package pkg = new Package();
        pkg.setPackageId(packageId);
        pkg.setSenderId(userId);
        pkg.setStatus("Pending");
        pkg.setWeight(weight);
        pkg.setShippingCost(calculateShippingCost(weight));
        pkg.setSentTime(System.currentTimeMillis());
        pkg.setReceiverName(receiverName);
        pkg.setReceiverPhone(receiverPhone);
        pkg.setReceiverAddress(receiverAddress);

        dbManager.addPackage(pkg, success -> {
            runOnUiThread(() -> {
                if (success) {
                    Toast.makeText(this, "Package sent successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Failed to send package", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private boolean validateInput(String name, String phone, String address, String weight) {
        if (TextUtils.isEmpty(name)) {
            showError(R.string.msg_input_required);
            return false;
        }

        if (TextUtils.isEmpty(phone)) {
            showError(R.string.msg_input_required);
            return false;
        }

        if (!phone.matches("^1[3-9]\\d{9}$")) {
            showError(R.string.msg_invalid_phone);
            return false;
        }

        if (TextUtils.isEmpty(address)) {
            showError(R.string.msg_input_required);
            return false;
        }

        if (TextUtils.isEmpty(weight)) {
            showError(R.string.msg_input_required);
            return false;
        }

        try {
            double w = Double.parseDouble(weight);
            if (w <= 0) {
                showError("Invalid weight");
                return false;
            }
        } catch (NumberFormatException e) {
            showError("Invalid weight format");
            return false;
        }

        return true;
    }

    private void showError(int messageResId) {
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }

    private void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private double calculateShippingCost(double weight) {
        return 10 + weight * 5;
    }

    private String getUserIdByUsername(String username) {
        if (userList != null) {
            for (UserInfo user : userList) {
                if (user.getUsername().equals(username)) {
                    return user.getUserId();
                }
            }
        }
        return null;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
} 