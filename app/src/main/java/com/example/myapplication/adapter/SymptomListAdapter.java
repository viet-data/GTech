package com.example.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Symptom;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class SymptomListAdapter extends RecyclerView.Adapter<SymptomListAdapter.SymptomViewHolder>{

    private List<Symptom> symptomList;
    private AppCompatActivity activity;
    private FirebaseFirestore firestore;
    private List<Symptom> selectedSymptomList;

    public SymptomListAdapter(AppCompatActivity appCompatActivity, List<Symptom> symptoms) {
        this.symptomList = symptoms;
        this.activity = appCompatActivity;
        this.selectedSymptomList = new ArrayList<>();

    }
    public List<Symptom> getSelectedSymptomList() {
        return selectedSymptomList;
    }

    @NonNull
    @Override
    public SymptomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_symptom, parent, false);

        firestore = FirebaseFirestore.getInstance();

        return new SymptomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SymptomViewHolder holder, int position) {
        Symptom symptom = symptomList.get(position);
        holder.txtSymptomDescription.setText(symptom.getDescription());

    }

    @Override
    public int getItemCount() {
        return symptomList==null ? 0 : symptomList.size();
    }
    public class SymptomViewHolder extends RecyclerView.ViewHolder {
        TextView txtSymptomDescription;
        public SymptomViewHolder(@NonNull View itemView) {
            super(itemView);
            txtSymptomDescription = itemView.findViewById(R.id.txt_symptom_desc);

        }
    }
}
