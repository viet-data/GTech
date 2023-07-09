package com.example.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.User;
import com.example.myapplication.ui.activity.admin.DoctorProfileActivity;
import com.example.myapplication.ui.activity.admin.UserProfileActivity;

import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserViewHolder> {
    private List<User> users;
    private Context context;

    public UserListAdapter(Context context, List<User> users) {
        this.users = users;
        this.context = context;
    }

    @Override
    public int getItemCount(){
        return users == null ? 0 : users.size();
    }

    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = users.get(position);
        holder.user = user;
        holder.txtName.setText(user.getFullName());
        String accessLevel = user.getUserLevel();
        accessLevel = accessLevel.substring(0,1) + accessLevel.substring(1).toLowerCase();
        holder.txtAccessLevel.setText(accessLevel);
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        User user;
        TextView txtName;
        TextView txtAccessLevel;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            txtName = itemView.findViewById(R.id.txt_name);
            txtAccessLevel = itemView.findViewById(R.id.txt_access_level);
        }
        @Override
        public void onClick(View v) {
            Context context = v.getContext();
            Intent intent;
            if (user.getUserLevel().equals("PATIENT")) {
                intent = new Intent(context, UserProfileActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("user", user);
                intent.putExtras(bundle);
            } else {
                intent = new Intent(context, DoctorProfileActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("user", user);
                intent.putExtras(bundle);
            }
            context.startActivity(intent);
        }
    }
}

