package com.example.myapplication.ui.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.ui.instance.Doctor;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.ktx.Firebase;

import java.util.Collection;

public class NewDoctorActivity extends AppCompatActivity {
    TextInputEditText editTextName, editTextAge, editTextNumber, editTextCategory;
    Button buttonSaveDoctor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_doctor);

        editTextAge = findViewById(R.id.et_age);
        editTextCategory = findViewById(R.id.et_category);
        editTextName = findViewById(R.id.et_name);
        editTextNumber = findViewById(R.id.et_number);
        buttonSaveDoctor = findViewById(R.id.btn_save_doctor);

        buttonSaveDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDoctor();
            }
        });
    }

    public void saveDoctor(){
        String name = editTextName.getText().toString();
        String age = editTextAge.getText().toString();
        String number = editTextNumber.getText().toString();
        String category = editTextCategory.getText().toString();

        if (name.trim().isEmpty() || age.trim().isEmpty() || number.trim().isEmpty() || category.trim().isEmpty()){
            Toast.makeText(this, "Please insert more detail", Toast.LENGTH_SHORT).show();
        }

        CollectionReference docRef = FirebaseFirestore.getInstance().collection("Doctors");
        docRef.add(new Doctor(name, age, category, number));
        Toast.makeText(this, "Added successful", Toast.LENGTH_SHORT).show();
        finish();
    }
}