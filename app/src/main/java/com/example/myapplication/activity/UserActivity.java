package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;
import com.example.myapplication.viewmodel.UserViewModel;
import com.example.myapplication.databinding.ActivityUserBinding;

public class UserActivity extends AppCompatActivity {
    private Button logoutBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ActivityUserBinding activityUserBinding = DataBindingUtil.setContentView(this, R.layout.activity_user);
        UserViewModel userViewModel = new UserViewModel();
        activityUserBinding.setUserViewModel(userViewModel);
        logoutBtn = findViewById(R.id.btn_user_logout);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backActivity();
            }
        });
    }
    protected void backActivity(){
        Intent intent = new Intent(UserActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}