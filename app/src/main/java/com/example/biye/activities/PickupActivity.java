package com.example.biye.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.biye.R;
import com.example.biye.utils.DatabaseManager;

public class PickupActivity extends AppCompatActivity {
    private EditText codeInput;
    private DatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pickup);

        dbManager = new DatabaseManager(this);
        setupViews();
    }

    private void setupViews() {
        // Setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.pickup_package);

        // Setup views
        codeInput = findViewById(R.id.codeInput);
        Button submitButton = findViewById(R.id.submitButton);
        submitButton.setText(R.string.submit);
        submitButton.setOnClickListener(v -> validateAndPickup());
    }

    private void validateAndPickup() {
        String code = codeInput.getText().toString().trim();
        if (code.isEmpty()) {
            Toast.makeText(this, R.string.msg_input_required, Toast.LENGTH_SHORT).show();
            return;
        }

        dbManager.pickupPackage(code, success -> {
            runOnUiThread(() -> {
                if (success) {
                    Toast.makeText(this, "Package picked up successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Invalid pickup code", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
} 