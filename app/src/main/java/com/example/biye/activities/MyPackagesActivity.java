package com.example.biye.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.biye.R;
import com.example.biye.adapter.PackageAdapter;
import com.example.biye.model.Package;
import com.example.biye.utils.DatabaseManager;
import com.example.biye.utils.SessionManager;
import java.util.ArrayList;

public class MyPackagesActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PackageAdapter adapter;
    private DatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_packages);

        dbManager = new DatabaseManager(this);
        setupToolbar();
        setupRecyclerView();
        loadPackages();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.my_packages);
    }

    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PackageAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);
    }

    private void loadPackages() {
        String userId = SessionManager.getInstance(this).getUserId();
        if (userId != null) {
            dbManager.getPackages(userId, packages -> {
                runOnUiThread(() -> adapter.updateData(packages));
            });
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
} 