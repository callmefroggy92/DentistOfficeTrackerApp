package com.example.froggy.dentistofficetracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
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
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    private User user;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference myRef;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Shared preferences retrieve the last username used for login
        prefs = getPreferences(Context.MODE_PRIVATE);
        if(prefs.contains("username")){
            EditText username_text = findViewById(R.id.username_text);
            username_text.setText(prefs.getString("username", ""));
        }

    }

    public void onLogin(View view) throws AuthException{
        EditText username_text = findViewById(R.id.username_text);
        final String username = username_text.getText().toString();
        EditText password_text = findViewById(R.id.password_text);
        final String password = password_text.getText().toString();

        // Create Firebase and reference
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();

        // EventListener retrieves values from the database and updates the application if anything changes
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // First, check to see if the given username exists in the database
                if (dataSnapshot.hasChild(username)) {
                    Gson gson = new Gson();

                    // create a new user object and fill it with login information
                    user = new User();
                    user.setSalt(dataSnapshot.child(username).child("salt").getValue().toString());
                    user.setUsername(dataSnapshot.child(username).child("username").getValue().toString());
                    user.setKey(dataSnapshot.child(username).child("key").getValue().toString());
                    user.setHash(dataSnapshot.child(username).child("hash").getValue().toString());

                    // The type determines where the user is sent after login
                    String userType = dataSnapshot.child(username).child("type").getValue().toString();
                    if (userType.equals("Admin"))
                        user.type = UserType.Admin;
                    else if (userType.equals("Patient"))
                        user.type = UserType.Patient;
                    else if (userType.equals("Dentist"))
                        user.type = UserType.Dentist;
                    else {
                        // If it has no type, it is not vaild
                        Toast.makeText(getApplicationContext(), "Invalid user file!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    user.setPassword(password);
                    try {
                        // Password verification
                        if (LoginController.verifyPassword(user)) {
                            Toast.makeText(getApplicationContext(), "Login Succesful!", Toast.LENGTH_SHORT).show();

                            // If login was successful, the username is saved in sharedpreferences
                            // until the next login
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putString("username", user.getUsername());
                            editor.apply();

                            // Sends the user to different activity depending on the usertype
                            if (user.getType() == UserType.Admin) {
                                //Intent i = new Intent(getApplicationContext(), activity_admin.class);
                                Intent i = new Intent(getApplicationContext(), NavigationDrawer.class);
                                i.putExtra("username", user.getUsername());
                                i.putExtra("key", user.getDecKey());
                                //startActivity(i);
                                startActivity(i);
                            } else if (user.getType() == UserType.Patient) {
                               // Intent i = new Intent(getApplicationContext(), activity_patient.class);
                                Intent i = new Intent(getApplicationContext(), NavigationDrawer.class);
                                i.putExtra("username", user.getUsername());
                                i.putExtra("key", user.getDecKey());
                               // startActivity(i);
                                startActivity(i);
                            } else if (user.getType() == UserType.Dentist) {
                                Intent i = new Intent(getApplicationContext(), NavigationDrawer.class);
                               // Intent i = new Intent(getApplicationContext(), activity_dentist.class);
                                i.putExtra("username", user.getUsername());
                                i.putExtra("key", user.getDecKey());
                                startActivity(i);
                            } else {
                                throw new AuthException("Error: User file inaccessible");
                            }

                        } else {
                            // If the password is wrong. . .
                            Toast.makeText(getApplicationContext(), "Invalid credentials", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Log.e("LoginController: ", e.getMessage());
                    }
                } else
                    // If the username is wrong. . .
                    Toast.makeText(getApplicationContext(), "Invalid credentials", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("onCancelled: ", "Failed to read value.", error.toException());
            }
        };
        // Add the ValueEventListener to login the user.
        // This function only retrieves the data once instead of continuously
        // looking for updates.
        myRef.addListenerForSingleValueEvent(listener);

    }
}
