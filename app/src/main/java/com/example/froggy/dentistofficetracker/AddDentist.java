package com.example.froggy.dentistofficetracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * <p>The AddDentist activity allows an Admin user to create a new Dentist user and
 * add him or her to the database. </p>
 *
 * @author Logan Holland
 * @since 4-7-2018
 */
public class AddDentist extends AppCompatActivity {

    private EditText nameText;
    private EditText usernameText;
    private EditText passwordText;

    private static final String TAG = "AddDentist";

    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dentist);

        nameText = findViewById(R.id.AddDentistNameText);
        usernameText = findViewById(R.id.AddDentistUsernameText);
        passwordText = findViewById(R.id.AddDentistPasswordText);

        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference().getRoot();
    }

    // This method is called when the user hits the "Submit" button.
    // It creates a new Dentist object then passes it on to the submitDentist() method.
    public void createDentist(View view){
        Dentist dentist = new Dentist();

        dentist.setName(nameText.getText().toString());
        dentist.setUsername(usernameText.getText().toString());
        dentist.setPassword(passwordText.getText().toString());

        try{
            LoginController.hashUserPassword(dentist);
        } catch(Exception e){
            Log.e(TAG, e.getMessage());
        }

        submitDentist(dentist);
    }

    // This mehtod is called by the createDentist method.  It submits the Dentist object
    // to the database.
    private void submitDentist(final Dentist dentist){

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final String username = dentist.getUsername();

                // If the username has already been taken, the user must choose a new username
                if(dataSnapshot.hasChild(username)){
                    Toast.makeText(getApplicationContext(), "Username already taken!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Creates the new Dentist account in the database
                Log.d(TAG, "Submitting new Dentist to database");
                myRef.child(username).child("username").setValue(username);
                myRef.child(username).child("hash").setValue(dentist.getHash());
                myRef.child(username).child("key").setValue(dentist.getKey());
                myRef.child(username).child("type").setValue("Dentist");
                myRef.child(username).child("salt").setValue(dentist.getSalt());
                myRef.child(username).child("name").setValue(dentist.getName());
                Log.d(TAG, "New Dentist submitted!");
                Toast.makeText(getApplicationContext(), dentist.getName() + " successfully added!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
