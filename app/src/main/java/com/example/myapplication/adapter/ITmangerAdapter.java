package com.example.myapplication.adapter;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.instance.Doctor;

import java.util.List;

public class ITmangerAdapter extends RecyclerView.Adapter<ITmangerAdapter.DataViewHolder> {
    @NonNull

    private List<Doctor> doctors;
    private Context context;

    public ITmangerAdapter(Context context, List<Doctor> doctors){
        this.doctors = doctors;
        this.context = context;
    }

    @Override
    public int getItemCount(){
        return  doctors == null ?0:doctors.size();
    }

    @Override
    public ITmangerAdapter.DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_doctor, parent, false);
        return new DataViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull ITmangerAdapter.DataViewHolder holder, int position) {
        String name = doctors.get(position).getName();
        holder.tvName.setText(name);
    }

    public static class DataViewHolder extends RecyclerView.ViewHolder{
        private TextView tvName;

        public DataViewHolder(View itemView){
            super(itemView);

            tvName = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }
}
