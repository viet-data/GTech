package com.example.myapplication.ui.activity.admin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.myapplication.databinding.ActivityAddConditionBinding;
import com.example.myapplication.model.Specialization;
import com.example.myapplication.model.Symptom;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class AddConditionActivity extends AppCompatActivity {
    private ActivityAddConditionBinding binding;
    private FirebaseFirestore firestore;
    private List<Specialization> specializationList;
    private List<Symptom> symptomList;
    private Specialization selectedSpec;
    private List<Symptom> selectedSymptoms;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddConditionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firestore = FirebaseFirestore.getInstance();
        specializationList = new ArrayList<>();
        symptomList = new ArrayList<>();
        loadData();
        initButtonsListeners();
    }

    private void initButtonsListeners() {
        binding.myToolbar.myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        binding.btnCreate.setOnClickListener(v -> {
            Map<String, Object> condition = new HashMap<>();
            condition.put("name", binding.edtName.getText().toString());
            condition.put("description", binding.edtDescription.getText().toString());
            condition.put("specialization", firestore.collection("Specializations").document(selectedSpec.getSpecializationId()));
            List<DocumentReference> refs = new ArrayList<>();
            for (Symptom symp : selectedSymptoms) {
                refs.add(firestore.collection("Symptoms").document(symp.getSymptomId()));
            }
            condition.put("symptoms", refs);
            firestore.collection("Conditions").add(condition);
            finish();
        });
        // handle the Open Alert Dialog button
        binding.btnSetSpecialization.setOnClickListener(v -> {
            // initialise the list items for the alert dialog
            List<String> specItems = new ArrayList<>();
            for (Specialization spec : specializationList) {
                specItems.add(spec.getName());
            }
            final String[] listItems = specItems.toArray(new String[0]);
            AtomicInteger checkedItem = new AtomicInteger();
            final List<String> selectedItems = Arrays.asList(listItems);

            // initially set the null for the text preview

            // initialise the alert dialog builder
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            // set the title for the alert dialog
            builder.setTitle("Choose from existing specialization");

            // now this is the function which sets the alert dialog for multiple item selection ready
            builder.setSingleChoiceItems(listItems, -1, (dialog, which) -> {
                checkedItem.set(which);
            });
            // alert dialog shouldn't be cancellable
            builder.setCancelable(false);

            // handle the negative button of the alert dialog
            builder.setNegativeButton("Cancel", (dialog, which) -> {});
            builder.setPositiveButton("Set", (dialog, which) -> {
                selectedSpec = specializationList.get(checkedItem.get());
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
            EditText edtSpecName = new EditText(this);
            EditText edtSpecDesc = new EditText(this);
            edtSpecName.setHint("Name");
            layout.addView(edtSpecName);
            edtSpecDesc.setHint("Description");
            layout.addView(edtSpecDesc);

            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("Create new specialization")
                    .setMessage("Enter specialization name and description")
                    .setView(layout)
                    .setPositiveButton("Add", (dialogInterface, i) -> {
                        String specName = edtSpecName.getText().toString();
                        String specDesc = edtSpecDesc.getText().toString();
                        Map<String, Object> hm = new HashMap<>();
                        hm.put("name", specName);
                        hm.put("description", specDesc);
                    })
                    .setNegativeButton("Cancel", null)
                    .create();
            dialog.show();
        });
        binding.btnSetSymptoms.setOnClickListener(v -> {
            // initialise the list items for the alert dialog
            List<String> sympItems = new ArrayList<>();
            for (Symptom symp : symptomList) {
                sympItems.add(symp.getDescription());
            }
            final String[] listItems = sympItems.toArray(new String[0]);
            final boolean[] checkedItems = new boolean[listItems.length];

            // initially set the null for the text preview

            // initialise the alert dialog builder
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            // set the title for the alert dialog
            builder.setTitle("Choose from existing symptoms");

            // now this is the function which sets the alert dialog for multiple item selection ready
            builder.setMultiChoiceItems(listItems, checkedItems, (dialog, which, isChecked) -> {
                checkedItems[which] = isChecked;
            });

            // alert dialog shouldn't be cancellable
            builder.setCancelable(false);

            // handle the negative button of the alert dialog
            builder.setNegativeButton("Cancel", (dialog, which) -> {});
            builder.setPositiveButton("Set", (dialog, which) -> {
                selectedSymptoms = new ArrayList<>();
                for (int i = 0; i < checkedItems.length; i++) {
                    if (checkedItems[i]) selectedSymptoms.add(symptomList.get(i));
                }
            });
            // create the builder
            builder.create();

            // create the alert dialog with the alert dialog builder instance
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });

        binding.btnNewSymptoms.setOnClickListener(v -> {
            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);
            EditText edtSympDesc = new EditText(this);
            edtSympDesc.setHint("Description");
            layout.addView(edtSympDesc);

            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("Create new specialization")
                    .setMessage("Enter symptom description")
                    .setView(layout)
                    .setPositiveButton("Add", (dialogInterface, i) -> {
                        String desc = edtSympDesc.getText().toString();
                        Map<String, Object> hm = new HashMap<>();
                        hm.put("description", desc);
                        firestore.collection("Symptoms").add(hm);
                    })
                    .setNegativeButton("Cancel", null)
                    .create();
            dialog.show();
        });
    }
    private void loadData() {
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
        firestore.collection("Symptoms").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for (DocumentChange documentChange : value.getDocumentChanges()) {
                    if (documentChange.getType() == DocumentChange.Type.ADDED) {
                        QueryDocumentSnapshot doc = documentChange.getDocument();
                        String id = doc.getId();
                        Symptom symptom = doc.toObject(Symptom.class);
                        symptom.setSymptomId(id);
                        symptomList.add(symptom);
                    }
                }
            }
        });
    }
}