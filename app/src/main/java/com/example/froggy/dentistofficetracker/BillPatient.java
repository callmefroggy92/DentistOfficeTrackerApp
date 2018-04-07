package com.example.froggy.dentistofficetracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * <p>An activity that creates a bill for a patient, then submits that bill to the external
 * database. </p>
 *
 * @author Logan Holland
 * @since 4-7-2018
 * */
public class BillPatient extends AppCompatActivity {

    private EditText name;
    private EditText amount;
    private EditText details;
    private Calendar date;
    private Bill bill;
    private String username;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_patient);

        name = findViewById(R.id.bill_patients_name);
        amount = findViewById(R.id.bill_patients_amount);
        details = findViewById(R.id.bill_patients_details);

        username = getIntent().getExtras().getString("username", "");

        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference().getRoot();

        bill = new Bill();
    }

    /*
    * Submits the bill to Firebase
     */
    public void onSubmit(View view){

        date = GregorianCalendar.getInstance();

        bill.patient_name = name.getText().toString();
        bill.amount = Float.valueOf(amount.getText().toString());
        bill.details =details.getText().toString();
        bill.date = date;

        Gson gson = new Gson();

        final String json = gson.toJson(bill);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(username)){
                    if(!dataSnapshot.child(username).hasChild("bills"))
                        myRef.child(username).child("bills").push();
                    myRef.child(username).child("bills").child("bill" + dataSnapshot.child(username).child("bills").getChildrenCount()).push();
                    myRef.child(username).child("bills").child("bill" + dataSnapshot.child(username).child("bills").getChildrenCount()).setValue(json);

                    Toast.makeText(getApplicationContext(), "Bill successfully submitted!", Toast.LENGTH_SHORT).show();
                    finish();

                    return;
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }
}
