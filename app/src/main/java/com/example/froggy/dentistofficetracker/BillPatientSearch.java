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

public class BillPatientSearch extends AppCompatActivity {

    private EditText name;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference myRef;
    private static final String TAG = "BillPatientSearch";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_patient_search);

        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference().getRoot();
    }

    public void onGo(View view){

        name = findViewById(R.id.bill_patients_name_search);
        final String patientName = name.getText().toString();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "Opened database: ");
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Log.d(TAG, ds.getKey());
                    if(ds.hasChild("name") && ds.child("name").getValue().toString().compareToIgnoreCase(patientName) == 0){
                        Intent i = new Intent(getApplicationContext(), BillPatient.class);
                        i.putExtra("username", ds.getKey());
                        startActivity(i);
                        return;
                    }
                }

                Toast.makeText(getApplicationContext(), "Patient not found!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void onViewBills(View view){
        name = findViewById(R.id.bill_patients_name_search);
        final String patientName = name.getText().toString();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "Opened database: ");
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Log.d(TAG, ds.getKey());
                    if(ds.hasChild("name") && ds.child("name").getValue().toString().compareToIgnoreCase(patientName) == 0){
                        Intent i = new Intent(getApplicationContext(), ViewBills.class);
                        i.putExtra("username", ds.getKey());
                        startActivity(i);
                        return;
                    }
                }

                Toast.makeText(getApplicationContext(), "Patient not found!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
