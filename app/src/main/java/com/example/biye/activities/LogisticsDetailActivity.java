package com.example.biye.activities;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.biye.R;
import com.example.biye.adapter.LogisticsTrackAdapter;
import com.example.biye.model.LogisticsTrack;
import com.example.biye.model.Package;
import com.example.biye.utils.DatabaseManager;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LogisticsDetailActivity extends AppCompatActivity {
    private DatabaseManager dbManager;
    private String packageId;
    private RecyclerView recyclerView;
    private LogisticsTrackAdapter adapter;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());

    private TextView packageIdText;
    private TextView statusText;
    private TextView receiverText;
    private TextView addressText;
    private TextView weightText;
    private TextView costText;
    private TextView timeText;
    private TextView estimatedTimeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logistics_detail);

        dbManager = new DatabaseManager(this);
        packageId = getIntent().getStringExtra("package_id");

        initViews();
        setupToolbar();
        setupRecyclerView();
        loadPackageDetails();
    }

    private void initViews() {
        packageIdText = findViewById(R.id.packageIdText);
        statusText = findViewById(R.id.statusText);
        receiverText = findViewById(R.id.receiverText);
        addressText = findViewById(R.id.addressText);
        weightText = findViewById(R.id.weightText);
        costText = findViewById(R.id.costText);
        timeText = findViewById(R.id.timeText);
        estimatedTimeText = findViewById(R.id.estimatedTimeText);
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Logistics Details");
    }

    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.trackRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new LogisticsTrackAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);
    }

    private void loadPackageDetails() {
        if (packageId != null) {
            dbManager.getPackageDetails(packageId, pkg -> {
                runOnUiThread(() -> {
                    if (pkg != null) {
                        updateUI(pkg);
                        loadTrackingInfo(pkg);
                    }
                });
            });
        }
    }

    private void updateUI(Package pkg) {
        packageIdText.setText("Tracking Number: " + pkg.getPackageId());
        statusText.setText("Status: " + pkg.getStatus());
        receiverText.setText("Receiver: " + pkg.getReceiverName());
        addressText.setText("Address: " + pkg.getReceiverAddress());
        weightText.setText(String.format("Weight: %.2fkg", pkg.getWeight()));
        costText.setText(String.format("Cost: ¥%.2f", pkg.getShippingCost()));
        timeText.setText("Sent Time: " + dateFormat.format(pkg.getSentTime()));
        estimatedTimeText.setText("Estimated Arrival: " + dateFormat.format(pkg.getEstimatedArrivalTime()));
    }

    private void loadTrackingInfo(Package pkg) {
        List<LogisticsTrack> tracks = new ArrayList<>();
        tracks.add(new LogisticsTrack("Picked Up", "Package picked up by courier", dateFormat.format(pkg.getSentTime()), ""));
        adapter.updateData(tracks);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
} 