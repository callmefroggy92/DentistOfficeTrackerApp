package com.example.froggy.dentistofficetracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

/**
 * ViewBills retrieves the bills from the external database then displays them to the screen.
 */
public class ViewBills extends AppCompatActivity {

    private String name;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference myRef;
    private List<Bill> bills;
    private LinearLayout lLayout;

    private static final String TAG = "ViewBills";

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


    /*
     * getBills() is a public function designed to retrieve the bills from the
     * external database
     */
    public void getBills(){

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i(TAG, "Retrieving bills");
                Gson gson = new Gson();

                // All bills are taken from the database and stored to the arrayList
                if(dataSnapshot.hasChild(name) && dataSnapshot.child(name).hasChild("bills")){
                    for(DataSnapshot ds : dataSnapshot.child(name).child("bills").getChildren()){
                        bills.add(gson.fromJson(ds.getValue().toString(), Bill.class));
                    }
                }

                // Bills are only displayed after all bills are retrieved
                // from the external database
                showBills();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /*
    * showBills() displays the bills on the screen
     */
    public void showBills(){

        Log.i(TAG, "Displaying bills");
        for(Bill b : bills){
            TextView t = new TextView(getApplicationContext());
            t.setText(" | NAME: " + b.patient_name + "\n | DATE: " + b.date.getTime().toString() + " \n | AMOUNT: " + b.amount +
                    "\n | DETAILS: " + b.details + "\n | PAID: " + b.paid + " \n _______________\n");

            lLayout.addView(t);
        }
    }
}
