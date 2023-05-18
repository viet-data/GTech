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
import com.example.myapplication.R;
import com.example.myapplication.viewmodel.RegisterViewModel;
import com.example.myapplication.databinding.ActivityRegisterBinding;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {
    private Button registerBtn;
    private TextView loginBtn;
    private ObservableField<Boolean> isSuccess;
    private Spinner registerSpinner;
    private LoginAdapter registerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ActivityRegisterBinding activityRegisterBinding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        RegisterViewModel registerViewModel = new RegisterViewModel();
        activityRegisterBinding.setRegisterViewModel(registerViewModel);

        registerSpinner = findViewById(R.id.register_spinner);
        registerAdapter = new LoginAdapter(this, R.layout.register_item_selected, getAllRegisterTypes());
        registerSpinner.setAdapter(registerAdapter);
        registerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(RegisterActivity.this, registerAdapter.getItem(position).getName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });


        registerBtn = findViewById(R.id.btn_register_in_register);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSuccess = registerViewModel.isValidRegister();
                if (isSuccess.get()) {backActivity();}
            }
        });

        loginBtn = findViewById(R.id.btn_login_in_register);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backActivity();
            }
        });
    }

    protected List<LoginType> getAllRegisterTypes(){
        List<LoginType> allRegisterTypes = new ArrayList<>();
        allRegisterTypes.add(new LoginType("Register by User"));
        allRegisterTypes.add(new LoginType("Register by IT Manager"));
        return allRegisterTypes;
    }

    protected void backActivity(){
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }

}