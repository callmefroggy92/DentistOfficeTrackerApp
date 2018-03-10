package com.example.froggy.dentistofficetracker;

import android.content.Intent;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onLogin(View view) throws AuthException{
        EditText username_text = findViewById(R.id.username_text);
        final String username = username_text.getText().toString();
        EditText password_text = findViewById(R.id.password_text);
        final String password = password_text.getText().toString();

        firebaseDatabase = FirebaseDatabase.getInstance();

            myRef = firebaseDatabase.getReference();

            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if(dataSnapshot.hasChild(username)) {
                        Gson gson = new Gson();

                        user = new User();
                        user.setSalt(dataSnapshot.child(username).child("salt").getValue().toString());
                        user.setUsername(dataSnapshot.child(username).child("username").getValue().toString());
                        user.setKey(dataSnapshot.child(username).child("key").getValue().toString());
                        user.setHash(dataSnapshot.child(username).child("hash").getValue().toString());

                        String userType = dataSnapshot.child(username).child("type").getValue().toString();
                        if(userType.equals("Admin"))
                            user.type = UserType.Admin;
                        else if(userType.equals("Patient"))
                            user.type = UserType.Patient;
                        else if(userType.equals("Dentist"))
                            user.type = UserType.Dentist;
                        else {
                            Toast.makeText(getApplicationContext(), "Invalid user file!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        user.setPassword(password);
                        try {
                            if (LoginController.verifyPassword(user)) {
                                Toast.makeText(getApplicationContext(), "Login Succesful!", Toast.LENGTH_SHORT).show();

                                if (user.getType() == UserType.Admin) {

                                    Intent i = new Intent(getApplicationContext(), activity_admin.class);
                                    i.putExtra("username", user.getUsername());
                                    i.putExtra("key", user.getDecKey());
                                    startActivity(i);
                                } else if (user.getType() == UserType.Patient) {

                                    Intent i = new Intent(getApplicationContext(), activity_patient.class);
                                    i.putExtra("username", user.getUsername());
                                    i.putExtra("key", user.getDecKey());
                                    startActivity(i);
                                } else if (user.getType() == UserType.Dentist) {

                                    Intent i = new Intent(getApplicationContext(), activity_dentist.class);
                                    i.putExtra("username", user.getUsername());
                                    i.putExtra("key", user.getDecKey());
                                    startActivity(i);
                                } else {
                                    throw new AuthException("Error: User file inaccessible");
                                }

                            } else {
                                Toast.makeText(getApplicationContext(), "Invalid credentials", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("LoginController: ", e.getMessage());
                        }
                    }
                    else
                        Toast.makeText(getApplicationContext(), "Invalid credentials", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w("onCancelled: ", "Failed to read value.", error.toException());
                }
            });
    }
}
