package com.example.myapplication.ui.activity.patient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.myapplication.R;
import com.example.myapplication.adapter.SymptomListAdapter;
import com.example.myapplication.databinding.ActivityDiagnosisBinding;
import com.example.myapplication.model.Symptom;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DiagnosisActivity extends AppCompatActivity {
    ActivityDiagnosisBinding binding;
    Toolbar toolbar;
    List<Symptom> symptomList;
    RecyclerView recyclerViewSymptoms;
    FirebaseFirestore firestore;
    SymptomListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDiagnosisBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        toolbar = findViewById(R.id.my_toolbar);
        recyclerViewSymptoms = findViewById(R.id.recycler_view_symptoms);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        recyclerViewSymptoms.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        firestore = FirebaseFirestore.getInstance();
        symptomList = new ArrayList<>();
        adapter = new SymptomListAdapter(DiagnosisActivity.this, symptomList);
        recyclerViewSymptoms.setAdapter(adapter);
        showData();

    }
    private void showData() {
        firestore.collection("Symptoms").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for (DocumentChange documentChange : value.getDocumentChanges()) {
                    if (documentChange.getType() == DocumentChange.Type.ADDED) {
                        QueryDocumentSnapshot doc = documentChange.getDocument();
                        String id = doc.getId();
                        Symptom symptom = doc.toObject(Symptom.class)
                                .withIdAndDesc(id, doc.getString("description"));
                        symptomList.add(symptom);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }
}