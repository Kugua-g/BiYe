package com.example.biye.adapter;

import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.biye.R;

public class LogisticsViewHolder extends RecyclerView.ViewHolder {
    public TextView packageId;
    public TextView status;
    public TextView location;
    public TextView time;
    public TextView weight;
    public TextView cost;

    public LogisticsViewHolder(View itemView) {
        super(itemView);
        packageId = itemView.findViewById(R.id.packageId);
        status = itemView.findViewById(R.id.status);
        location = itemView.findViewById(R.id.location);
        time = itemView.findViewById(R.id.time);
        weight = itemView.findViewById(R.id.weight);
        cost = itemView.findViewById(R.id.cost);
    }
} 