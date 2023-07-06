package com.example.myapplication.model;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Exclude;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Condition {
    @Exclude
    protected String conditionId;

    private String name;
    private String description;
    private Specialization specializationOb;

    private DocumentReference specialization;
    private ArrayList<DocumentReference> symptoms;
    private ArrayList<Symptom> symptomArrayList;



    public Condition() {}
    public Condition(String name, String description, DocumentReference specialization,ArrayList<DocumentReference> symptoms) {
        this.name = name;
        this.description = description;
        this.specialization = specialization;
        this.symptoms = symptoms;
        this.specialization.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                specializationOb = documentSnapshot.toObject(Specialization.class);
            }
        });


        List<Specialization> a = new ArrayList<>();
        this.specializationOb = specialization.get().getResult().toObject(Specialization.class);



        for (DocumentReference dR : symptoms){
            System.out.println(dR);
            Symptom symptom = dR.get().getResult().toObject(Symptom.class);
            symptomArrayList.add(symptom);
        }
    }
    private void change(){

    }

    public Specialization getSpecializationOb() {

        //System.out.println(specializationOb.getClass());
        return  specializationOb;
    }

    public DocumentReference getSpecialization() {
        return specialization;
    }




    public ArrayList<DocumentReference> getsymptoms(){
        return symptoms;
    }
    public ArrayList<Symptom> getSymptomArrayList(){
        return symptomArrayList;
    }










    public Condition(String name, String description, Specialization specialization) {
        this.name = name;
        this.description = description;
        this.specializationOb = specialization;
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

}
