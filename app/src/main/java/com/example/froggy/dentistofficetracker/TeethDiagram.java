package com.example.froggy.dentistofficetracker;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class TeethDiagram extends Activity {// this change lets the requestWindowFeature(Window.FEATURE_NO_TITLE) to work
    //public class TeethDiagram extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // this removes the title
        setContentView(R.layout.activity_teeth_diagram);


        // PIECE Spinner
        Spinner myPieceSpinner =(Spinner) findViewById(R.id.spinnerPiecelist);
        ArrayAdapter<String> myPieceAdapter = new ArrayAdapter<String>(TeethDiagram.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.pieceList));

        myPieceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        myPieceSpinner.setAdapter(myPieceAdapter);



        // FACE Spinner
        Spinner myFaceSpinner =(Spinner) findViewById(R.id.spinnerFaceList);
        ArrayAdapter<String> myFaceAdapter = new ArrayAdapter<String>(TeethDiagram.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.faceList));

        myFaceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        myFaceSpinner.setAdapter(myFaceAdapter);


        // DIAGNOSTICS Spinner
        Spinner myDiagSpinner =(Spinner) findViewById(R.id.spinnerDiagList);
        ArrayAdapter<String> myDiagAdapter = new ArrayAdapter<String>(TeethDiagram.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.diagList));

        myDiagAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        myDiagSpinner.setAdapter(myDiagAdapter);


        // PROCEDURE Spinner
        Spinner myProcSpinner =(Spinner) findViewById(R.id.spinnerProcList);
        ArrayAdapter<String> myProcAdapter = new ArrayAdapter<String>(TeethDiagram.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.procList));

        myProcAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        myProcSpinner.setAdapter(myProcAdapter);

    }

    public void onClick(View v)
    {
        Intent i = new Intent(this,ToothInfo.class);
        startActivity(i);
        // Remember to use in the XML android:onClick="" and add the method to use at click
    }



}
