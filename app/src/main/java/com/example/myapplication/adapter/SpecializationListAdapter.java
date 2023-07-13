package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Specialization;
import com.example.myapplication.model.Symptom;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class SpecializationListAdapter extends RecyclerView.Adapter<SpecializationListAdapter.SpecializationViewHolder>{

    private List<Specialization> specializationList;
    private Context context;
    private FirebaseFirestore firestore;

    public SpecializationListAdapter(Context context, List<Specialization> specializations) {
        this.specializationList = specializations;
        this.context = context;
    }
    public List<Specialization> getSpecializationList() {
        return specializationList;
    }

    @NonNull
    @Override
    public SpecializationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_specialization, parent, false);

        firestore = FirebaseFirestore.getInstance();

        return new SpecializationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SpecializationViewHolder holder, int position) {
        Specialization specialization = specializationList.get(position);
        holder.txtSpecName.setText(specialization.getName());
    }

    @Override
    public int getItemCount() {
        return specializationList == null ? 0 : specializationList.size();
    }

    public class SpecializationViewHolder extends RecyclerView.ViewHolder {
        TextView txtSpecName;
        public SpecializationViewHolder(@NonNull View itemView) {
            super(itemView);
            txtSpecName = itemView.findViewById(R.id.txt_name);

        }
    }
}

