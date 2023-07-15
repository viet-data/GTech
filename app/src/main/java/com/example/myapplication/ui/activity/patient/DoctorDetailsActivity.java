package com.example.myapplication.ui.activity.patient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.SpecializationListAdapter;
import com.example.myapplication.model.Doctor;

import org.w3c.dom.Text;

public class DoctorDetailsActivity extends AppCompatActivity {
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_details);

        toolbar = findViewById(R.id.back_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Bundle bundle = getIntent().getExtras();
        Doctor doctor = bundle.getParcelable("doctor");

        TextView tvName = findViewById(R.id.doctor_layout).findViewById(R.id.tv_name);
        tvName.setText(doctor.getName());

        TextView tvPhone = findViewById(R.id.doctor_layout).findViewById(R.id.tv_phone);
        tvPhone.setText(doctor.getPhone());

        RecyclerView recyclerView = findViewById(R.id.recycler_view_specs);
        SpecializationListAdapter specializationListAdapter = new SpecializationListAdapter(this, doctor.getSpecializationList());
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(specializationListAdapter);

    }
}