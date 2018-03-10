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

public class Search_Patient extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    EditText patientName;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search__patient);

        patientName = findViewById(R.id.search_patient_name);
        name = "";

    }

    public void onSearch(View view){

        firebaseDatabase = FirebaseDatabase.getInstance();

        name = patientName.getText().toString();

        myRef = firebaseDatabase.getReference().getRoot();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    if(ds.hasChild("name") && ds.child("name").getValue().toString().compareToIgnoreCase(name) == 0) {
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

                        Intent i = new Intent(getApplicationContext(), View_Patient_Info.class);
                        Gson gson = new Gson();
                        i.putExtra("patient", gson.toJson(p));
                        startActivity(i);
                        return;
                    }
                }

                Toast.makeText(getApplicationContext(), "Not found!", Toast.LENGTH_SHORT).show();
                return;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
