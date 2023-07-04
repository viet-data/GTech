package com.example.myapplication.ui.activity.patient;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityPatientBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class PatientActivity extends AppCompatActivity {

    private ActivityPatientBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPatientBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        NavController navController =
                Navigation.findNavController(this, R.id.nav_host_fragment_activity_patient);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }
}