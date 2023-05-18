package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.viewmodel.ItmanagerViewModel;
import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityItmanagerBinding;

public class ItmanagerActivity extends AppCompatActivity {
    private Button logoutBtn;
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
    }
    protected void backActivity(){
        Intent intent = new Intent(ItmanagerActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}