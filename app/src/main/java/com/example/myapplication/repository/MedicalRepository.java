package com.example.myapplication.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.model.Condition;
import com.example.myapplication.model.Symptom;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MedicalRepository {
    private static final String TAG = "MEDICAL REPOSITORY";
    private FirebaseFirestore firestore;
    private CollectionReference condRef;
    private MedicalRepository instance;

    private MedicalRepository() {
        firestore = FirebaseFirestore.getInstance();
        condRef = firestore.collection("Conditions");
    }

    public synchronized MedicalRepository getInstance() {
        if (instance == null) {
            instance = new MedicalRepository();
        }
        return instance;
    }

    public List<Condition> getAllConditions() {
        CollectionReference condRef = firestore.collection("Conditions");
        List<Condition> conditions = new ArrayList<>();
        condRef.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<String> list = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                list.add(document.getId());
                            }
                            Log.d(TAG, list.toString());
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        return conditions;
    }
    public void addCondition(Condition condition) {
        // TODO: complete
    }

    public void addSymptom(Condition condition, Symptom symptom) {
        // TODO: complete
    }


}
