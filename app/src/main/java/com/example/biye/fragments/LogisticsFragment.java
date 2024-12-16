package com.example.biye.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.biye.R;
import com.example.biye.adapter.LogisticsAdapter;
import com.example.biye.model.Package;
import com.example.biye.utils.DatabaseManager;
import java.util.ArrayList;

public class LogisticsFragment extends Fragment {
    private RecyclerView recyclerView;
    private LogisticsAdapter adapter;
    private DatabaseManager dbManager;
    private EditText searchInput;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_logistics, container, false);

        dbManager = new DatabaseManager(requireContext());
        setupViews(view);

        return view;
    }

    private void setupViews(View view) {
        // Initialize search input
        searchInput = view.findViewById(R.id.searchInput);
        Button searchButton = view.findViewById(R.id.searchButton);
        searchButton.setOnClickListener(v -> searchPackage());

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new LogisticsAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);
    }

    private void searchPackage() {
        String packageId = searchInput.getText().toString().trim();
        if (packageId.isEmpty()) {
            Toast.makeText(getContext(), "Please enter tracking number", Toast.LENGTH_SHORT).show();
            return;
        }

        dbManager.getPackageDetails(packageId, pkg -> {
            if (isAdded()) {
                requireActivity().runOnUiThread(() -> {
                    if (pkg != null) {
                        ArrayList<Package> list = new ArrayList<>();
                        list.add(pkg);
                        adapter.updateData(list);
                    } else {
                        Toast.makeText(getContext(), "Package not found", Toast.LENGTH_SHORT).show();
                        adapter.updateData(new ArrayList<>());
                    }
                });
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Clean up resources
        recyclerView.setAdapter(null);
        adapter = null;
    }
} 