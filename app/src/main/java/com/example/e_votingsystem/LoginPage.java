package com.example.e_votingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginPage extends AppCompatActivity implements View.OnClickListener{

    private TextView register,forgotpassword, fingerprint;
    private EditText editTextEmail, editTextPassword;
    private Button signIn;
    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
     int temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        register= findViewById(R.id.register);
        register.setOnClickListener(this);

        fingerprint= findViewById(R.id.fpText);
        fingerprint.setOnClickListener(this);

        forgotpassword=findViewById(R.id.forgotpassword);
        forgotpassword.setOnClickListener(this);

        signIn=findViewById(R.id.signIN);
        signIn.setOnClickListener(this);

        editTextEmail=findViewById(R.id.Mailadress);
        editTextPassword=findViewById(R.id.password);

        progressBar=findViewById(R.id.progressBar);
        mAuth=FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register:
                startActivity(new Intent(this,RegisterUser.class));
                break;
            case R.id.forgotpassword:
                startActivity(new Intent(this,ResetPassword.class));
                break;
            case R.id.signIN:
                userLogin();
                break;
            case R.id.fpText:
                startActivity(new Intent(this,FingerPrintAuth.class));
                break;
        }
    }

    private void userLogin() {
        String email=editTextEmail.getText().toString().trim();
        String password=editTextPassword.getText().toString().trim();

        if (email.isEmpty()){
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }
        if (password.isEmpty()){
            editTextPassword.setError("Password is required!");
            editTextPassword.requestFocus();
            return;
        }
        if (password.length()<6){
            editTextPassword.setError("Min password length is 6 characters");
            editTextPassword.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isComplete()){
                    user=FirebaseAuth.getInstance().getCurrentUser();
                    if(user.isEmailVerified()){
                        if(task.isSuccessful()){
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(LoginPage.this,"Login successfully",Toast.LENGTH_SHORT).show();
                            checkedTypeOfUser(user.getUid());

                        }
                        else{
                            Toast.makeText(LoginPage.this,"Error ! : Mail or password is invalid! ",Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }


                    }
                    else{
                        user.sendEmailVerification();
                        Toast.makeText(LoginPage.this,"Check your email to verify your account.",Toast.LENGTH_LONG).show();
                    }

                }
                else{
                    Toast.makeText(LoginPage.this,"Failed to login! Please check your credentials", Toast.LENGTH_LONG ).show();


                }
            }
        });
    }

    public void checkedTypeOfUser(String Uid ) {
        DatabaseReference df= FirebaseDatabase.getInstance().getReference().child("Users").child(Uid);
        df.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String typeAccountCheck=snapshot.child("typeOfAccount").getValue().toString().trim();

                if(typeAccountCheck.equals("Admin")){

                    temp = 1;
                    startActivity(new Intent(LoginPage.this,FingerPrintAuthAdmin.class));


                }
                else if(typeAccountCheck.equals("Users")){
                    temp= 0;
                    startActivity(new Intent(LoginPage.this, FingerPrintAuth.class));
                }
                else{
                    temp = -1;
                    Toast.makeText(LoginPage.this,"Invalid account type",Toast.LENGTH_LONG).show();

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(LoginPage.this,"Something wrong happened.",Toast.LENGTH_SHORT).show();
            }
        });

    }

}