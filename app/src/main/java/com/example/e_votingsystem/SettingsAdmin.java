package com.example.e_votingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SettingsAdmin extends AppCompatActivity {
    private Button backHome;
    private DatabaseReference reference;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseDatabase;
    TextView greetingTextView,fullNameTextView,emailTextView,ageTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_admin);

        greetingTextView=findViewById(R.id.greetingProfileAdmin);
        fullNameTextView=findViewById(R.id.fullNameProfileADMÄ°N);
        emailTextView=findViewById(R.id.EmailaddressprofileAdmin);
        ageTextView=findViewById(R.id.ageProfileAdmin);

        backHome=findViewById(R.id.backHomePage);

        backHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingsAdmin.this, HomePageAdmin.class));
            }
        });

        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();

        reference=firebaseDatabase.getInstance().getReference().child("Users").child(firebaseUser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String age=snapshot.child("age").getValue().toString();
                String name=snapshot.child("name").getValue().toString();
                String email=snapshot.child("email").getValue().toString();
                if(snapshot!=null){
                    greetingTextView.setText(name+ " HoĹźgeldiniz.");
                    fullNameTextView.setText(name);
                    emailTextView.setText(email);
                    ageTextView.setText(age);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SettingsAdmin.this,"Something wrong happened.",Toast.LENGTH_SHORT).show();
            }
        });

    }
}