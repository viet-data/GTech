package com.example.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.User;
import com.example.myapplication.ui.activity.admin.UserInformationActivity;

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
        holder.txtName.setText(String.valueOf(user.getFullName()));
        String userLevel = user.getUserLevel();
        userLevel = userLevel.substring(0, 1) + userLevel.substring(1).toLowerCase();
        holder.txtUserLevel.setText(userLevel);
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView txtName;
        TextView txtUserLevel;
        public UserViewHolder(@NonNull View itemView){
            super(itemView);
            itemView.setOnClickListener(this);
            txtName = itemView.findViewById(R.id.txt_name);
            txtUserLevel = itemView.findViewById(R.id.txt_access_level);
        }
        @Override
        public void onClick(View v) {
            Context context = v.getContext();
            Intent intent = new Intent(context, UserInformationActivity.class);
            context.startActivity(intent);
        }
    }
}

