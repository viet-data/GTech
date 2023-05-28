package com.example.myapplication.ui.auth;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivitySignupBinding;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {
    ActivitySignupBinding binding;
    com.google.android.material.textfield.TextInputEditText edtDob;
    com.google.android.material.textfield.TextInputEditText edtName;
    com.google.android.material.textfield.TextInputEditText edtPassword;
    com.google.android.material.textfield.TextInputEditText edtEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup);

        edtDob = binding.edtDob;
        edtName = binding.edtName;
        edtPassword = binding.edtPassword;
        edtEmail = binding.edtEmail;


        initializeListeners();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false); // if you want user to wait for some process to finish,
        builder.setView(R.layout.layout_loading_dialog);
        AlertDialog dialog = builder.create();

        FirebaseFirestore fStore = FirebaseFirestore.getInstance();
        binding.btnSignup.setOnClickListener((view) -> {
            String strEmail = edtEmail.getText().toString().trim();
            String strPassword = edtPassword.getText().toString().trim();
            String strName = edtName.getText().toString().trim();
            String strDob = edtDob.getText().toString();
            if (Patterns.EMAIL_ADDRESS.matcher(strEmail).matches() && strPassword.length() >= 8) {
                dialog.show();
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.createUserWithEmailAndPassword(strEmail, strPassword)
                        .addOnCompleteListener((task) -> {
                            if (task.isSuccessful()) {
                                FirebaseUser user = auth.getCurrentUser();
                                user.sendEmailVerification();

                                DocumentReference df = fStore.collection("Users").document(user.getUid());
                                Map<String, Object> userInfo = new HashMap<>();
                                userInfo.put("full_name", strName);
                                userInfo.put("date_of_birth", strDob);
                                userInfo.put("is_admin", false);
                                df.set(userInfo);

                                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finishAffinity();
                                Toast.makeText(getApplicationContext(),
                                        "A verification email has been sent. Verify your email to sign in.",
                                        Toast.LENGTH_SHORT).show();
                                FirebaseAuth.getInstance().signOut();
                            } else {
                                dialog.dismiss();
                                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finishAffinity();
                                Toast.makeText(getApplicationContext(), "Sign up failed. Please try agains.",
                                        Toast.LENGTH_SHORT).show();

                            }
                        });
            } else {
                Toast.makeText(SignupActivity.this, "Invalid email or password.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void navigateLogin() {
        finish();
    }
    private void initializeListeners() {
        edtDob.addTextChangedListener(dobWatcher);
        edtDob.addTextChangedListener(loginValid);
        edtName.addTextChangedListener(loginValid);
        edtPassword.addTextChangedListener(loginValid);
        edtEmail.addTextChangedListener(loginValid);
        binding.checkPolicy
                .addOnCheckedStateChangedListener(new MaterialCheckBox.OnCheckedStateChangedListener() {
            @Override
            public void onCheckedStateChangedListener(@NonNull MaterialCheckBox checkBox, int state) {
                loginValid.onTextChanged(null, 0,0,0);
            }
        });
    }
    TextWatcher loginValid = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

        @Override
        public void afterTextChanged(Editable s) { }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String email = edtEmail.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();
            String name = edtName.getText().toString().trim();
            String date = edtDob.getText().toString();
            boolean policy = binding.checkPolicy.isChecked();
            SimpleDateFormat curFormatter = new SimpleDateFormat("dd/MM/yyyy");
            Date dob;

            boolean enable = false;
            try {
                dob = curFormatter.parse(date);
                if (dob.before(curFormatter.parse("01/01/1900"))
                || dob.after(curFormatter.parse("31/12/2100"))) {
                    binding.btnSignup.setEnabled(false);
                    return;
                }
            } catch (ParseException pe) {
                binding.btnSignup.setEnabled(false);
                return;
            }
            Date now = new Date();
            // dob string in the form of "01/01/2000"

            long timeDifference
                    = now.getTime() - dob.getTime();
            long age
                    = (timeDifference
                    / (1000L * 60 * 60 * 24 * 365));
            enable =
                    !TextUtils.isEmpty(email)
                    && !TextUtils.isEmpty(password)
                    && age >= 16
                    && !TextUtils.isEmpty(name)
                    && policy;
            binding.btnSignup.setEnabled(enable);
        }
    };

    // Formatter for date textedit
    TextWatcher dobWatcher = new TextWatcher() {
        private String current = "";
        private String dateFormat = "ddmmyyyy";
        private Calendar cal = Calendar.getInstance();

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!s.toString().equals(current)) {
                String clean = s.toString().replaceAll("[^\\d.]", "");
                String cleanC = current.replaceAll("[^\\d.]", "");

                int cl = clean.length();
                int sel = cl;
                for (int i = 2; i <= cl && i < 6; i += 2) {
                    sel++;
                }
                //Fix for pressing delete next to a forward slash
                if (clean.equals(cleanC)) sel--;

                if (clean.length() < 8) {
                    clean = clean + dateFormat.substring(clean.length());
                } else {
                    //This part makes sure that when we finish entering numbers
                    //the date is correct, fixing it otherwise
                    int day  = Integer.parseInt(clean.substring(0,2));
                    int mon  = Integer.parseInt(clean.substring(2,4));
                    int year = Integer.parseInt(clean.substring(4,8));

                    mon = Math.min(mon, 12);
                    mon = Math.max(mon, 1);
                    cal.set(Calendar.MONTH, mon-1);

                    year = (year < 1900) ? 1900 : Math.min(year, 2100);
                    cal.set(Calendar.YEAR, year);
                    // ^ first set year for the line below to work correctly
                    //with leap years - otherwise, date e.g. 29/02/2012
                    //would be automatically corrected to 28/02/2012
                    day = Math.max(day, 1);
                    day = Math.min(day, cal.getActualMaximum(Calendar.DATE));
                    clean = String.format("%02d%02d%02d",day, mon, year);
                }

                clean = String.format("%s/%s/%s", clean.substring(0, 2),
                        clean.substring(2, 4),
                        clean.substring(4, 8));

                sel = Math.max(sel, 0);
                current = clean;
                binding.edtDob.setText(current);
                binding.edtDob.setSelection(Math.min(sel, current.length()));
            }
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

        @Override
        public void afterTextChanged(Editable s) { }
    };
}