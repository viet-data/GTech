package com.example.myapplication.repository;

import com.example.myapplication.model.Condition;
import com.example.myapplication.model.Symptom;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class ConditionRepository {
    private FirebaseFirestore firestore;
    private CollectionReference condRef;

    public ConditionRepository() {
        firestore = FirebaseFirestore.getInstance();
        condRef = firestore.collection("Conditions");
    }

    public void addCondition(Condition condition) {
        // TODO: complete
    }

    public void addSymptom(Condition condition, Symptom symptom) {
        // TODO: complete
    }


}
