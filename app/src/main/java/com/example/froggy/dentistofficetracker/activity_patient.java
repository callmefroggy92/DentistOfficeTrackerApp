package com.example.froggy.dentistofficetracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by froggy on 2/19/18.
 */

public class activity_patient extends AppCompatActivity {

    private String username;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);

        username = getIntent().getExtras().getString("username");
    }

    public void onViewBalance(View view){
        Intent i = new Intent(getApplicationContext(), View_Balance.class);
        i.putExtra("username", username);
        startActivity(i);
    }
}
