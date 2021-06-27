package com.example.e_votingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditCandidate extends AppCompatActivity {
    private DatabaseReference dbrefCandidateupdate;
    private EditText name,age,nationality;
    private Button update;
    String candidateName="";
    String candidateAge="";
    String candidateNationality="";
    String candidateWhich="";
    int candidateCountVote=0;
    String candidateIdentity="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_candidate);

        name=findViewById(R.id.editCandidateName);
        age=findViewById(R.id.editCandidateAge);
        nationality=findViewById(R.id.editCandidateNationality);

        update=findViewById(R.id.updateCandidate);

        Bundle extras= getIntent().getExtras();
        if(extras!=null){
            candidateName=extras.getString("candidateName");
            candidateAge=extras.getString("candidateAge");
            candidateNationality=extras.getString("candidateNationality");
            candidateWhich=extras.getString("candidateWhich");
            candidateCountVote=extras.getInt("candidateCountVote");
            candidateIdentity=extras.getString("candidateIdentity");
        }
        name.setText(candidateName);
        age.setText(candidateAge);
        nationality.setText(candidateNationality);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameEdit=name.getText().toString().trim();
                String ageEdit=age.getText().toString().trim();
                String nationalityEdit=nationality.getText().toString().trim();

                dbrefCandidateupdate= FirebaseDatabase.getInstance().getReference("Candidates");
                dbrefCandidateupdate.child(candidateName).removeValue();


                Candidate candidate=new Candidate(nameEdit,candidateCountVote,ageEdit,nationalityEdit,candidateIdentity,candidateWhich);
                dbrefCandidateupdate.child(nameEdit).setValue(candidate);


                Toast.makeText(EditCandidate.this,"Updated successfully",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(EditCandidate.this, ActiveCandidate.class));
            }
        });


    }
}