package com.example.myapplication.ui.activity.admin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;
import com.example.myapplication.adapter.UserListAdapter;
import com.example.myapplication.model.Doctor;
import com.example.myapplication.model.User;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ListDoctorsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FirebaseFirestore database;
    UserListAdapter userListAdapter;
    ArrayList<User> users;
    CollectionReference docRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_doctors);


        Button buttonAddDoctor = findViewById(R.id.btn_add_doctor);
        buttonAddDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListDoctorsActivity.this, NewDoctorActivity.class));
            }
        });

        recyclerView = findViewById(R.id.doctorlist);

        database = FirebaseFirestore.getInstance();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        users = new ArrayList<>();
        userListAdapter = new UserListAdapter(ListDoctorsActivity.this, users);
        recyclerView.setAdapter(userListAdapter);

        database.collection("Users").orderBy("name", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        for(DocumentChange dc : value.getDocumentChanges()){
                            users.add(dc.getDocument().toObject(User.class));
                        }
                        userListAdapter.notifyDataSetChanged();
                    }
                });
    }
}