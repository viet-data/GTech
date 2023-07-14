package com.example.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.PropertyName;

import java.util.ArrayList;
import java.util.List;

public class Doctor implements Parcelable {
    private String doctorId;
    private String name;
    private String description;
    private String phone;
    private List<Specialization> specializationList;
    private List<DocumentReference> specialization;

    public Doctor() {
        super();
        // no-args constructor for firestore
    }

    public Doctor(String name, String description, String phone) {
        this.name = name;
        this.description = description;
        this.phone = phone;
    }

    @Exclude
    public String getDoctorId() {
        return doctorId;
    }

    @Exclude
    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
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
    @PropertyName("specialization")
    public List<DocumentReference> getSpecialization() {
        return specialization;
    }

    @PropertyName("specialization")
    public void setSpecialization(List<DocumentReference> specialization) {
        this.specialization = specialization;
    }

    public Task<Doctor> changeToObject(String id){
        specializationList = new ArrayList<>();
        this.doctorId = id;
        //System.out.println(this.specialization);
        List<Task<DocumentSnapshot>> tasks = new ArrayList<>();
        for (DocumentReference dR : this.specialization){
            Task<DocumentSnapshot> task = dR.get();
            tasks.add(task);
            //System.out.println(dR.getId());
        }
        return Tasks.whenAllSuccess(tasks).continueWith(task -> {
            List<Object> snapshots = task.getResult();
            for (Object snapshot : snapshots) {
                    DocumentSnapshot documentSnapshot = (DocumentSnapshot) snapshot;
                    Specialization spec = documentSnapshot.toObject(Specialization.class);
                    spec.setSpecializationId(documentSnapshot.getId());
                    specializationList.add(spec);
            }
            return this;
        });
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(phone);
        dest.writeList(specializationList);
    }

    public Doctor(Parcel in) {

        name = in.readString();
        description = in.readString();
        phone = in.readString();
        List<Specialization> specializationList = new ArrayList<>();
        in.readList(specializationList, Specialization.class.getClassLoader());

    }

    public static final Creator<Doctor> CREATOR = new Creator<Doctor>() {
        @Override
        public Doctor createFromParcel(Parcel source) {
            return new Doctor(source);
        }

        @Override
        public Doctor[] newArray(int size) {
            return new Doctor[size];
        }
    };
}
