package com.example.biye.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.biye.R;
import com.example.biye.model.UserCredential;
import com.example.biye.model.UserInfo;
import java.util.List;
import android.text.TextUtils;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private List<UserInfo> users;

    public UserAdapter(List<UserInfo> users) {
        this.users = users;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        UserInfo user = users.get(position);
        
        // 设置用户名和ID
        holder.usernameText.setText(String.format("Username: %s (ID: %s)", 
            user.getUsername(), user.getUserId()));
        
        // 设置用户类型，使用不同颜色区分
        String userType = UserCredential.TYPE_ADMIN.equals(user.getUserType()) ? 
            holder.itemView.getContext().getString(R.string.user_type_admin) :
            holder.itemView.getContext().getString(R.string.user_type_user);
        holder.userTypeText.setText(userType);
        holder.userTypeText.setTextColor(
            UserCredential.TYPE_ADMIN.equals(user.getUserType()) ? 
            ContextCompat.getColor(holder.itemView.getContext(), R.color.primary) :
            ContextCompat.getColor(holder.itemView.getContext(), R.color.gray)
        );
        
        // 设置包裹数量和联系信息
        StringBuilder info = new StringBuilder();
        info.append(holder.itemView.getContext().getString(R.string.package_count, user.getPackageCount()));
        if (!TextUtils.isEmpty(user.getPhone())) {
            info.append("\nPhone: ").append(user.getPhone());
        }
        if (!TextUtils.isEmpty(user.getEmail())) {
            info.append("\nEmail: ").append(user.getEmail());
        }
        holder.packageCountText.setText(info.toString());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void updateData(List<UserInfo> newUsers) {
        this.users = newUsers;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView usernameText;
        TextView userTypeText;
        TextView packageCountText;

        ViewHolder(View view) {
            super(view);
            usernameText = view.findViewById(R.id.usernameText);
            userTypeText = view.findViewById(R.id.userTypeText);
            packageCountText = view.findViewById(R.id.packageCountText);
        }
    }
} 