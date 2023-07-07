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
import java.util.List;

public class Condition implements Parcelable {
    @Exclude
    private String conditionId;

    private String name;
    private String description;
    private Specialization specialization;
    private List<Symptom> symptoms;

    public Condition() {}

    public Condition(String name, String description, Specialization specialization) {
        this.name = name;
        this.description = description;
        this.specialization = specialization;
    }

    public Condition withId(@NonNull String id) {
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

    public List<Symptom> getSymptoms() {
        return symptoms;
    }
}
