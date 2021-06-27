package com.example.e_votingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class ResetPassword extends AppCompatActivity {
    private EditText resetPassword;
    private Button resetButton;
    private ProgressBar progressBar;

    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        resetPassword=findViewById(R.id.resetMailadress);
        resetButton=findViewById(R.id.resetButton);
        progressBar=findViewById(R.id.progressBar);

        auth=FirebaseAuth.getInstance();

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPass();
            }
        });
    }

    private void resetPass() {
        String email=resetPassword.getText().toString().trim();
        if(email.isEmpty()){
            resetPassword.setError("Email is required");
            resetPassword.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            resetPassword.setError("Please provide valid email!");
            resetPassword.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                    Toast.makeText(ResetPassword.this,"Check your email to reset your password!",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(ResetPassword.this,"Try again! Something wrong",Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }

        });
    }
}