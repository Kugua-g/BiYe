package com.example.biye.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.biye.R;
import com.example.biye.model.Package;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class PackageAdapter extends RecyclerView.Adapter<PackageAdapter.ViewHolder> {
    private List<Package> packages;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());

    public PackageAdapter(List<Package> packages) {
        this.packages = packages;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_package, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Package pkg = packages.get(position);
        holder.packageIdText.setText(String.format("Tracking Number: %s", pkg.getPackageId()));
        
        // 转换状态文本
        String statusText;
        switch (pkg.getStatus()) {
            case "Pending":
                statusText = "Pending";
                break;
            case "Delivered":
                statusText = "Delivered";
                break;
            default:
                statusText = pkg.getStatus();
                break;
        }
        holder.statusText.setText(statusText);
        
        holder.timeText.setText(dateFormat.format(pkg.getSentTime()));
        
        // 如果包裹状态是待取件，则显示取件码
        if ("Pending".equals(pkg.getStatus())) {
            holder.receiverText.setText(String.format("Pickup Code: %s", pkg.getPickupCode()));
        } else {
            holder.receiverText.setText(pkg.getReceiverName());
        }
    }

    @Override
    public int getItemCount() {
        return packages.size();
    }

    public void updateData(List<Package> newPackages) {
        this.packages = newPackages;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView packageIdText;
        TextView statusText;
        TextView timeText;
        TextView receiverText;

        ViewHolder(View view) {
            super(view);
            packageIdText = view.findViewById(R.id.packageIdText);
            statusText = view.findViewById(R.id.statusText);
            timeText = view.findViewById(R.id.timeText);
            receiverText = view.findViewById(R.id.receiverText);
        }
    }
} 