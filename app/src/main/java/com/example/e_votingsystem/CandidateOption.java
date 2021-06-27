package com.example.e_votingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class CandidateOption extends AppCompatActivity {
    private LinearLayout activecandidate;
    private LinearLayout createcandidate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_option);

        activecandidate=findViewById(R.id.seecandidateAdmin);
        createcandidate=findViewById(R.id.createCandidateAdmin);

        activecandidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CandidateOption.this, ActiveCandidate.class));
            }
        });
        createcandidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CandidateOption.this, CreateCandidatePage2.class));
            }
        });
    }
}