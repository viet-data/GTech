package com.example.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.Exclude;


public class Symptom implements Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@androidx.annotation.NonNull Parcel dest, int flags) {
        dest.writeString(symptomId);
        dest.writeString(description);


    }

    public Symptom(Parcel in) {
        symptomId = in.readString();
        description = in.readString();
    }

    public static final Creator<Symptom> CREATOR = new Creator<Symptom>() {
        @Override
        public Symptom createFromParcel(Parcel source) {
            return new Symptom(source);
        }

        @Override
        public Symptom[] newArray(int size) {
            return new Symptom[size];
        }
    };
}
