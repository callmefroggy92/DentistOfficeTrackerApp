package com.example.froggy.dentistofficetracker;

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

public class ChangePassword extends AppCompatActivity {

    private EditText oldPasswordText;
    private EditText newPasswordText;
    private EditText verifyPasswordText;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;

    private final static String TAG = "ChangePassword";

    private String username;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        oldPasswordText = findViewById(R.id.OldPasswordText);
        newPasswordText = findViewById(R.id.NewPasswordText);
        verifyPasswordText = findViewById(R.id.VerifyNewPasswordText);

        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference().getRoot();

        username = getIntent().getExtras().getString("username");

        // Verifies that the username matches one that is stored in the database
        verifyUser();

    }

    private void verifyUser(){
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.hasChild(username)){
                    Toast.makeText(getApplicationContext(), "Error processing request", Toast.LENGTH_SHORT);
                    finish();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void changePassword(View view){
        user = new User();

        verifyPassword();
    }

    /*
    * First, the information is retrieved from the database and it is verified that the
    * entered password matches the stored password
     */
    private void verifyPassword(){

        user.setPassword(oldPasswordText.getText().toString());

        myRef.child(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Hash and salt retrieved from the database
                user.setSalt(dataSnapshot.child("salt").getValue().toString());
                user.setHash(dataSnapshot.child("hash").getValue().toString());

                try {
                    // Password verified
                    if(LoginController.verifyPassword(user))
                        // If the password is verified, the process continues
                        createUser();
                    else
                        Toast.makeText(getApplicationContext(), "Invalid password", Toast.LENGTH_SHORT).show();

                } catch (Exception e){
                    Log.e(TAG, e.getMessage());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    /*
    * Creates new credentials using the new password
     */
    private void createUser(){


        String newPassword = newPasswordText.getText().toString();
        String verifyPassword = verifyPasswordText.getText().toString();
        user.setHash("");
        user.setSalt("");

        // Assures that the two passwords match
        if(newPassword.compareTo(verifyPassword) == 0) {
            user.setPassword(newPassword);

            try {
                LoginController.hashUserPassword(user);
                // the process continues. . .
                updateChange();
            } catch (Exception e){
                Log.e(TAG, e.getMessage());
            }
        }
    }

    /*
    * Finally, after everything else, the new credentials are stored in the database
     */
    private void updateChange(){

        myRef.child(username).child("hash").setValue(user.getHash());
        myRef.child(username).child("salt").setValue(user.getSalt());
        myRef.child(username).child("key").setValue(user.getKey());

        Toast.makeText(getApplicationContext(), "Password successfully changed", Toast.LENGTH_SHORT).show();

        finish();
    }
}
