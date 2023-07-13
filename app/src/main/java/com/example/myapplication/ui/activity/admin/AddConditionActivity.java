package com.example.myapplication.ui.activity.admin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityAddConditionBinding;
import com.example.myapplication.databinding.ActivityAddDoctorBinding;
import com.example.myapplication.model.Specialization;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddConditionActivity extends AppCompatActivity {
    private ActivityAddConditionBinding binding;
    private FirebaseFirestore firestore;
    private List<Specialization> specializationList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddConditionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firestore = FirebaseFirestore.getInstance();
        specializationList = new ArrayList<>();
        addSpecializationData();
        initButtonsListeners();
    }

    private void initButtonsListeners() {

        binding.myToolbar.myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // handle the Open Alert Dialog button
        binding.btnSetSpecialization.setOnClickListener(v -> {
            // initialise the list items for the alert dialog
            List<String> specItems = new ArrayList<>();
            for (Specialization spec : specializationList) {
                specItems.add(spec.getName());
            }
            final String[] listItems = specItems.toArray(new String[0]);
            int checkedItem = 0;
            final List<String> selectedItems = Arrays.asList(listItems);

            // initially set the null for the text preview

            // initialise the alert dialog builder
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            // set the title for the alert dialog
            builder.setTitle("Choose from existing specialization");

            // now this is the function which sets the alert dialog for multiple item selection ready
            builder.setSingleChoiceItems(listItems, checkedItem, (dialog, which) -> {
                String currentItem = selectedItems.get(which);
            });

            // alert dialog shouldn't be cancellable
            builder.setCancelable(false);

            // handle the negative button of the alert dialog
            builder.setNegativeButton("Cancel", (dialog, which) -> {});
            builder.setPositiveButton("Set", (dialog, which) -> {

            });
            // create the builder
            builder.create();

            // create the alert dialog with the alert dialog builder instance
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });

        EditText editText = new EditText(this);
        EditText editText2 = new EditText(this);
        binding.btnNewSpecialization.setOnClickListener(v -> {
            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);
            editText.setHint("Name");
            layout.addView(editText);
            editText2.setHint("Description");
            layout.addView(editText2);

            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("Create new specialization")
                    .setMessage("Enter specialization name and description")
                    .setView(layout)
                    .setPositiveButton("Add", (dialogInterface, i) -> {
                        String editTextInput = editText.getText().toString();
                        String editTextInput2 = editText2.getText().toString();
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