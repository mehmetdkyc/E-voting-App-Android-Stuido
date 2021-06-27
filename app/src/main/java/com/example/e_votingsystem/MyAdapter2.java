package com.example.e_votingsystem;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.MyViewHolder> {
    Context context;
    ArrayList<Candidate> list;
    private OnItemClickListener onItemClickListener;
    public MyAdapter2(Context context, ArrayList<Candidate> list ) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.candidatelist,parent,false);
        MyViewHolder mv=new MyViewHolder(v,onItemClickListener);
        return mv;
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Candidate candidate= list.get(position);
        holder.candidateTextName.setText(candidate.getFullNameCandidate());
        holder.ageText.setText(candidate.getAge());
        holder.nationText.setText(candidate.getNationality());
        holder.voteNumber.setText(String.valueOf(candidate.getCountVote()));


    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public interface  OnItemClickListener{
        void onVoteClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        onItemClickListener=listener;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView candidateTextName,ageText,nationText,voteNumber;

        public MyViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            voteNumber=itemView.findViewById(R.id.amountOfVoteText);
            candidateTextName=itemView.findViewById(R.id.nameSelection);
            ageText=itemView.findViewById(R.id.ageSelection);
            nationText=itemView.findViewById(R.id.nationalitySelection);
            itemView.findViewById(R.id.voteButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onVoteClick(position);
                        }
                    }
                }
            });

        }

    }

}
