package com.example.myapplication.ui.activity.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityVerifyBinding;
import com.example.myapplication.ui.activity.patient.PatientActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class VerifyActivity extends AppCompatActivity {
    private ActivityVerifyBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_verify);

        initializeListeners();
    }

    private void initializeListeners() {
        binding.btnVerified.setOnClickListener((v) -> {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            user.reload();
            if (user.isEmailVerified()) {
                Intent intent = new Intent(VerifyActivity.this, PatientActivity.class);
                startActivity(intent);
                finishAffinity();
            }
            else {
                Toast.makeText(this, "An error occurred. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
        binding.btnResend.setOnClickListener((v) -> {
            FirebaseAuth.getInstance().getCurrentUser().reload();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (!user.isEmailVerified()) {
                user.sendEmailVerification();
                Toast.makeText(this, "A new verification email was sent. Please check your inbox.", Toast.LENGTH_SHORT).show();
            }
        });
        binding.btnLogout.setOnClickListener((v) -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(VerifyActivity.this, LoginActivity.class);
            startActivity(intent);
            finishAffinity();
        });
    }
}