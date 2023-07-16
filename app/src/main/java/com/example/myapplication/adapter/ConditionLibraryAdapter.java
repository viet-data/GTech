package com.example.myapplication.adapter;


import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.model.Symptom;
import com.example.myapplication.utils.ClickConditionInterface;
import com.example.myapplication.R;
import com.example.myapplication.model.Condition;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;


public class ConditionLibraryAdapter extends RecyclerView.Adapter<ConditionLibraryAdapter.ConditionViewHolder> implements Filterable {
    private final ClickConditionInterface clickConditionInterface;
    private List<Condition> conditionList;
    private List<Condition> conditionListOld;
    private Context context;
    private FirebaseFirestore firestore;



    public ConditionLibraryAdapter(Context context, List<Condition> conditions, ClickConditionInterface clickConditionInterface) {
        this.conditionList = conditions;
        this.context = context;
        this.clickConditionInterface = clickConditionInterface;
        this.conditionListOld = conditions;

    }


    @NonNull
    @Override
    public ConditionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_condition, parent, false);
        firestore = FirebaseFirestore.getInstance();
        return new ConditionViewHolder(view, clickConditionInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ConditionViewHolder holder, int position) {
        Condition condition = conditionList.get(position);
        holder.txtConditionName.setText(condition.getName());
    }

    @Override
    public int getItemCount() {
        return conditionList == null ? 0 : conditionList.size();
    }



    public class ConditionViewHolder extends RecyclerView.ViewHolder {
        TextView txtConditionName;
        public ConditionViewHolder(@NonNull View itemView, ClickConditionInterface clickConditionInterface) {
            super(itemView);
            txtConditionName = itemView.findViewById(R.id.txt_concept_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    clickConditionInterface.onItemClick(pos);

                }
            });
        }
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if(strSearch.isEmpty()){
                    conditionList = conditionListOld;
                }else{
                    List<Condition> lst = new ArrayList<>();
                    for(Condition condition: conditionListOld){
                        if(condition.getName().toLowerCase().contains(strSearch.toLowerCase())){
                            lst.add(condition);
                        }
                    }
                    conditionList = lst;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = conditionList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                conditionList = (List<Condition>) results.values;
                notifyDataSetChanged();
            }
        };
    }



}
