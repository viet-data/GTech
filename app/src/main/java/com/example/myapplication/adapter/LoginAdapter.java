package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.instance.LoginType;
import com.example.myapplication.R;

import java.util.List;

public class LoginAdapter extends ArrayAdapter<LoginType>{

    public LoginAdapter(@NonNull Context context, int resource, @NonNull List<LoginType> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.login_item_selected, parent, false);
        TextView tvLoginSelected = convertView.findViewById(R.id.tv_login_item_selected);
        LoginType loginSelected= this.getItem(position);
        if (loginSelected != null) {
            tvLoginSelected.setText(loginSelected.getName());
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.login_category, parent, false);
        TextView tvLoginCategory = convertView.findViewById(R.id.tv_login_category);
        LoginType loginCategory = this.getItem(position);
        if (loginCategory != null) {
            tvLoginCategory.setText(loginCategory.getName());
        }
        return convertView;
    }
}
