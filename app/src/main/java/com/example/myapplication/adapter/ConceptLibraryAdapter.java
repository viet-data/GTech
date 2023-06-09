package com.example.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Concept;
import com.example.myapplication.model.Condition;
import com.example.myapplication.ui.library.LibraryFragment;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;


public class ConceptLibraryAdapter extends RecyclerView.Adapter<ConceptLibraryAdapter.ConceptViewHolder> {

    private List<Concept> conceptList;
    private LibraryFragment fragment;
    private FirebaseFirestore firestore;

    public ConceptLibraryAdapter(LibraryFragment libraryFragment, List<Concept> concepts) {
        this.conceptList = concepts;
        this.fragment = libraryFragment;
    }


    @NonNull
    @Override
    public ConceptViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_concept, parent, false);
        firestore = FirebaseFirestore.getInstance();
        return new ConceptViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ConceptViewHolder holder, int position) {
        Concept concept = conceptList.get(position);
        holder.txtConceptName.setText(concept.getName());
        holder.txtConceptType.setText((concept instanceof Condition) ? "Condition" : "Symptom");
    }

    @Override
    public int getItemCount() {
        return conceptList.size();
    }

    public class ConceptViewHolder extends RecyclerView.ViewHolder {
        TextView txtConceptName;
        TextView txtConceptType;
        public ConceptViewHolder(@NonNull View itemView) {
            super(itemView);
            txtConceptName = itemView.findViewById(R.id.txt_concept_name);
            txtConceptType = itemView.findViewById(R.id.txt_concept_type);
        }
    }

}
