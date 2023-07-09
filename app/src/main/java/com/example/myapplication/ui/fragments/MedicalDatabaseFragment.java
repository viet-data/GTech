package com.example.myapplication.ui.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.Interface.ClickConditionInterface;
import com.example.myapplication.R;
import com.example.myapplication.adapter.ConditionLibraryAdapter;
import com.example.myapplication.databinding.FragmentLibraryBinding;
import com.example.myapplication.databinding.FragmentMedicalDatabaseBinding;
import com.example.myapplication.model.Condition;
import com.example.myapplication.ui.activity.admin.AddConditionActivity;
import com.example.myapplication.ui.activity.patient.ConditionDetailsActivity;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MedicalDatabaseFragment extends Fragment implements ClickConditionInterface {
    private FragmentMedicalDatabaseBinding binding;
    private RecyclerView recyclerView;
    private FirebaseFirestore firestore;
    private ConditionLibraryAdapter adapter;
    private List<Condition> conditionList;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentMedicalDatabaseBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        recyclerView = binding.recyclerViewConditions;


        binding.btnAddCondition.setOnClickListener((v) -> {
            Intent intent = new Intent(this.getContext(), AddConditionActivity.class);
            startActivity(intent);
        });

        recyclerView = binding.recyclerViewConditions;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        firestore = FirebaseFirestore.getInstance();
        conditionList = new ArrayList<>();
        adapter = new ConditionLibraryAdapter(this.getContext(), conditionList, this);
        recyclerView.setAdapter(adapter);
        showData();
        return root;
    }

    private void showData() {
        firestore.collection("Conditions").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for (DocumentChange documentChange : value.getDocumentChanges()) {
                    if (documentChange.getType() == DocumentChange.Type.ADDED) {
                        QueryDocumentSnapshot doc = documentChange.getDocument();
                        String id = doc.getId();
                        Condition condition = doc.toObject(Condition.class).changeToObject(id);
                        condition.setConditionId(id);
                        conditionList.add(condition);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemClick(int position) {

        Condition condition = conditionList.get(position);
        Intent intent = new Intent(getContext(), ConditionDetailsActivity.class);
        intent.putExtra("conditionId", condition.getConditionId());
        intent.putExtra("specialization", condition.getSpecializationOb());
        intent.putExtra("condition",condition);
        startActivity(intent);
    }
}