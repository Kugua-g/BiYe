package com.example.biye.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.example.biye.R;
import com.example.biye.LoginActivity;
import com.example.biye.activities.MyPackagesActivity;
import com.example.biye.activities.SettingsActivity;
import com.example.biye.activities.UserListActivity;
import com.example.biye.utils.SessionManager;
import com.google.android.material.button.MaterialButton;

public class ProfileFragment extends Fragment {
    private SessionManager sessionManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        
        sessionManager = SessionManager.getInstance(requireContext());
        setupViews(view);
        
        return view;
    }

    private void setupViews(View view) {
        TextView usernameText = view.findViewById(R.id.usernameText);
        usernameText.setText(sessionManager.getUsername());

        MaterialButton myPackagesButton = view.findViewById(R.id.myPackagesButton);
        MaterialButton settingsButton = view.findViewById(R.id.settingsButton);
        MaterialButton logoutButton = view.findViewById(R.id.logoutButton);
        MaterialButton usersButton = view.findViewById(R.id.usersButton);

        myPackagesButton.setText(R.string.my_packages);
        settingsButton.setText(R.string.settings);
        logoutButton.setText(R.string.logout);
        usersButton.setText(R.string.users);

        usersButton.setVisibility(sessionManager.isAdmin() ? View.VISIBLE : View.GONE);

        myPackagesButton.setOnClickListener(v -> 
            startActivity(new Intent(getActivity(), MyPackagesActivity.class)));
            
        settingsButton.setOnClickListener(v -> 
            startActivity(new Intent(getActivity(), SettingsActivity.class)));

        logoutButton.setOnClickListener(v -> {
            sessionManager.clearSession();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        usersButton.setOnClickListener(v -> 
            startActivity(new Intent(getActivity(), UserListActivity.class)));
    }
} 