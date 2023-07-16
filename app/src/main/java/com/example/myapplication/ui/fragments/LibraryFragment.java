package com.example.myapplication.ui.fragments;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.utils.ClickConditionInterface;
import com.example.myapplication.adapter.ConditionLibraryAdapter;
import com.example.myapplication.databinding.FragmentLibraryBinding;
import com.example.myapplication.model.Condition;
import com.example.myapplication.ui.activity.patient.ConditionDetailsActivity;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class LibraryFragment extends Fragment implements ClickConditionInterface {

    private FragmentLibraryBinding binding;
    private RecyclerView recyclerViewConcepts;
    private FirebaseFirestore firestore;
    private ConditionLibraryAdapter adapter;
    private List<Condition> conditionList;
    private SearchView searchView;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentLibraryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        searchView = (SearchView) binding.searchView;
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        recyclerViewConcepts = binding.recyclerViewConditions;
        recyclerViewConcepts.setLayoutManager(new LinearLayoutManager(getContext()));
        firestore = FirebaseFirestore.getInstance();
        conditionList = new ArrayList<>();
        adapter = new ConditionLibraryAdapter(this.getContext(), conditionList, this);
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
                        Condition condition = doc.toObject(Condition.class);
                        condition.changeToObject(id).addOnSuccessListener(cond -> {
                            conditionList.add(condition);
                            System.out.println("________________________________");
                            System.out.println(condition.getSymptomList());
                            adapter.notifyDataSetChanged();
                        });
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
        intent.putExtra("specialization", condition.getSpecializationObject());
        intent.putExtra("condition",condition);
        startActivity(intent);
    }

}