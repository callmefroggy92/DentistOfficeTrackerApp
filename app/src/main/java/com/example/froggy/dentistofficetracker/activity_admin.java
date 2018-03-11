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

public class activity_admin extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
    }

    public void onAddPatient(View view){
        Intent i = new Intent(getApplicationContext(), Add_Patient.class);
        startActivity(i);
    }

    public void onViewPatient(View view){
        Intent i = new Intent(getApplicationContext(), Search_Patient.class);
        startActivity(i);
    }

    public void onViewBalance(View view){

    }
}
