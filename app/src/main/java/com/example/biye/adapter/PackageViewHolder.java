package com.example.biye.adapter;

import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.biye.R;

public class PackageViewHolder extends RecyclerView.ViewHolder {
    public TextView packageIdText;
    public TextView statusText;
    public TextView timeText;
    public TextView receiverText;

    public PackageViewHolder(View itemView) {
        super(itemView);
        packageIdText = itemView.findViewById(R.id.packageIdText);
        statusText = itemView.findViewById(R.id.statusText);
        timeText = itemView.findViewById(R.id.timeText);
        receiverText = itemView.findViewById(R.id.receiverText);
    }
} 