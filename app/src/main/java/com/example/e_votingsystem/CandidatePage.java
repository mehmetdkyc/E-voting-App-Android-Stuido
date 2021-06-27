package com.example.e_votingsystem;


import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.nio.charset.CoderMalfunctionError;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CandidatePage extends AppCompatActivity {
    RecyclerView recyclerViewCandidate;
    DatabaseReference dbrefCandidate,dbrefForUser,db;

    public ArrayList<checkUserVoted> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<checkUserVoted> arrayListt) {
        arrayList = arrayListt;
    }

    private MyAdapter2 myAdapter;
    private FirebaseUser user;
    public  ArrayList<checkUserVoted> arrayList= new ArrayList<>();

    private Button votedButton;
    private ArrayList<Candidate> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_page);
        votedButton=findViewById(R.id.voteButton);
        recyclerViewCandidate=findViewById(R.id.candidateList);

        String electionName1="electionName not set";
        Bundle extras= getIntent().getExtras();
        if(extras!=null){
            electionName1=extras.getString("electionName");

        }
        dbrefCandidate= FirebaseDatabase.getInstance().getReference("Candidates");
        dbrefForUser= FirebaseDatabase.getInstance().getReference("Users");
        recyclerViewCandidate.setHasFixedSize(true);
        recyclerViewCandidate.setLayoutManager(new LinearLayoutManager(this));

        list=new ArrayList<>();
        myAdapter = new MyAdapter2(this, list);
        recyclerViewCandidate.setAdapter(myAdapter);
        String finalUsername = electionName1;

        dbrefCandidate.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Candidate candidate=dataSnapshot.getValue(Candidate.class);
                    if(candidate.whichCandidate.equals(finalUsername)){
                        list.add(candidate);
                    }

                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        myAdapter.setOnItemClickListener(new MyAdapter2.OnItemClickListener() {
            @Override
            public void onVoteClick(int position) {
                /*String userId= FirebaseAuth.getInstance().getCurrentUser().getUid();
                db=dbrefForUser.child(userId);
                Toast.makeText(CandidatePage.this,String.valueOf(arrayList.size()),Toast.LENGTH_SHORT).show();
                db.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        User user=snapshot.getValue(User.class);
                        String id= user.getIdentity();
                        Toast.makeText(CandidatePage.this,id,Toast.LENGTH_SHORT).show();

                        for (int i = 0; i < arrayList.size(); i++) {
                            checkUserVoted c = arrayList.get(i);

                            if (c.electionName.equals(finalUsername)) {

                                if (c.map.get(id) == 0) {
                                    Toast.makeText(CandidatePage.this, "girdi", Toast.LENGTH_SHORT).show();
                                    int amountOfVote = list.get(position).getCountVote();
                                    String nameKey = list.get(position).getFullNameCandidate();
                                    amountOfVote = amountOfVote + 1;
                                    c.map.put(id, 1);
                                    Toast.makeText(CandidatePage.this, "Voted successfully", Toast.LENGTH_SHORT).show();
                                    dbrefCandidate.child(nameKey).child("countVote").setValue(amountOfVote);
                                } else {
                                    Toast.makeText(CandidatePage.this, "You can't vote twice", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
*/
                int amountOfVote = list.get(position).getCountVote();
                String nameKey = list.get(position).getFullNameCandidate();
                amountOfVote = amountOfVote + 1;
                Toast.makeText(CandidatePage.this, "Voted successfully", Toast.LENGTH_SHORT).show();
                dbrefCandidate.child(nameKey).child("countVote").setValue(amountOfVote);
                myAdapter.notifyDataSetChanged();
                list.clear();



            }
        });



    }

}