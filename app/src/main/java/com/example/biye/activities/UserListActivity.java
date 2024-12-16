package com.example.biye.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.biye.R;
import com.example.biye.adapter.UserAdapter;
import com.example.biye.model.UserInfo;
import com.example.biye.utils.DatabaseManager;
import java.util.ArrayList;

public class UserListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private UserAdapter adapter;
    private DatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        dbManager = new DatabaseManager(this);
        setupToolbar();
        setupRecyclerView();
        loadUsers();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.users);
    }

    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UserAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);
    }

    private void loadUsers() {
        dbManager.getAllUsers(users -> {
            runOnUiThread(() -> {
                adapter.updateData(users);
                setTitle(getString(R.string.user_count, users.size()));
            });
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
} 