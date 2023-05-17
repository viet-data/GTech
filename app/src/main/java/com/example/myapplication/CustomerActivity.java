package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.databinding.ActivityCustomerBinding;

public class CustomerActivity extends AppCompatActivity {
    private Button logoutBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        ActivityCustomerBinding activityCustomerBinding = DataBindingUtil.setContentView(this, R.layout.activity_customer);
        CustomerViewModel customerViewModel = new CustomerViewModel();
        activityCustomerBinding.setCustomerViewModel(customerViewModel);
        logoutBtn = findViewById(R.id.btn_logout);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backActivity();
            }
        });
    }
    protected void backActivity(){
        Intent intent = new Intent(CustomerActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}