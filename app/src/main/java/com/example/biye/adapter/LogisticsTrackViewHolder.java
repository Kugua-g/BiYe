package com.example.biye.adapter;

import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.biye.R;

public class LogisticsTrackViewHolder extends RecyclerView.ViewHolder {
    public View topLine;
    public View dot;
    public View bottomLine;
    public TextView statusText;
    public TextView descriptionText;
    public TextView timeText;

    public LogisticsTrackViewHolder(View itemView) {
        super(itemView);
        topLine = itemView.findViewById(R.id.top_line);
        dot = itemView.findViewById(R.id.dot);
        bottomLine = itemView.findViewById(R.id.bottom_line);
        statusText = itemView.findViewById(R.id.status_text);
        descriptionText = itemView.findViewById(R.id.description_text);
        timeText = itemView.findViewById(R.id.time_text);
    }
} 