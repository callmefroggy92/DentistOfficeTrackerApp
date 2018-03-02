package com.example.froggy.dentistofficetracker;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Add_Patient extends AppCompatActivity {

    EditText patientName;
    EditText patientAddress;
    EditText patientNumber;
    EditText patientEmail;
    EditText patientAge;
    EditText patientUsername;
    EditText patientPassword;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__patient);

        patientName = findViewById(R.id.patient_name);
        patientAddress = findViewById(R.id.patient_address);
        patientNumber = findViewById(R.id.patient_number);
        patientEmail = findViewById(R.id.patient_email);
        patientAge = findViewById(R.id.patient_age);
        patientUsername = findViewById(R.id.patient_username);
        patientPassword = findViewById(R.id.patient_password);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onSubmit(View view){
        Patient p = new Patient();

        p.setAddress(patientAddress.getText().toString());
        p.setName(patientName.getText().toString());
        p.setTelephone(patientNumber.getText().toString());
        p.setAge(Integer.valueOf(patientAge.getText().toString()));
        p.setUsername(patientUsername.getText().toString());
        p.setPassword(patientPassword.getText().toString());

        try {
            LoginController.hashUserPassword(p);
        } catch(Exception e){
            Log.e("HashPassword: ", e.getMessage());
        }


        firebaseDatabase = FirebaseDatabase.getInstance();

        Log.w("create", "started");
        myRef = firebaseDatabase.getReference().child(patientUsername.getText().toString());
        Log.w("Create", "done");

        final Patient u = p;

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                myRef.child("username").push();
                myRef.child("username").setValue(u.getUsername());
                myRef.child("hash").push();
                myRef.child("hash").setValue(u.getHash());
                myRef.child("address").push();
                myRef.child("address").setValue(u.getAddress());
                myRef.child("telephone").push();
                myRef.child("telephone").setValue(u.getTelephone());
                myRef.child("age").push();
                myRef.child("age").setValue(u.getAge());
                myRef.child("key").push();
                myRef.child("key").setValue(u.getKey());
                myRef.child("name").push();
                myRef.child("name").setValue(u.getName());
                myRef.child("salt").push();
                myRef.child("salt").setValue(u.getSalt());
                myRef.child("type").push();
                myRef.child("type").setValue(UserType.Patient);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
