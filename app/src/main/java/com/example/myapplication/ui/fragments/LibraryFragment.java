package com.example.myapplication.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.ConditionLibraryAdapter;
import com.example.myapplication.databinding.FragmentLibraryBinding;
import com.example.myapplication.model.Condition;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class LibraryFragment extends Fragment {

    private FragmentLibraryBinding binding;
    private RecyclerView recyclerViewConcepts;
    private FirebaseFirestore firestore;
    private ConditionLibraryAdapter adapter;
    private List<Condition> conditionList;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentLibraryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerViewConcepts = root.findViewById(R.id.recycler_view_conditions);
        recyclerViewConcepts.setLayoutManager(new LinearLayoutManager(getContext()));
        firestore = FirebaseFirestore.getInstance();
        conditionList = new ArrayList<>();
        adapter = new ConditionLibraryAdapter(LibraryFragment.this, conditionList);
        recyclerViewConcepts.setAdapter(adapter);
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
                        Condition condition = doc.toObject(Condition.class)
                                .withIdAndName(id, doc.getString("name"));
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
}