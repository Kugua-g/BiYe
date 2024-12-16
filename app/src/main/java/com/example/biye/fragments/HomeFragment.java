package com.example.biye.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.biye.R;
import com.example.biye.activities.PickupActivity;
import com.example.biye.activities.SendActivity;
import com.example.biye.adapter.PackageAdapter;
import com.example.biye.model.Package;
import com.example.biye.utils.DatabaseManager;
import com.example.biye.utils.SessionManager;
import com.google.android.material.button.MaterialButton;
import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private PackageAdapter adapter;
    private DatabaseManager dbManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        dbManager = new DatabaseManager(requireContext());
        setupViews(view);
        loadRecentPackages();

        return view;
    }

    private void setupViews(View view) {
        MaterialButton pickupButton = view.findViewById(R.id.pickupButton);
        MaterialButton sendButton = view.findViewById(R.id.sendButton);

        boolean isAdmin = SessionManager.getInstance(requireContext()).isAdmin();
        sendButton.setText(isAdmin ? R.string.add_package : R.string.send_package);

        pickupButton.setOnClickListener(v -> 
            startActivity(new Intent(getActivity(), PickupActivity.class)));
        sendButton.setOnClickListener(v -> 
            startActivity(new Intent(getActivity(), SendActivity.class)));

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PackageAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);
    }

    private void loadRecentPackages() {
        String userId = SessionManager.getInstance(requireContext()).getUserId();
        if (userId != null) {
            dbManager.getRecentPackages(userId, packages -> {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        adapter.updateData(packages);
                    });
                }
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        loadRecentPackages();
    }
}