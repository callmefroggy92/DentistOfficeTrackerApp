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

/**
 * <p>An activity designed to search through the database for a Patient.  If found
 * the patient should be passed on to the next activity.</p>
 *
 * @author Logan Holland
 * @since 4-7-2018
 */
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

    /**
     * <p> This function is called by the New Bill button in the corresponding activity.  When called, it
     * searches through the database for the appropriate patient.  If found, it calls the next activity.
     * If not found, it notifies the user. </p>
     * @param view
     */
    public void onGo(View view){

        Log.d(TAG, "New Bill button pressed");

        name = findViewById(R.id.bill_patients_name_search);
        final String patientName = name.getText().toString();

        // To retrieve information from the database
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "Opened database ");

                // Searches through all User accounts in the database
                for(DataSnapshot ds : dataSnapshot.getChildren()){
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

    /**
     * <p> This function is called by the View Bills button in the corresponding activity.  When called, it
     * searches through the database for the appropriate patient.  If found, it calls the next activity.
     * If not found, it notifies the user. </p>
     * @param view
     */
    public void onViewBills(View view){
        name = findViewById(R.id.bill_patients_name_search);
        final String patientName = name.getText().toString();
        Log.d(TAG, "View Bill button pressed");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "Opened database");

                // Searches through all Users to find the desired patient
                for(DataSnapshot ds : dataSnapshot.getChildren()){
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
