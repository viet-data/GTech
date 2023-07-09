package com.example.myapplication.ui.activity.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityAddDoctorBinding;
import com.example.myapplication.databinding.ActivityUserProfileBinding;
import com.example.myapplication.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserProfileActivity extends AppCompatActivity {
    private ActivityUserProfileBinding binding;
    private FirebaseAuth auth;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();

        Bundle bundle = getIntent().getExtras();
        user = bundle.getParcelable("user");
        binding.txtName.setText(user.getFullName());
        binding.txtDob.setText(user.getDateOfBirth());
        binding.txtAccessLevel.setText(user.getUserLevel());

        binding.myToolbar.myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.btnToDoctor.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddDoctorActivity.class);
            bundle.putParcelable("user", user);
            intent.putExtras(bundle);
            startActivity(intent);
        });
    }
}