package com.gtech.domain.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

// Sample Doctor Entity Component

@Entity(tableName = "doctor")
public class Doctor {
    @PrimaryKey(autoGenerate = true)
    public int doctorId;

    public String doctorName;
    public String phoneNumber;
    public String emailAddress;
    public String doctorDescription;

    public Doctor(String doctorName, String phoneNumber, String emailAddress, String doctorDescription) {
        this.doctorName = doctorName;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.doctorDescription = doctorDescription;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getDoctorDescription() {
        return doctorDescription;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }
}
