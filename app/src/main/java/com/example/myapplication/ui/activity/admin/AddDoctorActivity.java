package com.example.myapplication.ui.activity.admin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toolbar;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityAddDoctorBinding;
import com.example.myapplication.model.Condition;
import com.example.myapplication.model.Specialization;
import com.example.myapplication.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddDoctorActivity extends AppCompatActivity {
    private List<Specialization> selectedItems;
    private User user;
    private ActivityAddDoctorBinding binding;
    private String strName;
    private String strPhone;
    private String strDesc;
    private FirebaseFirestore firestore;
    private List<Specialization> specializationList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddDoctorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle bundle = getIntent().getExtras();
        user = bundle.getParcelable("user");

        firestore = FirebaseFirestore.getInstance();
        specializationList = new ArrayList<>();
        addSpecializationData();
        // copy the items from the main list to the selected item list for the preview
        // if the item is checked then only the item should be displayed for the user

        initButtonsListeners();

        binding.myToolbar.myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.btnCreate.setOnClickListener(v -> {
            strName = binding.edtName.getText().toString();
            strDesc = binding.edtDescription.getText().toString();
            strPhone = binding.edtPhone.getText().toString();
            Map<String, Object> doctorInfo = new HashMap<>();
            doctorInfo.put("name", strName);
            doctorInfo.put("description", strDesc);
            doctorInfo.put("phone", strPhone);


            List<DocumentReference> refs = new ArrayList<>();
            for (Specialization spec : selectedItems) {
                DocumentReference dRef = firestore.collection("Specializations").document(spec.getSpecializationId());
                refs.add(dRef);
            }
            doctorInfo.put("specialization", refs);
            firestore.collection("Doctors").document(user.getUserId()).set(doctorInfo);
            firestore.collection("Users").document(user.getUserId()).update("user_level", "DOCTOR");

            finish();
        });
    }
    private void initButtonsListeners() {
        // handle the Open Alert Dialog button
        binding.btnSetSpecialization.setOnClickListener(v -> {
            // initialise the list items for the alert dialog
            List<String> specItems = new ArrayList<>();
            for (Specialization spec : specializationList) {
                specItems.add(spec.getName());
            }
            final String[] listItems = specItems.toArray(new String[0]);
            final boolean[] checkedItems = new boolean[listItems.length];

            // initialise the alert dialog builder
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            // set the title for the alert dialog
            builder.setTitle("Choose from existing specialization");

            // now this is the function which sets the alert dialog for multiple item selection ready
            builder.setMultiChoiceItems(listItems, checkedItems, (dialog, which, isChecked) -> {
                checkedItems[which] = isChecked;
            });

            // alert dialog shouldn't be cancellable
            builder.setCancelable(false);

            // handle the negative button of the alert dialog
            builder.setNegativeButton("Cancel", (dialog, which) -> {});
            builder.setPositiveButton("Add", (dialog, which) -> {
                selectedItems = new ArrayList<>();
                for (int i = 0; i < checkedItems.length; i++) {
                    if (checkedItems[i]) selectedItems.add(specializationList.get(i));
                }
            });
            // create the builder
            builder.create();

            // create the alert dialog with the alert dialog builder instance
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });

        binding.btnNewSpecialization.setOnClickListener(v -> {
            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);
            EditText edtName = new EditText(this);
            EditText edtDesc = new EditText(this);
            edtName.setHint("Name");
            layout.addView(edtName);
            edtDesc.setHint("Description");
            layout.addView(edtDesc);

            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("Create new specialization")
                    .setMessage("Enter specialization name and description")
                    .setView(layout)
                    .setPositiveButton("Add", (dialogInterface, i) -> {
                        String name = edtName.getText().toString();
                        String desc = edtDesc.getText().toString();
                        Map<String, Object> spec = new HashMap<>();
                        spec.put("name", name);
                        spec.put("description", desc);
                        firestore.collection("Specializations").add(spec);
                    })
                    .setNegativeButton("Cancel", null)
                    .create();
            dialog.show();
        });
    }
    private void addSpecializationData() {

        firestore.collection("Specializations").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for (DocumentChange documentChange : value.getDocumentChanges()) {
                    if (documentChange.getType() == DocumentChange.Type.ADDED) {
                        QueryDocumentSnapshot doc = documentChange.getDocument();
                        String id = doc.getId();
                        Specialization specialization = doc.toObject(Specialization.class);
                        specialization.setSpecializationId(id);
                        specializationList.add(specialization);
                    }
                }
            }
        });
    }
}