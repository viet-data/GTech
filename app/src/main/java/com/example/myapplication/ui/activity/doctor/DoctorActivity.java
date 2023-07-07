package com.example.myapplication.ui.activity.doctor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.databinding.ActivityDoctorBinding;
import com.example.myapplication.ui.activity.auth.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

public class DoctorActivity extends AppCompatActivity {
    ActivityDoctorBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDoctorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnLogout.setOnClickListener((v) -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(DoctorActivity.this, LoginActivity.class);
            startActivity(intent);
            finishAffinity();
        });
    }
}