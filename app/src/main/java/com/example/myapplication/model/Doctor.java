package com.example.myapplication.model;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Exclude;

import java.util.ArrayList;
import java.util.List;

public class Doctor {
    private String name;
    private String description;
    private String phone;
    private List<Specialization> specializationList;
    private List<DocumentReference> specializations;

    public Doctor() {
        super();
        // no-args constructor for firestore
    }

    public Doctor(String name, String description, String phone) {
        this.name = name;
        this.description = description;
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Exclude
    public List<Specialization> getSpecializationList() {
        return specializationList;
    }
    @Exclude
    public void setSpecializationList(List<Specialization> specializationList) {
        this.specializationList = specializationList;
    }

    public List<DocumentReference> getSpecializations() {
        return specializations;
    }

    public void setSpecializations(List<DocumentReference> specializations) {
        this.specializations = specializations;
    }
}
