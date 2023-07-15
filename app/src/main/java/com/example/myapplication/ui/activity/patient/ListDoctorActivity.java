package com.example.myapplication.ui.activity.patient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.myapplication.R;
import com.example.myapplication.adapter.DoctorListAdapter;
import com.example.myapplication.databinding.ActivityListDoctorBinding;
import com.example.myapplication.model.Condition;
import com.example.myapplication.model.Doctor;
import com.example.myapplication.model.Specialization;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ListDoctorActivity extends AppCompatActivity {

    ArrayList<Condition> conditions;
    Condition condition;
    Specialization specialization;
    FirebaseFirestore firestore;
    List<Doctor> doctorArrayList = new ArrayList<Doctor>();

    DoctorListAdapter doctorListAdapter ;
    private ActivityListDoctorBinding binding;

    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private ActivityListDoctorBinding activityListDoctorBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityListDoctorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        Bundle bundle = getIntent().getExtras();
        conditions = bundle.getParcelableArrayList("condition_list");
        condition = conditions.get(0);


        specialization = condition.getSpecializationObject();


        System.out.println(specialization.getSpecializationId());

        toolbar = binding.back.myToolbar;
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        recyclerView = binding.recyclerViewUsers;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        firestore = FirebaseFirestore.getInstance();
        doctorListAdapter  = new DoctorListAdapter(this, doctorArrayList);
        recyclerView.setAdapter(doctorListAdapter);
        doctorListAdapter.notifyDataSetChanged();

        firestore.collection("Doctors").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for (DocumentChange documentChange : value.getDocumentChanges()) {
                    if (documentChange.getType() == DocumentChange.Type.ADDED) {
                        QueryDocumentSnapshot doc = documentChange.getDocument();
                        String id = doc.getId();
                        Doctor doctor = doc.toObject(Doctor.class);
                        doctor.changeToObject(id).addOnCompleteListener(new OnCompleteListener<Doctor>() {
                            @Override
                            public void onComplete(@NonNull Task<Doctor> task) {
                                String idToMatch = condition.getSpecializationObject().getSpecializationId();
//                                System.out.println(idToMatch);
                                System.out.println(doctor.getSpecializationList());
                                for (Specialization spec : doctor.getSpecializationList()) {
                                    if (spec.getSpecializationId().equals(idToMatch)) {
                                        doctorArrayList.add(doctor);
                                        doctorListAdapter.notifyDataSetChanged();
                                        System.out.println(doctor.getSpecializationList());
                                    }
                                }
                            }
                        });
                    }
                }
            }
        });
    }
}