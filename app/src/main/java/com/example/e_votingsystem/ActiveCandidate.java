package com.example.e_votingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ActiveCandidate extends AppCompatActivity {

    RecyclerView recyclerViewCandidateAdmin;
    DatabaseReference dbrefCandidateAdmin;
    private MyAdapter3 myAdapter;
    private MyAdapter3.OnItemClickListener listener;
    private ArrayList<Candidate> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_candidate);

        recyclerViewCandidateAdmin=findViewById(R.id.candidateListForAdmin);

        dbrefCandidateAdmin= FirebaseDatabase.getInstance().getReference("Candidates");
        recyclerViewCandidateAdmin.setHasFixedSize(true);
        recyclerViewCandidateAdmin.setLayoutManager(new LinearLayoutManager(this));


        list=new ArrayList<>();
        myAdapter = new MyAdapter3(this, list);
        recyclerViewCandidateAdmin.setAdapter(myAdapter);
        dbrefCandidateAdmin.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Candidate candidate=dataSnapshot.getValue(Candidate.class);
                    list.add(candidate);
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        myAdapter.setOnItemClickListener(new MyAdapter3.OnItemClickListener() {
            @Override
            public void onEditClick(int position) {
                Intent intent=new Intent(getApplicationContext(),EditCandidate.class);
                intent.putExtra("candidateName",list.get(position).getFullNameCandidate());
                intent.putExtra("candidateAge",list.get(position).getAge());
                intent.putExtra("candidateNationality",list.get(position).getNationality());
                intent.putExtra("candidateWhich",list.get(position).getWhichCandidate());
                intent.putExtra("candidateCountVote",list.get(position).getCountVote());
                intent.putExtra("candidateIdentity",list.get(position).getIdentityNumberofCandidate());
                startActivity(intent);


            }

            @Override
            public void onDeleteClick(int position) {
                String candidate=list.get(position).getFullNameCandidate();
                dbrefCandidateAdmin.child(candidate).removeValue();
                myAdapter.notifyDataSetChanged();
                list.clear();
                Toast.makeText(ActiveCandidate.this,"Deleted successfully",Toast.LENGTH_SHORT).show();
                finish();
                startActivity(getIntent());
            }
        });
}}