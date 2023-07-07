package com.example.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.PropertyName;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;

public class Condition implements Parcelable {
    @Exclude
    protected String conditionId;

    private String name;
    private String description;
    @PropertyName("specialization")
    private DocumentReference specializationDocRef;
    @PropertyName("symptoms")
    private ArrayList<DocumentReference> symptomsDocRef;
    @Exclude
    private Specialization specialization;
    @Exclude
    private ArrayList<Symptom> symptoms = new ArrayList<>();

    public Condition() {}
    public Condition(String conditionId, String name, String description){
        this.conditionId = conditionId;
        this.name = name;
        this.description = description;

    }
    public Condition changeToObject(String id){
        this.conditionId = id;
        //System.out.println(this.specialization);
        this.specializationDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                specialization = documentSnapshot.toObject(Specialization.class);
            }
        });

        for (DocumentReference dR : this.symptomsDocRef){
            dR.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    symptoms.add(documentSnapshot.toObject(Symptom.class).withId(documentSnapshot.getId()));
                }
            });
        }
        return (Condition) this;
    }
    @Exclude
    public Specialization getSpecialization() {
        return  this.specialization;
    }

    @PropertyName("specialization")
    public DocumentReference getSpecializationDocRef() {
        return specializationDocRef;
    }
    @Exclude
    public void setSpecialization(Specialization anSpecialization) {
        this.specialization = anSpecialization;
    }
    @PropertyName("symptoms")
    public ArrayList<DocumentReference> getSymptomsDocRef(){
        return symptomsDocRef;
    }
    @Exclude
    public ArrayList<Symptom> getSymptoms(){
        return symptoms;
    }

    public String getConditionId() {
        return conditionId;
    }

    public Condition(String name, String description, Specialization specializationOb) {
        this.name = name;
        this.description = description;
        this.specialization = specializationOb;
    }



    public Condition withId_NameAndSymptoms(@NonNull String id, @NonNull String name) {
        this.conditionId = id;
        this.name = name;
        return (Condition) this;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
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
