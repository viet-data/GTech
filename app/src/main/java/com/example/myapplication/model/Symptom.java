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
    public Symptom withId(String id){
        this.symptomId = id;
        return  (Symptom) this;
    }
    public Symptom withIdAndDesc(@NonNull String id, @NonNull String description) {
        this.symptomId = id;
        this.description = description;
        return (Symptom) this;
    }

    public String getDescription() {
        return description;
    }

    public String getSymptomId() {
        return symptomId;
    }
    @Exclude
    public void setSymptomId(String symptomId) {
        this.symptomId = symptomId;
    }
    @Exclude
    public void setDescription(String description) {
        this.description = description;
    }
}
