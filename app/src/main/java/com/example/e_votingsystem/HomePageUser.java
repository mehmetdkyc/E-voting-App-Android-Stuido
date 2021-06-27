package com.example.e_votingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class HomePageUser extends AppCompatActivity {
    private LinearLayout logout;
    private LinearLayout voterpage;
    private LinearLayout settingspage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_user);

        voterpage=findViewById(R.id.voteUserPage);
        settingspage=findViewById(R.id.SettingsUser);
        logout=findViewById(R.id.logoutUser);

        voterpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePageUser.this, CreateCandidatePage.class));
            }
        });
        settingspage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePageUser.this, SettingsUser.class));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePageUser.this, LoginPage.class));
                Toast.makeText(HomePageUser.this,"Logout successfully",Toast.LENGTH_LONG).show();
            }
        });

    }
}