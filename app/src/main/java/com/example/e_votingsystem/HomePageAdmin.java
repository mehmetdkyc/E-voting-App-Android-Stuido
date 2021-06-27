package com.example.e_votingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class HomePageAdmin extends AppCompatActivity {
    private LinearLayout logout;
    private LinearLayout createElection;
    private LinearLayout settingsPage;
    private LinearLayout createCandidate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page_admin);
        createElection=findViewById(R.id.createElectionAdmin);
        settingsPage=findViewById(R.id.SettingsAdmin);
        createCandidate=findViewById(R.id.candidateAdmin);

        logout=findViewById(R.id.logoutAdmin);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePageAdmin.this, LoginPage.class));
                Toast.makeText(HomePageAdmin.this,"Logout successfully",Toast.LENGTH_LONG).show();
            }
        });
        createElection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePageAdmin.this, CreateElectionPage.class));
            }
        });
        createCandidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePageAdmin.this, CandidateOption.class));
            }
        });

        settingsPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePageAdmin.this, SettingsAdmin.class));
            }
        });


    }
}