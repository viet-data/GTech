package com.example.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Condition;
import com.example.myapplication.model.Symptom;
import com.example.myapplication.ui.activity.patient.DiagnosisActivity;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class SymptomListAdapter extends RecyclerView.Adapter<SymptomListAdapter.SymptomViewHolder>{


    private List<Symptom> symptomList;
    private DiagnosisActivity activity;
    private FirebaseFirestore firestore;
    private List<Symptom> selectedSymptomList;

    public SymptomListAdapter(DiagnosisActivity diagnosisActivity, List<Symptom> symptoms) {
        this.symptomList = symptoms;
        this.activity = diagnosisActivity;
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
        holder.cbSymptom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.cbSymptom.isChecked()){
                    selectedSymptomList.add(symptom);
                }else{
                    selectedSymptomList.remove(symptom);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return symptomList==null ? 0 : symptomList.size();
    }
    public class SymptomViewHolder extends RecyclerView.ViewHolder {
        TextView txtSymptomDescription;
        CheckBox cbSymptom;
        public SymptomViewHolder(@NonNull View itemView) {
            super(itemView);
            txtSymptomDescription = itemView.findViewById(R.id.txt_symptom_desc);
            cbSymptom = itemView.findViewById(R.id.check_symptom);
        }
    }
}
