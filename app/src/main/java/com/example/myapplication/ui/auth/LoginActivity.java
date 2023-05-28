package com.example.myapplication.ui.auth;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityLoginBinding;
import com.example.myapplication.ui.MainActivity;
import com.example.myapplication.ui.admin.AdminActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false); // if you want user to wait for some process to finish,
        builder.setView(R.layout.layout_loading_dialog);
        AlertDialog dialog = builder.create();
        binding.btnLogin.setOnClickListener((View v) -> {
            strEmail = binding.edtEmail.getText().toString().trim();
            strPassword = binding.edtPassword.getText().toString().trim();
            if (strPassword.length() < 8 || !Patterns.EMAIL_ADDRESS.matcher(strEmail).matches()) {
                Toast.makeText(getApplicationContext(),
                        "Email or password is invalid",Toast.LENGTH_LONG).show();
            }
            else {
                dialog.show();
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.signInWithEmailAndPassword(strEmail, strPassword)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                if (auth.getCurrentUser().isEmailVerified()) {
                                    getUserAccessLevel(authResult.getUser().getUid());
                                    dialog.dismiss();
                                } else {
                                    dialog.dismiss();
                                    Intent intent = new Intent(LoginActivity.this, VerifyActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        })
                        .addOnFailureListener((task) -> {
                            dialog.dismiss();
                            Toast.makeText(LoginActivity.this,
                                    "Incorrect credential. Please try again.", Toast.LENGTH_SHORT).show();
                        });
            }
        });

        binding.btnNavigateSignup.setOnClickListener((View v) -> {
            Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
            startActivity(intent);
        });
    }
    private void getUserAccessLevel(String uid) {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        DocumentReference df = firestore.collection("Users").document(uid);
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                boolean userIsAdmin = documentSnapshot.getBoolean("is_admin");
                Intent intent;
                if (userIsAdmin) {
                    intent = new Intent(getApplicationContext(), AdminActivity.class);
                } else {
                    intent = new Intent(getApplicationContext(), MainActivity.class);
                }
                startActivity(intent);
                finish();
            }
        });
    }
}
