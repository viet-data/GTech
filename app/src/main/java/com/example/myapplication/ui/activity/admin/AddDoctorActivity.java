package com.example.myapplication.ui.activity.admin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toolbar;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityAddDoctorBinding;

import java.util.Arrays;
import java.util.List;

public class AddDoctorActivity extends AppCompatActivity {
    private ActivityAddDoctorBinding binding;
    private String strEmail;
    private String strName;
    private String strPhone;
    private String strDesc;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddDoctorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // copy the items from the main list to the selected item list for the preview
        // if the item is checked then only the item should be displayed for the user

        // handle the Open Alert Dialog button
        binding.btnSetSpecialization.setOnClickListener(v -> {
            // initialise the list items for the alert dialog
            String[] listItems = new String[]{"General Practitioner", "Gynecologist", "Neurologist", "Cardiologist"};
            boolean[] checkedItems = new boolean[listItems.length];
            final List<String> selectedItems = Arrays.asList(listItems);

            // initially set the null for the text preview

            // initialise the alert dialog builder
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            // set the title for the alert dialog
            builder.setTitle("Choose from existing specialization");

            // now this is the function which sets the alert dialog for multiple item selection ready
            builder.setMultiChoiceItems(listItems, checkedItems, (dialog, which, isChecked) -> {
                checkedItems[which] = isChecked;
                String currentItem = selectedItems.get(which);
            });

            // alert dialog shouldn't be cancellable
            builder.setCancelable(false);

            // handle the negative button of the alert dialog
            builder.setNegativeButton("Cancel", (dialog, which) -> {});
            builder.setPositiveButton("Add", (dialog, which) -> {

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
        binding.myToolbar.myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}