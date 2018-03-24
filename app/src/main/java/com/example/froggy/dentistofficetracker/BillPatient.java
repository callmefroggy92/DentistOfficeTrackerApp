package com.example.froggy.dentistofficetracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * An activity that creates a bill for a patient, then submits that bill to the external
 * database.
 * */
public class BillPatient extends AppCompatActivity {

    EditText name;
    EditText amount;
    EditText details;
    Calendar date;
    Bill bill;
    String username;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;

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
                    return;
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }
}
