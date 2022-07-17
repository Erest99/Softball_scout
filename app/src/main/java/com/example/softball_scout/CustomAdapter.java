package com.example.softball_scout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


import java.util.Collections;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private List<Record> records;

    Animation translate_anim;
    //int position;

    CustomAdapter(Context context, List<Record> records)
    {
        this.context = context;
        this.records = records;


    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {


        holder.name.setText(String.valueOf(records.get(position).getName()));
        holder.position.setText(String.valueOf(records.get(position).getPosition()));
        holder.duration.setText(String.valueOf(records.get(position).getDuration()));
        holder.action.setText(String.valueOf(records.get(position).getAction()));

    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name,position,duration,action;
        ConstraintLayout constraintLayout;

        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            name = itemView.findViewById(R.id.tw_name);
            position = itemView.findViewById(R.id.tw_position);
            duration = itemView.findViewById(R.id.tw_duration);
            action = itemView.findViewById(R.id.tw_action);
            constraintLayout = itemView.findViewById(R.id.mylayout);
            translate_anim = AnimationUtils.loadAnimation(context,R.anim.translate_anim);
            constraintLayout.setAnimation(translate_anim);
        }
    }
}
