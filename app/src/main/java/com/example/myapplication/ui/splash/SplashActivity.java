package com.example.myapplication.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.ui.MainActivity;
import com.example.myapplication.ui.admin.AdminActivity;
import com.example.myapplication.ui.auth.LoginActivity;
import com.example.myapplication.ui.auth.VerifyActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                nextActivity();
            }
        }, 500);
    }
    private void nextActivity() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Intent intent;
        if (user == null) {
            intent = new Intent(this, LoginActivity.class);
        }
        else if (!user.isEmailVerified()) {
            intent = new Intent(this, VerifyActivity.class);
        }
        else {
            String uid = user.getUid();
            FirebaseFirestore fStore = FirebaseFirestore.getInstance();
            DocumentReference df = fStore.collection("Users").document(uid);
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
            return;
        }
        startActivity(intent);
        finish();
    }
}