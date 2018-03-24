package com.example.froggy.dentistofficetracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bills);

        name = getIntent().getExtras().getString("username", "");

        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference().getRoot();

        bills = new ArrayList<>();

        getBills();

        ListView listView = findViewById(R.id.listView);

        List<String> billStrings = new ArrayList<>();

        for(Bill b : bills){
            billStrings.add("Name: " + b.patient_name + " date: " + b.date + " amount: " + b.amount + " details: " + b.details + " paid: " + b.paid);
        }

        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.activity_list_view, billStrings);
        listView.setAdapter(adapter);

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
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
