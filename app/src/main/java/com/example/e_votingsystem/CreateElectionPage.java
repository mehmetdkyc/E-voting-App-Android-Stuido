package com.example.e_votingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CreateElectionPage extends AppCompatActivity {
    private DatePickerDialog datePickerDialogStart,datePickerDialogEnd;
    private Button dateButtonStart,dateButtonEnd, createElectionButton;
    private EditText nameOfElection;

    private ProgressBar progressBar;
    private DatabaseReference databaseReference,databaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_election_page);
        initDatePicker();

        dateButtonStart=findViewById(R.id.StartDatePicker);
        dateButtonEnd=findViewById(R.id.EndDatePicker);
        nameOfElection=findViewById(R.id.ElectionName);
        createElectionButton=findViewById(R.id.CreateElectionButton);
        progressBar=findViewById(R.id.progressBar1);
        dateButtonStart.setText(getTodaysDate());
        dateButtonEnd.setText(getTodaysDate());

        databaseReference =FirebaseDatabase.getInstance().getReference().child("Election");
        databaseRef =FirebaseDatabase.getInstance().getReference().child("Users");
        createElectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=nameOfElection.getText().toString().trim();
                String dateStartText=dateButtonStart.getText().toString().trim();
                String dateEndText=dateButtonEnd.getText().toString().trim();
                if(name.isEmpty()){
                    nameOfElection.setError("Full name is required");
                    nameOfElection.requestFocus();
                    return;
                }
                if(dateStartText.isEmpty()){
                    Toast.makeText(CreateElectionPage.this,"Select valid date",Toast.LENGTH_LONG).show();
                    dateButtonStart.requestFocus();
                    return;
                }
                if(dateEndText.isEmpty()){
                    Toast.makeText(CreateElectionPage.this,"Select valid date",Toast.LENGTH_LONG).show();
                    dateButtonEnd.requestFocus();
                    return;
                }
                Election election=new Election(name,dateStartText,dateEndText);


                databaseReference.push().setValue(election);
                Map<String,Integer> map=new HashMap<>();
                databaseRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                                User user=dataSnapshot.getValue(User.class);
                                String id=user.getIdentity();
                                map.put(id,0);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                });
                CandidatePage candidatePage=new CandidatePage();
                ArrayList<checkUserVoted> arrayList= new ArrayList<>();
                arrayList=candidatePage.getArrayList();
                arrayList.add(new checkUserVoted(map,name));
                candidatePage.setArrayList(arrayList);
                Toast.makeText(CreateElectionPage.this, String.valueOf(arrayList.size()), Toast.LENGTH_LONG).show();
                Toast.makeText(CreateElectionPage.this, "Successfully!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(CreateElectionPage.this, HomePageAdmin.class));

                progressBar.setVisibility(View.GONE);




            }
        });

    }

    private String getTodaysDate() {
        Calendar cal=Calendar.getInstance();
        int year=cal.get(Calendar.YEAR);
        int month=cal.get(Calendar.MONTH);
        month=month+1;
        int day=cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day,month,year);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker dataPicker, int year, int month, int day) {
                month=month+1;
                String date=makeDateString(day,month,year);
                dateButtonStart.setText(date);
            }
        };
        Calendar cal=Calendar.getInstance();
        int year=cal.get(Calendar.YEAR);
        int month=cal.get(Calendar.MONTH);
        int day=cal.get(Calendar.DAY_OF_MONTH);

        int style= AlertDialog.THEME_HOLO_LIGHT;

        DatePickerDialog.OnDateSetListener dateSetListenerEnd=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker dataPicker, int year, int month, int day) {
                month=month+1;
                String date=makeDateString(day,month,year);
                dateButtonEnd.setText(date);
            }
        };

        Calendar calEnd=Calendar.getInstance();
        int yearEnd=calEnd.get(Calendar.YEAR);
        int monthEnd=calEnd.get(Calendar.MONTH);
        int dayEnd=calEnd.get(Calendar.DAY_OF_MONTH);

        int styleEnd= AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialogStart=new DatePickerDialog(this,style,dateSetListener,year,month,day);
        datePickerDialogEnd=new DatePickerDialog(this,styleEnd,dateSetListenerEnd,yearEnd,monthEnd,dayEnd);

    }

    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month)+ " " +day+" "+year;
    }

    private String getMonthFormat(int month) {
        if(month==1)
            return "JAN";
        if(month==2)
            return "FEB";
        if(month==3)
            return "MAR";
        if(month==4)
            return "APR";
        if(month==5)
            return "MAY";
        if(month==6)
            return "JUN";
        if(month==7)
            return "JUL";
        if(month==8)
            return "AUG";
        if(month==9)
            return "SEP";
        if(month==10)
            return "OCT";
        if(month==11)
            return "NOV";
        if(month==12)
            return "DEC";
        return "JAN";
    }

    public void openDatePickerStart(View view) {
        datePickerDialogStart.show();
    }

    public void openDatePickerEnd(View view) {
        datePickerDialogEnd.show();
    }
}