package com.example.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Exclude;


import java.util.ArrayList;
import java.util.List;

public class Condition implements Parcelable {
    @Exclude
    protected String conditionId;
    private String name;
    private String description;
    private Specialization specializationObject;
    private List<Symptom> symptomList;
    private DocumentReference specialization;
    private List<DocumentReference> symptoms;

    public Condition() {
        symptomList = new ArrayList<>();
        symptoms = new ArrayList<>();
    }
    public Condition(String conditionId, String name, String description ){
        this.conditionId = conditionId;
        this.name = name;
        this.description = description;

    }
    public Condition(String conditionId, String name, String description, ArrayList<DocumentReference> symptoms){
        this.conditionId = conditionId;
        this.name = name;
        this.description = description;
        this.symptoms = symptoms;

    }

    public Task<Condition> changeToObject(String id) {
        symptomList = new ArrayList<>();
        System.out.println("list_____________________");
        System.out.println(symptoms);
        this.conditionId = id;

        List<Task<DocumentSnapshot>> tasks = new ArrayList<>();

        Task<DocumentSnapshot> specTask = specialization.get();
        tasks.add(specTask);

        for (DocumentReference dR : symptoms){
            Task<DocumentSnapshot> task = dR.get();
            tasks.add(task);
        }
        return Tasks.whenAllSuccess(tasks).continueWith(task -> {
            List<Object> snapshots = task.getResult();
            int i = 0;
            for (Object snapshot : snapshots) {
                DocumentSnapshot documentSnapshot = (DocumentSnapshot) snapshot;
                if (i == 0) {
                    specializationObject = documentSnapshot.toObject(Specialization.class);
                    specializationObject.setSpecializationId(documentSnapshot.getId());
                    i++;
                } else {
                    Symptom symp = documentSnapshot.toObject(Symptom.class);
                    symp.setSymptomId(documentSnapshot.getId());
                    symptomList.add(symp);
                }
            }
            return this;
        });
    }
//    public Condition updateToObject(String id){
//        symptomList = new ArrayList<>();
//        this.conditionId = id;
//        //System.out.println(this.specialization);
//        this.specialization.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                specializationObject = documentSnapshot.toObject(Specialization.class);
//                specializationObject.setSpecializationId(documentSnapshot.getId());
//            }
//        });
//
//        for (DocumentReference dR : this.symptoms){
//            //System.out.println(dR.getId());
//            dR.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                @Override
//                public void onSuccess(DocumentSnapshot documentSnapshot) {
//                    Symptom symptom = documentSnapshot.toObject(Symptom.class);
//
//                    symptom.setSymptomId(documentSnapshot.getId());
//
//                    symptomList.add(symptom);
//                }
//            });
//        }
//        return (Condition) this;
//    }

    public Specialization getSpecializationObject() {
        return  this.specializationObject;
    }

    public DocumentReference getSpecialization() {
        return specialization;
    }

    public void setSpecializationObject(Specialization specializationObj) {
        this.specializationObject = specializationObj;
    }

    public List<DocumentReference> getSymptoms(){
        return symptoms;
    }
    public List<Symptom> getSymptomList(){
        return symptomList;
    }

    @Exclude
    public String getConditionId() {
        return conditionId;
    }
    @Exclude
    public void setConditionId(String conditionId) {
        this.conditionId = conditionId;
    }
//    public Condition(String name, String description, Specialization specializationObj) {
//        this.name = name;
//        this.description = description;
//        this.specializationObject = specializationObj;
//    }
    public void addSymptom(Symptom symptom) {

        this.symptomList.add(symptom);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSymptomList(List<Symptom> symptomList) {
        this.symptomList = symptomList;
    }

    public void setSpecialization(DocumentReference specialization) {
        this.specialization = specialization;
    }

    public void setSymptoms(List<DocumentReference> symptoms) {
        this.symptoms = symptoms;
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

        dest.writeList(symptomList);
        dest.writeParcelable(specializationObject, flags);


    }

    public Condition(Parcel in) {
        conditionId = in.readString();
        name = in.readString();
        description = in.readString();
        symptomList = new ArrayList<>();
        symptoms = new ArrayList<>();
        in.readList(symptomList, Symptom.class.getClassLoader());
        specializationObject = in.readParcelable(Specialization.class.getClassLoader());
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