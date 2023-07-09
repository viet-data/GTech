package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Condition;
import com.example.myapplication.ui.fragments.LibraryFragment;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;


public class ConditionLibraryAdapter extends RecyclerView.Adapter<ConditionLibraryAdapter.ConditionViewHolder> {

    private List<Condition> conditionList;
    private Context context;
    private FirebaseFirestore firestore;

    public ConditionLibraryAdapter(Context context, List<Condition> conditions) {
        this.conditionList = conditions;
        this.context = context;
    }


    @NonNull
    @Override
    public ConditionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_condition, parent, false);
        firestore = FirebaseFirestore.getInstance();
        return new ConditionViewHolder(view);
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
        public ConditionViewHolder(@NonNull View itemView) {
            super(itemView);
            txtConditionName = itemView.findViewById(R.id.txt_concept_name);
        }
    }
}
