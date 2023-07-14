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
import com.example.myapplication.model.Specialization;
import com.example.myapplication.model.User;
import com.google.firebase.firestore.DocumentChange;
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
    private List<Specialization> specializationList;
    private SpecializationListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDoctorProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle bundle = getIntent().getExtras();
        User user = bundle.getParcelable("user");

//        firestore.collection("Doctors").document(user.getUserId()).addSnapshotListener(new);
        binding.backToolbar.myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        recyclerView = binding.recyclerViewSpecs;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        firestore = FirebaseFirestore.getInstance();
        specializationList = new ArrayList<>();
        adapter = new SpecializationListAdapter(this, specializationList);
        recyclerView.setAdapter(adapter);
//        showData();
    }
    private void showData() {
        firestore.collection("Specializations").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for (DocumentChange documentChange : value.getDocumentChanges()) {
                    if (documentChange.getType() == DocumentChange.Type.ADDED) {
                        QueryDocumentSnapshot doc = documentChange.getDocument();
                        String id = doc.getId();
                        Specialization spec = doc.toObject(Specialization.class);
                        spec.setSpecializationId(id);
                        specializationList.add(spec);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }
}