package com.example.e_votingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CreateCandidatePage2 extends AppCompatActivity {
    private EditText nameCandidate,numberofIdentity,nationality,age;
    private Spinner spinner;
    private Button save;
    private ProgressBar progressBar;
    private DatabaseReference dbref,dbrefElectionName;
    ValueEventListener listener;
    private ArrayList<String> list;
    private ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_candidate_page2);

        save=findViewById(R.id.createCandidateButton);

        numberofIdentity=findViewById(R.id.IdentityNumber);
        spinner=findViewById(R.id.spinner);
        nameCandidate=findViewById(R.id.CandidateName);
        nationality=findViewById(R.id.nationalityofCandidate);
        age=findViewById(R.id.ageOfCandidate);

        progressBar=findViewById(R.id.progressBar3);

        dbref= FirebaseDatabase.getInstance().getReference("Candidates");
        dbrefElectionName =FirebaseDatabase.getInstance().getReference("Election");
        list=new ArrayList<String>();
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,list);
        spinner.setAdapter(adapter);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();
            }
        });
        fetchData();

    }

    private void insertData() {
        String name=nameCandidate.getText().toString().trim();
        String age1=age.getText().toString().trim();
        String nation=nationality.getText().toString().trim();
        String Id=numberofIdentity.getText().toString().trim();
        String spinnerData=spinner.getSelectedItem().toString().trim();
        int voteCount=0;
        if(name.isEmpty()){
            nameCandidate.setError("Full name is required");
            nameCandidate.requestFocus();
            return;
        }
        if(age1.isEmpty()){
            age.setError("Age is required");
            age.requestFocus();
            return;
        }
        if(nation.isEmpty()){
            nationality.setError("Nationality is required");
            nationality.requestFocus();
            return;
        }
        if(Id.isEmpty()){
            numberofIdentity.setError("Id is required");
            numberofIdentity.requestFocus();
            return;
        }
        if(spinnerData.equals("")) {
            Toast.makeText(this,"Please select which election",Toast.LENGTH_LONG).show();
            spinner.requestFocus();
            return;
        }

        Candidate candidate=new Candidate(name,voteCount,age1,nation,Id,spinnerData);
        dbref.child(candidate.fullNameCandidate).setValue(candidate);
        if(!candidate.equals("")) {
            Toast.makeText(CreateCandidatePage2.this, "Successfully!", Toast.LENGTH_LONG).show();
            startActivity(new Intent(CreateCandidatePage2.this, HomePageAdmin.class));
        }
        else{
            Toast.makeText(CreateCandidatePage2.this, "Failed!",Toast.LENGTH_LONG).show();
        }
        progressBar.setVisibility(View.GONE);



    }
    public void fetchData(){
        listener=dbrefElectionName.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    Election election=dataSnapshot.getValue(Election.class);
                    list.add(election.name);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}