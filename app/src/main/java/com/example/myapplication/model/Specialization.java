package com.example.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.GridLayout;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.Exclude;

public class Specialization implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return "Specialization{" +
                "specializationId='" + specializationId + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public void writeToParcel(@androidx.annotation.NonNull Parcel dest, int flags) {
        dest.writeString(specializationId);
        dest.writeString(name);
        dest.writeString(description);
    }

    public Specialization(Parcel in) {
        specializationId = in.readString();
        name = in.readString();
        description = in.readString();
    }

    public static final Creator<Specialization> CREATOR = new Creator<Specialization>() {
        @Override
        public Specialization createFromParcel(Parcel source) {
            return new Specialization(source);
        }

        @Override
        public Specialization[] newArray(int size) {
            return new Specialization[size];
        }
    };
}
