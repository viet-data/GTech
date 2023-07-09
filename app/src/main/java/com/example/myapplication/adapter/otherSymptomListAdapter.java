package com.example.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Condition;
import com.example.myapplication.model.Symptom;
import com.example.myapplication.ui.activity.patient.ConditionDetailsActivity;
import com.example.myapplication.ui.activity.patient.DiagnosisActivity;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class otherSymptomListAdapter extends RecyclerView.Adapter<otherSymptomListAdapter.otherSymptomViewHolder>{


    private List<Symptom> symptomList;
    private AppCompatActivity activity;
    private FirebaseFirestore firestore;
    private List<Symptom> selectedSymptomList;

    public otherSymptomListAdapter(AppCompatActivity appCompatActivity, List<Symptom> symptoms) {
        this.symptomList = symptoms;
        this.activity = appCompatActivity;
        this.selectedSymptomList = new ArrayList<>();

    }




    public List<Symptom> getSelectedSymptomList() {
        return selectedSymptomList;
    }

    @NonNull
    @Override
    public otherSymptomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_symptom, parent, false);

        firestore = FirebaseFirestore.getInstance();

        return new otherSymptomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull otherSymptomViewHolder holder, int position) {
        Symptom symptom = symptomList.get(position);
        holder.txtSymptomDescription.setText(symptom.getDescription());

    }

    @Override
    public int getItemCount() {
        return symptomList==null ? 0 : symptomList.size();
    }
    public class otherSymptomViewHolder extends RecyclerView.ViewHolder {
        TextView txtSymptomDescription;
        public otherSymptomViewHolder(@NonNull View itemView) {
            super(itemView);
            txtSymptomDescription = itemView.findViewById(R.id.txt_symptom_desc);

        }
    }
}
