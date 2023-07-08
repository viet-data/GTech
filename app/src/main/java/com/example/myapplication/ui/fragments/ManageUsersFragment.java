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

import com.example.myapplication.R;
import com.example.myapplication.adapter.UserListAdapter;
import com.example.myapplication.databinding.FragmentLibraryBinding;
import com.example.myapplication.databinding.FragmentManageUsersBinding;
import com.example.myapplication.model.Condition;
import com.example.myapplication.model.User;
import com.example.myapplication.ui.activity.admin.AdminActivity;
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

public class ManageUsersFragment extends Fragment {
    private FragmentManageUsersBinding binding;
    private RecyclerView recyclerViewUsers;
    private FirebaseFirestore firestore;
    private UserListAdapter adapter;
    private List<User> users;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentManageUsersBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.btnLogout.setOnClickListener((v) -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            getActivity().finishAffinity();
        });


        recyclerViewUsers = binding.recyclerViewUsers;
        recyclerViewUsers.setLayoutManager(new LinearLayoutManager(getContext()));
        firestore = FirebaseFirestore.getInstance();

        users = new ArrayList<>();
        adapter = new UserListAdapter(ManageUsersFragment.this.getContext(), users);
        recyclerViewUsers.setAdapter(adapter);
        showData();
        return root;
    }

    private void showData() {
        firestore.collection("Users").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for (DocumentChange documentChange : value.getDocumentChanges()) {
                    if (documentChange.getType() == DocumentChange.Type.ADDED) {
                        QueryDocumentSnapshot doc = documentChange.getDocument();
                        String id = doc.getId();
                        User user = doc.toObject(User.class);
                        user.setUserId(id);
                        users.add(user);
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