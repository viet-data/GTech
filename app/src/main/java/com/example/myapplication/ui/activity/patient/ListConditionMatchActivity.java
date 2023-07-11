package com.example.myapplication.ui.activity.patient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.widget.Toolbar;

import com.example.myapplication.utils.ClickConditionInterface;
import com.example.myapplication.R;
import com.example.myapplication.adapter.ConditionLibraryAdapter;
import com.example.myapplication.databinding.ActivityListConditionMatchBinding;
import com.example.myapplication.model.Condition;

import java.util.ArrayList;

public class ListConditionMatchActivity extends AppCompatActivity implements ClickConditionInterface{
    ArrayList<Condition> conditions;
    ConditionLibraryAdapter conditionMatchAdapter;
    RecyclerView recyclerView;
    Toolbar toolbar;
    ActivityListConditionMatchBinding binding;

    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListConditionMatchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle bundle = getIntent().getExtras();
        conditions = bundle.getParcelableArrayList("condition_list");
        toolbar = binding.backToolbar.myToolbar;
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        button = findViewById(R.id.btn_show_list);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListConditionMatchActivity.this, ListDoctorActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("condition_list", conditions);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


        recyclerView = binding.conditionMatch;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //conditions = new ArrayList<>();
        conditionMatchAdapter = new ConditionLibraryAdapter(ListConditionMatchActivity.this, conditions, this);
        recyclerView.setAdapter(conditionMatchAdapter);
    }


    @Override
    public void onItemClick(int position) {
        Condition condition = conditions.get(position);
        Intent intent = new Intent(this, ConditionDetailsActivity.class);
        intent.putExtra("conditionId", condition.getConditionId());
        intent.putExtra("specialization", condition.getSpecializationObject());
        intent.putExtra("condition",condition);
        startActivity(intent);
    }
}
