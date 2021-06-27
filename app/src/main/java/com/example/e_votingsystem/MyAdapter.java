package com.example.e_votingsystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    Context context;
    private RecyclerViewClickListener recyclerViewClickListener;
    ArrayList<Election> list;

    public MyAdapter(Context context, ArrayList<Election> list,RecyclerViewClickListener recyclerViewClickListener) {
        this.context = context;
        this.list = list;
        this.recyclerViewClickListener=recyclerViewClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.electionlist,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Election election= list.get(position);
        holder.electionTextName.setText(election.getName());
        holder.dueToTextName.setText(election.getEndDate());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public interface RecyclerViewClickListener{
        void OnClick(View v, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView electionTextName,dueToTextName;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            electionTextName=itemView.findViewById(R.id.ElectionNameText);
            dueToTextName=itemView.findViewById(R.id.DueDate);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            recyclerViewClickListener.OnClick(itemView,getAdapterPosition());
        }
    }
}
