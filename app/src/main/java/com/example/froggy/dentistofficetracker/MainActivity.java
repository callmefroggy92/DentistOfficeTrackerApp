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
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    private BufferedReader databaseReader;
    private UserDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            InputStream is = getAssets().open("userdatabase.txt");
            databaseReader = new BufferedReader(new InputStreamReader(is));

        } catch(Exception e){
            Log.e("onCreate: ", e.getMessage());
        }

        Gson gson = new Gson();
        database = gson.fromJson(databaseReader, UserDatabase.class);


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onLogin(View view) throws AuthException{
        EditText username_text = findViewById(R.id.username_text);
        String username = username_text.getText().toString();
        EditText password_text = findViewById(R.id.password_text);
        String password = password_text.getText().toString();

        User user = new User();
        try{
            InputStream is = getAssets().open(username + ".txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            Gson gson = new Gson();

            user = gson.fromJson(reader, User.class);

        }catch(Exception e){
            Log.e("onLogin: ", e.getMessage());
        }

        user.setPassword(password);
        try {
            if(LoginController.verifyPassword(user)){
                Toast.makeText(getApplicationContext(), "Login Succesful!", Toast.LENGTH_SHORT).show();

                if(user.getType() == UserType.Admin){

                    Intent i = new Intent(getApplicationContext(), activity_admin.class);
                    i.putExtra("username", user.getUsername());
                    i.putExtra("key", user.getDecKey());
                    startActivity(i);
                }

                else if(user.getType() == UserType.Patient){

                    Intent i = new Intent(getApplicationContext(), activity_patient.class);
                    i.putExtra("username", user.getUsername());
                    i.putExtra("key", user.getDecKey());
                    startActivity(i);
                }

                else if(user.getType() == UserType.Dentist){

                    Intent i = new Intent(getApplicationContext(), activity_dentist.class);
                    i.putExtra("username", user.getUsername());
                    i.putExtra("key", user.getDecKey());
                    startActivity(i);
                }
                else{
                    throw new AuthException("Error: User file inaccessible");
                }

            }
            else{
                Toast.makeText(getApplicationContext(), "Invalid credentials", Toast.LENGTH_SHORT).show();
            }
        } catch(Exception e){
            Log.e("LoginController: ", e.getMessage());
        }
    }
}
