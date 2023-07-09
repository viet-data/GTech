package com.example.myapplication.model;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.Exclude;

public class Symptom {
    private String symptomId;
    private String description;
    public Symptom() {}
    public Symptom(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
    @Exclude
    public String getSymptomId() {
        return symptomId;
    }
    @Exclude
    public void setSymptomId(String symptomId) {
        this.symptomId = symptomId;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
