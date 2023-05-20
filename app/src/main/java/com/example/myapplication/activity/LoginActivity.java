package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableField;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.adapter.LoginAdapter;
import com.example.myapplication.instance.LoginType;
import com.example.myapplication.viewmodel.LoginViewModel;
import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private Button loginBtn;
    private TextView registerBtn;
    private ObservableField<Boolean> isSuccess;
    private Spinner loginSpinner;
    private LoginAdapter loginAdapter;
    private String loginTypeName;
    private String activityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        LoginViewModel loginViewModel = new LoginViewModel();
        activityMainBinding.setLoginViewModel(loginViewModel);

        loginSpinner = findViewById(R.id.login_spinner);
        loginAdapter = new LoginAdapter(this, R.layout.login_item_selected, getAllLoginTypes());
        loginSpinner.setAdapter(loginAdapter);
        loginSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(LoginActivity.this, loginAdapter.getItem(position).getName(), Toast.LENGTH_SHORT).show();
                loginTypeName = loginAdapter.getItem(position).getName();
                loginViewModel.refresh();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });


        loginBtn = findViewById(R.id.btn_login);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSuccess = loginViewModel.isValidLogin();
                activityName = loginTypeName;
                if (isSuccess.get()) {nextActivity(activityName);}
            }
        });

        registerBtn = findViewById(R.id.btn_register);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityName = "Register";
                nextActivity(activityName);
            }
        });
    }

    protected List<LoginType> getAllLoginTypes(){
        List<LoginType> allLoginTypes = new ArrayList<>();
        allLoginTypes.add(new LoginType("Login by User"));
        allLoginTypes.add(new LoginType("Login by IT Manager"));
        return allLoginTypes;
    }

    protected void nextActivity(String activityName){
        Boolean isUser = activityName.matches(new String("Login by User"));
        Boolean isItmanager = activityName.matches(new String("Login by IT Manager"));
        Boolean isRegister = activityName.matches(new String("Register"));

        Intent intent;
        if (isUser) {
            intent = new Intent(LoginActivity.this, UserActivity.class);
            startActivity(intent);
        } else if (isItmanager) {
            intent = new Intent(LoginActivity.this, ItmanagerActivity.class);
            startActivity(intent);
        } else if (isRegister) {
            intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        }
    }
}