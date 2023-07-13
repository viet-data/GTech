package com.example.myapplication.ui.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.adapter.ConditionLibraryAdapter;
import com.example.myapplication.adapter.SpecializationListAdapter;
import com.example.myapplication.databinding.FragmentDoctorProfileBinding;
import com.example.myapplication.model.Condition;
import com.example.myapplication.model.Specialization;
import com.example.myapplication.ui.activity.auth.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DoctorProfileFragment extends Fragment {
    private FragmentDoctorProfileBinding binding;
    private RecyclerView recyclerView;
    private FirebaseFirestore firestore;
    private List<Specialization> specializationList;
    private SpecializationListAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDoctorProfileBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();

        binding.btnLogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            getActivity().finishAffinity();
        });

        recyclerView = binding.recyclerViewSpecs;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        firestore = FirebaseFirestore.getInstance();
        specializationList = new ArrayList<>();
        adapter = new SpecializationListAdapter(this.getContext(), specializationList);
        recyclerView.setAdapter(adapter);
        showData();
        return root;
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