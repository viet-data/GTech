package com.example.myapplication.ui.activity.patient;

import static com.example.myapplication.adapter.SymptomListAdapter.*;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;
import com.example.myapplication.adapter.SymptomListAdapter;
import com.example.myapplication.databinding.ActivityDiagnosisBinding;
import com.example.myapplication.model.Condition;
import com.example.myapplication.model.Symptom;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class DiagnosisActivity extends AppCompatActivity {
    ActivityDiagnosisBinding binding;
    Toolbar toolbar;
    List<Symptom> symptomList;
    RecyclerView recyclerViewSymptoms;
    FirebaseFirestore firestore;
    SymptomListAdapter adapter;
    ArrayList<Condition> conditionList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDiagnosisBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        recyclerViewSymptoms = findViewById(R.id.recycler_view_symptoms);

        toolbar = findViewById(R.id.my_toolbar);
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
        showConditions();

        Button buttonDiagnosis = findViewById(R.id.btn_diagnosis);
        buttonDiagnosis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Matching
                List<Symptom> selectedSymptomList = adapter.getSelectedSymptomList();


                ArrayList results = findCondition(selectedSymptomList, conditionList);
                Intent intent = new Intent(DiagnosisActivity.this, ListConditionMatchActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("condition_list", results);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }
    private ArrayList<Condition> findCondition(List<Symptom> selectedSymptomList, List<Condition> conditionList){
        List<Map.Entry<Integer, Condition>> dataDict = new ArrayList<>();
        ArrayList<Condition> results = new ArrayList<>();

        for(Condition condition : conditionList){
            dataDict.add(new AbstractMap.SimpleEntry<>(getMatchingSymptomCount(selectedSymptomList, condition), condition));
        }
        // Sort the list in decreasing order based on keys
        Collections.sort(dataDict, new Comparator<Map.Entry<Integer, Condition>>() {
            @Override
            public int compare(Map.Entry<Integer, Condition> entry1, Map.Entry<Integer, Condition> entry2) {
                return entry2.getKey().compareTo(entry1.getKey());
            }
        });
        int count = 3;
        for (Map.Entry<Integer, Condition> entry : dataDict) {
            if (count == 0){break;}
            count = count - 1;
            results.add(entry.getValue());
        }
        return results;
    }
    private int getMatchingSymptomCount(List<Symptom> selectedSymptomList, Condition condition){
        int count = 0;

        ArrayList<String> pool = new ArrayList<>();
        for(Symptom symptom : condition.getSymptomArrayList()){

            pool.add(symptom.getSymptomId());
        }

        for(Symptom symptom : selectedSymptomList){
            if(pool.contains(symptom.getSymptomId())){
                count ++;
            }
        }
        return count;
    }



    private void showConditions(){
        firestore.collection("Conditions").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for (DocumentChange documentChange : value.getDocumentChanges()) {
                    if (documentChange.getType() == DocumentChange.Type.ADDED) {
                        QueryDocumentSnapshot doc = documentChange.getDocument();
                        String id = doc.getId();
                        Condition condition = doc.toObject(Condition.class).changeToObject(id);
                        conditionList.add(condition);

                    }
                }

            }

        });



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