package com.example.myapplication.model;

import com.google.firebase.firestore.Exclude;

import org.checkerframework.checker.nullness.qual.NonNull;

public class Symptom {

    @Exclude
    protected String symptomId;
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
}
