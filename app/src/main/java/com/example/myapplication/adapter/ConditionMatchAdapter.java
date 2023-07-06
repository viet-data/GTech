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
import com.example.myapplication.model.Doctor;
import com.example.myapplication.model.User;

import java.util.ArrayList;
import java.util.List;

public class ConditionMatchAdapter extends RecyclerView.Adapter<ConditionMatchAdapter.ConditionViewHolder> {
    private List<Condition> conditions;
    private Context context;

    public ConditionMatchAdapter(Context context, List<Condition> conditions){
        this.conditions = conditions;
        this.context = context;
    }

    @Override
    public int getItemCount(){
        return conditions == null ? 0 : conditions.size();
    }

    @Override
    public ConditionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_condition, parent, false);
        return new ConditionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ConditionViewHolder holder, int position) {
        Condition condition = conditions.get(position);
        holder.tvName.setText(String.valueOf(condition.getName()));
    }

    public static class ConditionViewHolder extends RecyclerView.ViewHolder{
        TextView tvName;

        public ConditionViewHolder(@NonNull View itemView){
            super(itemView);
            tvName = itemView.findViewById(R.id.txt_concept_name);
        }
    }
}

