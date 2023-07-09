package com.example.myapplication.model;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.PropertyName;

import java.util.ArrayList;
import java.util.List;

public class Doctor {
    private String doctorId;
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
    public List<DocumentReference> getSpecializations() {
        return specializations;
    }

    @PropertyName("specialization")
    public void setSpecializations(List<DocumentReference> specializations) {
        this.specializations = specializations;
    }

    public Doctor changeToObject(String id){
        specializationList = new ArrayList<>();
        this.doctorId = id;
        //System.out.println(this.specialization);


        for (DocumentReference dR : this.specializations){
            //System.out.println(dR.getId());
            dR.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    Specialization spec = documentSnapshot.toObject(Specialization.class);
                    spec.setSpecializationId(documentSnapshot.getId());
                    specializationList.add(spec);
                }
            });
        }
        return (Doctor) this;
    }
}
