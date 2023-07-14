package com.example.myapplication.ui.activity.admin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.myapplication.R;
import com.example.myapplication.adapter.SpecializationListAdapter;
import com.example.myapplication.databinding.ActivityAdminBinding;
import com.example.myapplication.databinding.ActivityDoctorBinding;
import com.example.myapplication.databinding.ActivityDoctorProfileBinding;
import com.example.myapplication.model.Doctor;
import com.example.myapplication.model.Specialization;
import com.example.myapplication.model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DoctorProfileActivity extends AppCompatActivity {
    private ActivityDoctorProfileBinding binding;
    private RecyclerView recyclerView;
    private FirebaseFirestore firestore;
    private List<Specialization> specializationList = new ArrayList<>();
    private SpecializationListAdapter adapter = new SpecializationListAdapter(this, specializationList);
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDoctorProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firestore = FirebaseFirestore.getInstance();


        Bundle bundle = getIntent().getExtras();
        User user = bundle.getParcelable("user");

        firestore.collection("Doctors").document(user.getUserId())
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                System.out.println(user.getUserId());
                Doctor doctor = documentSnapshot.toObject(Doctor.class);
                doctor.changeToObject(documentSnapshot.getId()).addOnSuccessListener(new OnSuccessListener<Doctor>() {
                    @Override
                    public void onSuccess(Doctor doctor) {
                        binding.tvName.setText(doctor.getName());
                        binding.tvPhone.setText(doctor.getPhone());
                        for (Specialization specialization : doctor.getSpecializationList()) {
                            specializationList.add(specialization);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
            }
        });

//        firestore.collection("Doctors").document(user.getUserId()).addSnapshotListener(new);
        binding.backToolbar.myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        recyclerView = binding.recyclerViewSpecs;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}