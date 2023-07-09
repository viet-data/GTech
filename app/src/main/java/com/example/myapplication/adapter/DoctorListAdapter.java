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
import com.example.myapplication.model.Doctor;
import com.example.myapplication.ui.activity.admin.UpdateDoctorProfileActivity;

import java.util.List;

public class DoctorListAdapter extends RecyclerView.Adapter<DoctorListAdapter.UserViewHolder> {
    private List<Doctor> doctors;
    private Context context;

    public DoctorListAdapter(Context context, List<Doctor> doctors) {
        this.doctors = doctors;
        this.context = context;
    }

    @Override
    public int getItemCount(){
        return doctors == null ? 0 : doctors.size();
    }

    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_doctor, parent, false);
        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        Doctor doctor = doctors.get(position);
        holder.txtName.setText(String.valueOf(doctor.getName()));
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtName;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            txtName = itemView.findViewById(R.id.txt_name);
        }
        @Override
        public void onClick(View v) {
            Context context = v.getContext();
            Intent intent = new Intent(context, UpdateDoctorProfileActivity.class);
            context.startActivity(intent);
        }
    }
}

