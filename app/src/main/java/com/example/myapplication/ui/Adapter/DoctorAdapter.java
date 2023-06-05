package com.example.myapplication.ui.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.ui.instance.Doctor;

import java.util.ArrayList;
import java.util.List;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.DataViewHolder> {
    private ArrayList<Doctor> doctors;
    private Context context;

    public DoctorAdapter(Context context, ArrayList<Doctor> doctors){
        this.doctors = doctors;
        this.context = context;
    }

    @Override
    public int getItemCount(){
        return  doctors == null ?0:doctors.size();
    }

    @Override
    public DoctorAdapter.DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_doctor, parent, false);
        return new DataViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull DoctorAdapter.DataViewHolder holder, int position) {
        Doctor doctor = doctors.get(position);
        holder.tvName.setText(String.valueOf(doctor.getName()));
        holder.tvAge.setText(String.valueOf(doctor.getAge()));
        holder.tvCategory.setText(String.valueOf(doctor.getCategory()));
    }

    public static class DataViewHolder extends RecyclerView.ViewHolder{
        TextView tvName, tvAge, tvCategory;

        public DataViewHolder(@NonNull View itemView){
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_name);
            tvAge = itemView.findViewById(R.id.tv_age);
            tvCategory = itemView.findViewById(R.id.tv_category);
        }
    }
}

