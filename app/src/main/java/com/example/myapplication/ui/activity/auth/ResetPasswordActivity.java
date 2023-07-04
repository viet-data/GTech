package com.example.myapplication.ui.activity.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityResetPasswordBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import org.checkerframework.checker.nullness.qual.NonNull;

public class ResetPasswordActivity extends AppCompatActivity {

    ActivityResetPasswordBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reset_password);

        initializeListeners();
    }
    private void initializeListeners() {
        FirebaseAuth fAuth = FirebaseAuth.getInstance();

        binding.btnSendReset.setOnClickListener((View v) -> {

            String emailAddress = binding.edtEmail.getText().toString().trim();
            if (TextUtils.isEmpty(emailAddress) || !Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
                Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
                return;
            }
            finish();
            fAuth.sendPasswordResetEmail(emailAddress)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ResetPasswordActivity.this,
                                        "Reset password email sent", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(ResetPasswordActivity.this,
                                        "An error occured. Please try again", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        });
    }
}