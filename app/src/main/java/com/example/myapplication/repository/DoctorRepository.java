package com.example.myapplication.repository;

import com.example.myapplication.model.Doctor;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class DoctorRepository {
    private FirebaseFirestore firestore;
    private CollectionReference docRef;

    public DoctorRepository() {
        firestore = FirebaseFirestore.getInstance();
        docRef = firestore.collection("Doctors");
    }

    public void addDoctor(Doctor doctor) {
        // TODO: complete
    }

}
