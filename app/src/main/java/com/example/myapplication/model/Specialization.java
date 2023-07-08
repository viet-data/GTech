package com.example.myapplication.model;

import com.google.firebase.firestore.Exclude;

public class Specialization {
    private String specializationId;
    private String name;
    private String description;
    public Specialization(){}
    public Specialization(String name, String description) {
        this.name = name;
        this.description = description;
    }
    @Exclude
    public String getSpecializationId() {
        return specializationId;
    }
    @Exclude
    public void setSpecializationId(String specializationId) {
        this.specializationId = specializationId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
