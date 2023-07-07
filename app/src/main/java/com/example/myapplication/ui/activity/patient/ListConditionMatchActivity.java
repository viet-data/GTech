package com.example.myapplication.ui.activity.patient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.widget.Toolbar;

import com.example.myapplication.R;
import com.example.myapplication.adapter.ConditionMatchAdapter;
import com.example.myapplication.adapter.UserListAdapter;
import com.example.myapplication.databinding.ActivityDiagnosisBinding;
import com.example.myapplication.databinding.ActivityListConditionMatchBinding;
import com.example.myapplication.model.Condition;
import com.example.myapplication.model.User;
import com.example.myapplication.ui.activity.admin.ListDoctorsActivity;

import java.util.ArrayList;

public class ListConditionMatchActivity extends AppCompatActivity {
    ArrayList<Condition> conditions;
    ConditionMatchAdapter conditionMatchAdapter;
    RecyclerView recyclerView;
    Toolbar toolbar;

    ActivityListConditionMatchBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListConditionMatchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle bundle = getIntent().getExtras();
        conditions = bundle.getParcelableArrayList("condition_list");
        toolbar = findViewById(R.id.back_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        recyclerView = findViewById(R.id.condition_match);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //conditions = new ArrayList<>();
        conditionMatchAdapter = new ConditionMatchAdapter(ListConditionMatchActivity.this, conditions);
        recyclerView.setAdapter(conditionMatchAdapter);
    }
}