package com.example.myapplication.ui.activity.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityAdminBinding;
import com.example.myapplication.ui.activity.auth.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class AdminActivity extends AppCompatActivity {

    private ActivityAdminBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_admin);
        NavigationUI.setupWithNavController(binding.navView, navController);

        binding.btnLogout.setOnClickListener((v) -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(AdminActivity.this, LoginActivity.class);
            startActivity(intent);
            finishAffinity();
        });

        binding.btnListdoctors.setOnClickListener((v) -> {
            Intent intent = new Intent(AdminActivity.this, ListDoctorsActivity.class);
            startActivity(intent);
            //finishAffinity();
        });
    }

}