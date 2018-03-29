package com.example.froggy.dentistofficetracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ViewBills extends AppCompatActivity {

    private String name;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    List<Bill> bills;
    LinearLayout lLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bills);

        name = getIntent().getExtras().getString("username", "");

        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference().getRoot();

        bills = new ArrayList<>();

        lLayout = findViewById(R.id.ViewBillsLinearLayout);

        getBills();

    }


    public void getBills(){

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Gson gson = new Gson();
                if(dataSnapshot.child(name).hasChild("bills")){
                    for(DataSnapshot ds : dataSnapshot.child(name).child("bills").getChildren()){
                        bills.add(gson.fromJson(ds.getValue().toString(), Bill.class));
                    }
                }

                showBills();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void showBills(){

        for(Bill b : bills){
            TextView t = new TextView(getApplicationContext());
            t.setText("| NAME: " + b.patient_name + " | DATE: " + b.date.getTime().toString() + " | AMOUNT: " + b.amount +
                    " | DETAILS: " + b.details + " | PAID: " + b.paid + " |\n");
            lLayout.addView(t);
        }
    }
}
