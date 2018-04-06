package com.example.froggy.dentistofficetracker;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

public class ToothInfo extends Activity {// this change lets the requestWindowFeature(Window.FEATURE_NO_TITLE) to work
    //public class ToothInfo extends AppCompatActivity {
    TextView teethNum;
    TextView textDiagnost;
    TextView textProcedure;
    TextView textFaces;

    private DatabaseReference mDatabase;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // this removes the title
        setContentView(R.layout.activity_tooth_info);


        textDiagnost = (TextView) findViewById(R.id.textDiag);
        textProcedure = (TextView) findViewById(R.id.textProced);
        textFaces = (TextView) findViewById(R.id.textFace);

        teethNum.setText(getIntent().getExtras().getString("teethNumber"));





        mDatabase = FirebaseDatabase.getInstance().getReference("teethNumber");
        mDatabase.child("Face").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String face = dataSnapshot.getValue(String.class);
                textFaces.setText(face);

                String diag = dataSnapshot.getValue(String.class);
                textDiagnost.setText(diag);

                String proc = dataSnapshot.getValue(String.class);
                textProcedure.setText(proc);

            }




            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}