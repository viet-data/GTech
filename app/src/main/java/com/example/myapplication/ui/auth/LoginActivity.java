package com.example.myapplication.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityLoginBinding;
import com.example.myapplication.ui.MainActivity;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    String strEmail;
    String strPassword;
//    private LoginViewModel loginViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
//        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        initializeListeners();
    }
    private void initializeListeners() {
        binding.btnLogin.setOnClickListener((View v) -> {
            strEmail = binding.edtEmail.getText().toString().trim();
            strPassword = binding.edtPassword.getText().toString().trim();
            if (!Patterns.EMAIL_ADDRESS.matcher(strEmail).matches() || !(strPassword.length() >= 8)) {
                Toast.makeText(LoginActivity.this,
                        "Email or password is invalid",Toast.LENGTH_SHORT);
                return;
            }
            FirebaseAuth auth = FirebaseAuth.getInstance();
            auth.signInWithEmailAndPassword(strEmail, strPassword)
                .addOnCompleteListener((task) -> {
                    if (task.isSuccessful()) {
                        if (auth.getCurrentUser().isEmailVerified()) {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Intent intent = new Intent(LoginActivity.this, VerifyActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this,
                                "Sign in failed. Please try again.", Toast.LENGTH_SHORT);
                    }
                });
        });

        binding.btnNavigateSignup.setOnClickListener((View v) -> {
            Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
            startActivity(intent);
        });
    }
}
