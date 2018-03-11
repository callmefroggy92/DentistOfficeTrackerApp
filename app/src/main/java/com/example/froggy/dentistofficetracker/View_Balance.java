package com.example.froggy.dentistofficetracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class View_Balance extends AppCompatActivity {

    Bundle extras;
    private String username;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference myRef;
    private String balance;
    private TextView balanceText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__balance);

        // retrieves the username
        extras = getIntent().getExtras();
        username = extras.getString("username");

        // Create a Firebase instance
        firebaseDatabase = FirebaseDatabase.getInstance();

        // Sets the reference to root
        myRef = firebaseDatabase.getReference().getRoot();

        // Listens for events
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Ensures that the specified user exists.  If exists, verifies has a balance
                if(dataSnapshot.hasChild(username) && dataSnapshot.child(username).hasChild("balance")) {
                    balance = dataSnapshot.child(username).child("balance").getValue().toString();
                }
                // Defaults to "0" for no balance
                else
                    balance = "0";

                balanceText = findViewById(R.id.patient_balance);
                balanceText.setText("Current Balance: " + balance);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
