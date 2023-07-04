package com.example.myapplication.model;

import com.google.firebase.firestore.Exclude;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;

public class Condition {
    @Exclude
    protected String conditionId;

    private String name;
    private String description;
    private Specialization specialization;
    private ArrayList<Symptom> symptoms;

    public Condition() {}
    public Condition(String name, String description, Specialization specialization) {
        this.name = name;
        this.description = description;
        this.specialization = specialization;
    }

    public void addSymptom(Symptom symptom) {
        this.symptoms.add(symptom);
    }
    public Condition withIdAndName(@NonNull String id, @NonNull String name) {
        this.conditionId = id;
        this.name = name;
        return (Condition) this;
    }

    public String getName() {
        return name;
    }
}
