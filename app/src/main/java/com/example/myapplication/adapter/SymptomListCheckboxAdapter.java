package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Symptom;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class SymptomListCheckboxAdapter extends RecyclerView.Adapter<SymptomListCheckboxAdapter.SymptomCheckboxViewHolder> implements Filterable {


    private List<Symptom> symptomList;
    private Context context;
    private FirebaseFirestore firestore;
    private List<Symptom> selectedSymptomList;
    private List<Symptom> symptomListOld;

    public SymptomListCheckboxAdapter(Context context, List<Symptom> symptoms) {
        this.symptomList = symptoms;
        this.context = context;
        this.selectedSymptomList = new ArrayList<>();
        this.symptomListOld = symptoms;

    }




    public List<Symptom> getSelectedSymptomList() {
        return selectedSymptomList;
    }

    @NonNull
    @Override
    public SymptomCheckboxViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_symptom_with_cb, parent, false);


        firestore = FirebaseFirestore.getInstance();

        return new SymptomCheckboxViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SymptomCheckboxViewHolder holder, int position) {
        Symptom symptom = symptomList.get(position);
        holder.txtSymptomDescription.setText(symptom.getDescription());
        holder.cbSymptom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.cbSymptom.isChecked()){
                    selectedSymptomList.add(symptom);
                }else{
                    selectedSymptomList.remove(symptom);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return symptomList==null ? 0 : symptomList.size();
    }



    public class SymptomCheckboxViewHolder extends RecyclerView.ViewHolder {
        TextView txtSymptomDescription;
        CheckBox cbSymptom;
        public SymptomCheckboxViewHolder(@NonNull View itemView) {
            super(itemView);
            txtSymptomDescription = itemView.findViewById(R.id.txt_symptom_desc);

            cbSymptom = itemView.findViewById(R.id.check_symptom);
        }
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if(strSearch.isEmpty()){
                    symptomList = symptomListOld;
                }else{
                    List<Symptom> lst = new ArrayList<>();
                    for(Symptom symptom: symptomListOld){
                        if(symptom.getDescription().toLowerCase().contains(strSearch.toLowerCase())){
                            lst.add(symptom);
                        }
                    }
                    symptomList = lst;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = symptomList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                symptomList = (List<Symptom>) results.values;
                notifyDataSetChanged();
            }
        };
    }


}
