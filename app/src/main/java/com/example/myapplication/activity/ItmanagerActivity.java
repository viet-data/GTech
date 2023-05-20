package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.adapter.ITmangerAdapter;
import com.example.myapplication.instance.Doctor;
import com.example.myapplication.viewmodel.ItmanagerViewModel;
import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityItmanagerBinding;

import java.util.ArrayList;
import java.util.List;

public class ItmanagerActivity extends AppCompatActivity {
    private Button logoutBtn;
    private RecyclerView rvItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itmanager);
        ActivityItmanagerBinding activityItmanagerBinding = DataBindingUtil.setContentView(this, R.layout.activity_itmanager);
        ItmanagerViewModel itmanagerViewModel = new ItmanagerViewModel();
        activityItmanagerBinding.setItmanagerViewModel(itmanagerViewModel);
        logoutBtn = findViewById(R.id.btn_itmanager_logout);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backActivity();
            }
        });

        List<Doctor> doctors = new ArrayList<>();
        doctors.add(new Doctor("Tuan Anh"));
        doctors.add(new Doctor("Dat"));
        doctors.add(new Doctor("Huy"));
        doctors.add(new Doctor("Sang"));
        doctors.add(new Doctor("Trang"));
        doctors.add(new Doctor("Viet"));

        rvItems = (RecyclerView) findViewById(R.id.rv_items);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvItems.setLayoutManager(layoutManager);
        rvItems.setHasFixedSize(true);
        rvItems.setAdapter(new ITmangerAdapter(this,doctors));

    }
    protected void backActivity(){
        Intent intent = new Intent(ItmanagerActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}