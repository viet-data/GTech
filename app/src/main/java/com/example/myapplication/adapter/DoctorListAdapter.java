package com.example.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Doctor;
import com.example.myapplication.model.Symptom;
import com.example.myapplication.ui.activity.admin.DoctorProfileActivity;
import com.example.myapplication.ui.activity.patient.DoctorDetailsActivity;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class DoctorListAdapter extends RecyclerView.Adapter<DoctorListAdapter.DoctorListViewHolder> {

    private List<Doctor> doctorList;
    private AppCompatActivity activity;
    private FirebaseFirestore firestore;
    private Context context;
    //private List<Symptom> selectedSymptomList;

    public DoctorListAdapter(Context context, List<Doctor> doctorList) {
        this.doctorList = doctorList;
        this.context = context;


    }
    @NonNull
    @Override
    public DoctorListAdapter.DoctorListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_doctor, parent, false);

        firestore = FirebaseFirestore.getInstance();

        return new DoctorListAdapter.DoctorListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorListAdapter.DoctorListViewHolder holder, int position) {
        Doctor doctor = doctorList.get(position);
        holder.doctor = doctor;
        holder.txtName.setText(doctor.getName());

    }

    @Override
    public int getItemCount() {
        return doctorList==null ? 0 : doctorList.size();
    }
    public class DoctorListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView txtName;
        Doctor doctor;
        public DoctorListViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txt_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Context context = v.getContext();
            Intent intent;
            intent = new Intent(context, DoctorDetailsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("doctor", doctor);
            intent.putExtras(bundle);

            context.startActivity(intent);
        }
    }
}
