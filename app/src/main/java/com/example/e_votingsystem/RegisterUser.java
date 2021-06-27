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
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener{
    private FirebaseAuth mAuth;
    private TextView banner;
    private Button registerUser;
    private EditText editTextFullName,editTextAge,editTextMail,editTextPassword;
    private ProgressBar progressBar;
    private RadioButton adminRadio,userRadio;
    public String AdminOrUser ="Admin";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);


        mAuth = FirebaseAuth.getInstance();

        banner=findViewById(R.id.banner);
        banner.setOnClickListener(this);

        registerUser=findViewById(R.id.RegisterUser);
        registerUser.setOnClickListener(this);

        editTextFullName=findViewById(R.id.fullname);
        editTextAge=findViewById(R.id.age);
        editTextMail=findViewById(R.id.Email);
        editTextPassword=findViewById(R.id.password);

        progressBar=findViewById(R.id.progressBar);

        adminRadio=findViewById(R.id.Adminradio);
        adminRadio.setOnClickListener(this);
        adminRadio.setChecked(true);
        userRadio=findViewById(R.id.UserRadio);
        userRadio.setOnClickListener(this);

        /*if(mAuth.getCurrentUser() !=null){
            startActivity(new Intent(RegisterUser.this,ProfileActivity.class));
        }*/


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.banner:
                startActivity(new Intent(this, LoginPage.class));
                break;
            case R.id.RegisterUser:
                registerUser();
                break;
            case R.id.Adminradio:
                AdminOrUser="Admin";
                userRadio.setChecked(false);
                break;
            case R.id.UserRadio:
                AdminOrUser="Users";
                adminRadio.setChecked(false);
                break;
        }
    }

    private void registerUser() {
        String name=editTextFullName.getText().toString().trim();
        String age=editTextAge.getText().toString().trim();
        String email=editTextMail.getText().toString().trim();
        String password=editTextPassword.getText().toString().trim();
        String typeOfAccount= AdminOrUser.trim();
        boolean stateAdminButton=adminRadio.isChecked();
        boolean stateUserButton=userRadio.isChecked();
        if(name.isEmpty()){
            editTextFullName.setError("Full name is required");
            editTextFullName.requestFocus();
            return;
        }
        if(age.isEmpty()){
            editTextAge.setError("Identity is required");
            editTextAge.requestFocus();
            return;
        }
        if(email.isEmpty()){
            editTextMail.setError("Mail is required");
            editTextMail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextMail.setError("Please provide valid email.");
            editTextMail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }
        if(password.length()<6) {
            editTextPassword.setError("Password must be over 6 characters.");
            editTextPassword.requestFocus();
            return;
        }
        if(!stateAdminButton && !stateUserButton ) {
            Toast.makeText(RegisterUser.this, "You must select type of registration!",Toast.LENGTH_LONG).show();
            adminRadio.requestFocus();
            return;
        }


        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    User user=new User(email,name,age,typeOfAccount);
                    FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser()
                    .getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(RegisterUser.this, "User has been registered succesfully!",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(RegisterUser.this, LoginPage.class));
                                progressBar.setVisibility(View.GONE);
                            }
                            else
                            {
                                Toast.makeText(RegisterUser.this, "Failed!",Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });

                }
                else
                {
                    Toast.makeText(RegisterUser.this, "Failed to register!",Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }

            }
        });

    }
}