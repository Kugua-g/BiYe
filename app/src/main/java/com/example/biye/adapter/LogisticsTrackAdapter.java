package com.example.biye.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.example.biye.R;
import com.example.biye.model.LogisticsTrack;
import java.util.List;

public class LogisticsTrackAdapter extends RecyclerView.Adapter<LogisticsTrackViewHolder> {
    private List<LogisticsTrack> trackList;

    public LogisticsTrackAdapter(List<LogisticsTrack> trackList) {
        this.trackList = trackList;
    }

    @Override
    public LogisticsTrackViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_logistics_track, parent, false);
        return new LogisticsTrackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LogisticsTrackViewHolder holder, int position) {
        LogisticsTrack track = trackList.get(position);
        holder.statusText.setText(track.getStatus());
        holder.descriptionText.setText(track.getDescription());
        holder.timeText.setText(String.format("Time: %s", track.getTime()));

        // Handle timeline display
        if (position == 0) {
            holder.topLine.setVisibility(View.INVISIBLE);
        }
        if (position == trackList.size() - 1) {
            holder.bottomLine.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return trackList != null ? trackList.size() : 0;
    }

    public void updateData(List<LogisticsTrack> newTrackList) {
        this.trackList = newTrackList;
        notifyDataSetChanged();
    }
} 