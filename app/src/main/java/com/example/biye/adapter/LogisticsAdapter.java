package com.example.biye.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.biye.R;
import com.example.biye.activities.LogisticsDetailActivity;
import com.example.biye.model.Package;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class LogisticsAdapter extends RecyclerView.Adapter<LogisticsAdapter.ViewHolder> {
    private List<Package> packages;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());

    public LogisticsAdapter(List<Package> packages) {
        this.packages = packages;
    }

    public void updateData(List<Package> newPackages) {
        this.packages = newPackages;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_logistics, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Package pack = packages.get(position);
        
        holder.packageId.setText("Tracking Number: " + pack.getPackageId());
        holder.status.setText("Status: " + pack.getStatus());
        holder.location.setText("Receiver Address: " + pack.getReceiverAddress());
        holder.time.setText(String.format("Sent Time: %s\nEstimated Arrival: %s", 
            dateFormat.format(pack.getSentTime()),
            dateFormat.format(pack.getEstimatedArrivalTime())));

        holder.weight.setText(String.format("Weight: %.2fkg", pack.getWeight()));
        holder.cost.setText(String.format("Cost: ¥%.2f", pack.getShippingCost()));

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), LogisticsDetailActivity.class);
            intent.putExtra("package_id", pack.getPackageId());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return packages != null ? packages.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView packageId;
        public TextView status;
        public TextView location;
        public TextView time;
        public TextView weight;
        public TextView cost;

        public ViewHolder(View view) {
            super(view);
            packageId = view.findViewById(R.id.packageId);
            status = view.findViewById(R.id.status);
            location = view.findViewById(R.id.location);
            time = view.findViewById(R.id.time);
            weight = view.findViewById(R.id.weight);
            cost = view.findViewById(R.id.cost);
        }
    }
} 