package com.example.e_votingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CreateCandidatePage extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    private MyAdapter.RecyclerViewClickListener  listener;
    private MyAdapter myAdapter;
    private ArrayList<Election> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_candidate_page);

        recyclerView=findViewById(R.id.electionList);

        databaseReference= FirebaseDatabase.getInstance().getReference("Election");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listener =new MyAdapter.RecyclerViewClickListener() {
            @Override
            public void OnClick(View v, int position) {
                Intent intent=new Intent(getApplicationContext(),CandidatePage.class);
                intent.putExtra("electionName",list.get(position).getName());
                startActivity(intent);
            }
        };
        list=new ArrayList<>();
        myAdapter = new MyAdapter(this, list, listener);
        recyclerView.setAdapter(myAdapter);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Election election=dataSnapshot.getValue(Election.class);
                    list.add(election);
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateCandidatePage.this, CandidatePage.class));
            }
        });

    }
}