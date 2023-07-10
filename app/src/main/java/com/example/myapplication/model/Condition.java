package com.example.myapplication.model;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Exclude;


import java.util.ArrayList;
import java.util.List;

public class Condition implements Parcelable {
    @Exclude
    protected String conditionId;

    private String name;
    private String description;
    private Specialization specializationOb;
    private List<Symptom> symptomList;
    private DocumentReference specialization;
    private List<DocumentReference> symptoms;
    private String specializationString;


    public Condition() {}
    public Condition(String conditionId, String name, String description ){
        this.conditionId = conditionId;
        this.name = name;
        this.description = description;

    }
    public Condition changeToObject(String id){
        symptomList = new ArrayList<>();
        this.conditionId = id;
        //System.out.println(this.specialization);
        this.specialization.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                specializationOb = documentSnapshot.toObject(Specialization.class);
                specializationOb.setSpecializationId(documentSnapshot.getId());
            }
        });

        for (DocumentReference dR : this.symptoms){
            //System.out.println(dR.getId());
            dR.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    Symptom symptom = documentSnapshot.toObject(Symptom.class);

                    symptom.setSymptomId(documentSnapshot.getId());

                    symptomList.add(symptom);
                }
            });
        }
        return (Condition) this;
    }

    public Specialization getSpecializationOb() {
        return  this.specializationOb;
    }

    public DocumentReference getSpecialization() {
        return specialization;
    }

    public void setSpecializationOb(Specialization anSpecialization) {
        this.specializationOb = anSpecialization;
    }

    public List<DocumentReference> getSymptoms(){
        return symptoms;
    }
    public List<Symptom> getSymptomList(){
        return symptomList;
    }

    @Exclude
    public String getConditionId() {
        return conditionId;
    }
    @Exclude
    public void setConditionId(String conditionId) {
        this.conditionId = conditionId;
    }
    public Condition(String name, String description, Specialization specializationOb) {
        this.name = name;
        this.description = description;
        this.specializationOb = specializationOb;
    }
    public void addSymptom(Symptom symptom) {

        this.symptomList.add(symptom);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSymptomList(List<Symptom> symptomList) {
        this.symptomList = symptomList;
    }

    public void setSpecialization(DocumentReference specialization) {
        this.specialization = specialization;
    }

    public void setSymptoms(List<DocumentReference> symptoms) {
        this.symptoms = symptoms;
    }

    public String getName() {
        return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(conditionId);
        dest.writeString(name);
        dest.writeString(description);

        dest.writeList(symptomList);
        dest.writeParcelable(specializationOb, flags);


    }

    public Condition(Parcel in) {
        conditionId = in.readString();
        name = in.readString();
        description = in.readString();
        symptomList = new ArrayList<Symptom>();
        in.readList(symptomList, Symptom.class.getClassLoader());
        specializationOb = in.readParcelable(Specialization.class.getClassLoader());
    }

    public static final Creator<Condition> CREATOR = new Creator<Condition>() {
        @Override
        public Condition createFromParcel(Parcel source) {
            return new Condition(source);
        }

        @Override
        public Condition[] newArray(int size) {
            return new Condition[size];
        }
    };
}