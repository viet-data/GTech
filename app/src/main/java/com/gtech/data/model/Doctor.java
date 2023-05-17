package com.gtech.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// Sample Doctor Entity Component

@Entity(tableName = "doctor")
public class Doctor {
    @PrimaryKey(autoGenerate = true)
    public int doctorId;

    @ColumnInfo(name = "doc_name")
    public String doctorName;

    @ColumnInfo(name = "phone_no")
    public String phoneNumber;

    @ColumnInfo(name = "email")
    public String emailAddress;
    @ColumnInfo(name = "description")
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
