package com.example.myapplication.ui.activity.patient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.example.myapplication.R;
import com.example.myapplication.adapter.SymptomListAdapter;
import com.example.myapplication.adapter.otherSymptomListAdapter;
import com.example.myapplication.model.Condition;
import com.example.myapplication.model.Specialization;
import com.example.myapplication.model.Symptom;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ConditionDetailsActivity extends AppCompatActivity {
    private TextView tvName, tvDes, tvSpec;
    private FirebaseFirestore firestore;

    private Condition condition;
    private Specialization specialization;
    private RecyclerView recyclerViewSymptoms;
    private Toolbar toolbar;
    private List<Symptom> symptomList;
    private otherSymptomListAdapter symptomListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_condition_details);

        condition = getIntent().getParcelableExtra("condition");

        firestore = FirebaseFirestore.getInstance();

        tvName = findViewById(R.id.tv_name);
        tvDes = findViewById(R.id.tv_des);
        tvSpec = findViewById(R.id.tv_spec);

        tvName.setText(condition.getName());
        tvDes.setText(condition.getDescription());
        tvSpec.setText(condition.getSpecializationOb().getName());

        recyclerViewSymptoms = findViewById(R.id.symtoms_list);
        toolbar = findViewById(R.id.back_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        recyclerViewSymptoms.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        symptomList = condition.getSymptomList();
        symptomListAdapter = new otherSymptomListAdapter(ConditionDetailsActivity.this, symptomList);
        recyclerViewSymptoms.setAdapter(symptomListAdapter);




    }
}