package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Doctor;
import com.example.myapplication.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserViewHolder> {
    private List<User> users;
    private Context context;

    public UserListAdapter(Context context, List<User> users){
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
        holder.tvName.setText(String.valueOf(user.getName()));
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder{
        TextView tvName;

        public UserViewHolder(@NonNull View itemView){
            super(itemView);
            tvName = itemView.findViewById(R.id.txt_name);
        }
    }
}

