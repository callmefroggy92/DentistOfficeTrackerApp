package com.example.froggy.dentistofficetracker;

import android.content.Intent;
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
import com.google.gson.Gson;

/**
 * The Search_Patient activity searches through the database to locate the
 * desired patient.
 */

public class Search_Patient extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference myRef;
    private EditText patientName;
    private String name;

    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search__patient);

        patientName = findViewById(R.id.search_patient_name);
        name = "";

        username = getIntent().getExtras().getString("username", "");

    }

    public void onSearch(View view){

        // Initiates the Firebase and sets the reference to root
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference().getRoot();

        // Retrieves the name of the patient being searched for
        name = patientName.getText().toString();

        // EventListenver retrieves values from database
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Searches entire database for specified patient
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    if(ds.hasChild("name") && ds.child("name").getValue().toString().compareToIgnoreCase(name) == 0) {

                        // When found, creates a patient object and fills in all values
                        Patient p = new Patient();
                        p.setEmail(ds.child("email").getValue().toString());
                        p.setName(ds.child("name").getValue().toString());
                        p.setAddress(ds.child("address").getValue().toString());
                        p.setTelephone(ds.child("telephone").getValue().toString());
                        p.setFathersTelephone(ds.child("father_telephone").getValue().toString());
                        p.setFathersName(ds.child("father_name").getValue().toString());
                        p.setMothersName(ds.child("mother_name").getValue().toString());
                        p.setMothersTelephone(ds.child("mother_telephone").getValue().toString());
                        p.setNameOfSchool(ds.child("school_name").getValue().toString());
                        p.setGradeInSchool(ds.child("school_grade").getValue().toString());
                        p.setUsername(ds.child("username").getValue().toString());

                        // Sends info on to the display activity
                        Intent i = new Intent(getApplicationContext(), View_Patient_Info.class);
                        Gson gson = new Gson();
                        i.putExtra("patient", gson.toJson(p));
                        startActivity(i);
                        return;
                    }
                }

                // If it has made it this far, the patient has not been found
                Toast.makeText(getApplicationContext(), "Not found!", Toast.LENGTH_SHORT).show();
                return;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
