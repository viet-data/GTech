package com.example.myapplication.ui.activity.patient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.DoctorListAdapter;
import com.example.myapplication.databinding.ActivitySpecializationDetailsBinding;
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

public class SpecializationDetailsActivity extends AppCompatActivity {
    ActivitySpecializationDetailsBinding binding;
    RecyclerView recyclerView;
    FirebaseFirestore firestore;
    DoctorListAdapter doctorListAdapter;
    List<Doctor> doctorArrayList = new ArrayList<>();
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialization_details);

        Specialization specialization = getIntent().getParcelableExtra("specialization");

        TextView tvName = findViewById(R.id.tv_name);
        tvName.setText(specialization.getName());

        TextView tvDes = findViewById(R.id.tv_des);
        tvDes.setText(specialization.getDescription());

        toolbar = findViewById(R.id.back_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        recyclerView = findViewById(R.id.rcv_spec);
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
                                String idToMatch = specialization.getSpecializationId();
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