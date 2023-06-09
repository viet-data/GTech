package com.example.myapplication.ui.library;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.ConceptLibraryAdapter;
import com.example.myapplication.databinding.FragmentLibraryBinding;
import com.example.myapplication.model.Concept;
import com.example.myapplication.model.Condition;
import com.example.myapplication.model.Symptom;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class LibraryFragment extends Fragment {

    private FragmentLibraryBinding binding;
    private RecyclerView recyclerViewConcepts;
    private FirebaseFirestore firestore;
    private ConceptLibraryAdapter adapter;
    private List<Concept> conceptList;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        LibraryViewModel libraryViewModel =
                new ViewModelProvider(this).get(LibraryViewModel.class);

        binding = FragmentLibraryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerViewConcepts = root.findViewById(R.id.recycler_view_concepts);
        recyclerViewConcepts.setLayoutManager(new LinearLayoutManager(getContext()));
        firestore = FirebaseFirestore.getInstance();
        conceptList = new ArrayList<>();
        adapter = new ConceptLibraryAdapter(LibraryFragment.this, conceptList);
        recyclerViewConcepts.setAdapter(adapter);
        showData();
        return root;
    }

    private void showData() {
        firestore.collection("symptom").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for (DocumentChange documentChange : value.getDocumentChanges()) {
                    if (documentChange.getType() == DocumentChange.Type.ADDED) {
                        QueryDocumentSnapshot doc = documentChange.getDocument();
                        String id = doc.getId();
                        Symptom symptom = doc.toObject(Symptom.class)
                                .withIdAndName(id, doc.getString("name"));
                        conceptList.add(symptom);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });

        firestore.collection("condition").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for (DocumentChange documentChange : value.getDocumentChanges()) {
                    if (documentChange.getType() == DocumentChange.Type.ADDED) {
                        QueryDocumentSnapshot doc = documentChange.getDocument();
                        String id = doc.getId();
                        Condition condition = doc.toObject(Condition.class)
                                .withIdAndName(id, doc.getString("name"));
                        conceptList.add(condition);
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