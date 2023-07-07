package com.example.myapplication.model;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Exclude;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Condition implements Parcelable {
    @Exclude
    protected String conditionId;

    private String name;
    private String description;
    private Specialization specializationOb;
    private ArrayList<Symptom> symptomArrayList = new ArrayList<>();
    private DocumentReference specialization;
    private ArrayList<DocumentReference> symptoms;




    public Condition() {}
    public Condition(String conditionId, String name, String description ){
        this.conditionId = conditionId;
        this.name = name;
        this.description = description;

    }
    public Condition changeToObject(String id){
        this.conditionId = id;
        //System.out.println(this.specialization);
        this.specialization.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                specializationOb = documentSnapshot.toObject(Specialization.class);

            }
        });

        for (DocumentReference dR : this.symptoms){
            //System.out.println(dR.getId());
            dR.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    symptomArrayList.add(documentSnapshot.toObject(Symptom.class).withId(documentSnapshot.getId()));
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

    public ArrayList<DocumentReference> getSymptoms(){
        return symptoms;
    }
    public ArrayList<Symptom> getSymptomArrayList(){
        return symptomArrayList;
    }

    public String getConditionId() {
        return conditionId;
    }

    public Condition(String name, String description, Specialization specializationOb) {
        this.name = name;
        this.description = description;
        this.specializationOb = specializationOb;
    }



    public Condition withId_NameAndSymptoms(@NonNull String id, @NonNull String name) {
        this.conditionId = id;
        this.name = name;
        return (Condition) this;
    }

    public void addSymptom(Symptom symptom) {

        this.symptomArrayList.add(symptom);
    }
    public Condition withIdAndName(@NonNull String id, @NonNull String name) {
        this.conditionId = id;
        this.name = name;
        return (Condition) this;
    }

    public String getName() {
        return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@androidx.annotation.NonNull Parcel dest, int flags) {
        dest.writeString(conditionId);
        dest.writeString(name);
        dest.writeString(description);


    }

    public Condition(Parcel in) {
        conditionId = in.readString();
        name = in.readString();
        description = in.readString();
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
