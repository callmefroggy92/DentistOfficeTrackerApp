package com.example.froggy.dentistofficetracker;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 *
 *
 *
 *
 *
 */

public class TeethDiagram extends Activity implements AdapterView.OnItemSelectedListener{// this change lets the requestWindowFeature(Window.FEATURE_NO_TITLE) to work
    //public class TeethDiagram extends AppCompatActivity {


    String bottonVal = null;
    String item;
    String item2;
    String item3;
    String item4;

    private String username;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // this removes the title
        setContentView(R.layout.activity_teeth_diagram);

        username = getIntent().getExtras().getString("username");

        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference().child(username);

        // PIECE Spinner
        Spinner myPieceSpinner = (Spinner) findViewById(R.id.spinnerPiecelist);
        ArrayAdapter<String> myPieceAdapter = new ArrayAdapter<String>(TeethDiagram.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.pieceList));

        myPieceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        myPieceSpinner.setAdapter(myPieceAdapter);
        myPieceSpinner.setOnItemSelectedListener(this); // new!!!!!!!!!!!



        // FACE Spinner
        Spinner myFaceSpinner =(Spinner) findViewById(R.id.spinnerFaceList);
        ArrayAdapter<String> myFaceAdapter = new ArrayAdapter<String>(TeethDiagram.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.faceList));

        myFaceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        myFaceSpinner.setAdapter(myFaceAdapter);
        myFaceSpinner.setOnItemSelectedListener(this); // new!!!!!!!!!!!



        // DIAGNOSTICS Spinner
        Spinner myDiagSpinner =(Spinner) findViewById(R.id.spinnerDiagList);



        ArrayAdapter<String> myDiagAdapter = new ArrayAdapter<String>(TeethDiagram.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.diagList));

        myDiagAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        myDiagSpinner.setAdapter(myDiagAdapter);

        myDiagSpinner.setOnItemSelectedListener(this); // new!!!!!!!!!!!


        // PROCEDURE Spinner
        Spinner myProcSpinner =(Spinner) findViewById(R.id.spinnerProcList);
        ArrayAdapter<String> myProcAdapter = new ArrayAdapter<String>(TeethDiagram.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.procList));

        myProcAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        myProcSpinner.setAdapter(myProcAdapter);

        myProcSpinner.setOnItemSelectedListener(this); // new!!!!!!!!!!!


    }




    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    // onclick function used from the OnClick attribute at XML file
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.a18ActionButton:
            bottonVal = "18";
            break;
            case R.id.a17ActionButton:
                bottonVal = "17";
                break;
            case R.id.a16ActionButton:
                bottonVal = "16";
                break;
            case R.id.a15ActionButton:
                bottonVal = "15";
                break;
            case R.id.a14ActionButton:
                bottonVal = "14";
                break;
            case R.id.a13ActionButton:
                bottonVal = "13";
                break;
            case R.id.a12ActionButton:
                bottonVal = "12";
                break;
            case R.id.a11ActionButton:
                bottonVal = "11";
                break;


            case R.id.b28ActionButton:
                bottonVal = "28";
                break;
            case R.id.b27ActionButton:
                bottonVal = "27";
                break;
            case R.id.b26ActionButton:
                bottonVal = "26";
                break;
            case R.id.b25ActionButton:
                bottonVal = "25";
                break;
            case R.id.b24ActionButton:
                bottonVal = "24";
                break;
            case R.id.b23ActionButton:
                bottonVal = "23";
                break;
            case R.id.b22ActionButton:
                bottonVal = "22";
                break;
            case R.id.b21ActionButton:
                bottonVal = "21";
                break;


            case R.id.d38ActionButton:
                bottonVal = "38";
                break;
            case R.id.d37ActionButton:
                bottonVal = "37";
                break;
            case R.id.d36ActionButton:
                bottonVal = "36";
                break;
            case R.id.d35ActionButton:
                bottonVal = "35";
                break;
            case R.id.d34ActionButton:
                bottonVal = "34";
                break;
            case R.id.d33ActionButton:
                bottonVal = "33";
                break;
            case R.id.d32ActionButton:
                bottonVal = "32";
                break;
            case R.id.d31ActionButton:
                bottonVal = "31";
                break;


            case R.id.c48ActionButton:
                bottonVal = "48";
                break;
            case R.id.c47ActionButton:
                bottonVal = "47";
                break;
            case R.id.c46ActionButton:
                bottonVal = "46";
                break;
            case R.id.c45ActionButton:
                bottonVal = "45";
                break;
            case R.id.c44ActionButton:
                bottonVal = "44";
                break;
            case R.id.c43ActionButton:
                bottonVal = "43";
                break;
            case R.id.c42ActionButton:
                bottonVal = "42";
                break;
            case R.id.c41ActionButton:
                bottonVal = "41";
                break;


            case R.id.e55kidAButton:
                bottonVal = "55";
                break;
            case R.id.e54kidAButton:
                bottonVal = "54";
                break;
            case R.id.e53kidAButton:
                bottonVal = "53";
                break;
            case R.id.e52kidAButton:
                bottonVal = "52";
                break;
            case R.id.e51kidAButton:
                bottonVal = "51";
                break;


            case R.id.f65kidAButton:
                bottonVal = "65";
                break;
            case R.id.f64kidAButton:
                bottonVal = "64";
                break;
            case R.id.f63kidAButton:
                bottonVal = "63";
                break;
            case R.id.f62kidAButton:
                bottonVal = "62";
                break;
            case R.id.f61kidAButton:
                bottonVal = "61";
                break;


            case R.id.h75kidAButton:
                bottonVal = "75";
                break;
            case R.id.h74kidAButton:
                bottonVal = "74";
                break;
            case R.id.h73kidAButton:
                bottonVal = "73";
                break;
            case R.id.h72kidAButton:
                bottonVal = "72";
                break;
            case R.id.h71kidAButton:
                bottonVal = "71";
                break;

            case R.id.g85kidAButton:
                bottonVal = "85";
                break;
            case R.id.g84kidAButton:
                bottonVal = "84";
                break;
            case R.id.g83kidAButton:
                bottonVal = "83";
                break;
            case R.id.g82kidAButton:
                bottonVal = "82";
                break;
            case R.id.g81kidAButton:
                bottonVal = "81";
                break;

            default:
                break;
        }

        Toast.makeText(this, "you clicked " +  bottonVal, Toast.LENGTH_LONG).show();
        Intent i = new Intent(this,ToothInfo.class);
        i.putExtra("teethNumber", bottonVal);
        startActivity(i);
        // Remember to use in the XML android:onClick="" and add the method to use at click

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Spinner spinner = (Spinner) parent; // im not sure if this is the rigth way to do this


        if(spinner.getId() == R.id.spinnerPiecelist)
        {
            item4 = parent.getItemAtPosition(position).toString();
            Toast.makeText(this, item4 , Toast.LENGTH_LONG).show();
        }

        if(spinner.getId() == R.id.spinnerFaceList)
        {
            item3 = parent.getItemAtPosition(position).toString();
            Toast.makeText(this, item3 , Toast.LENGTH_LONG).show();
        }

        if(spinner.getId() == R.id.spinnerDiagList)
        {
            item2 = parent.getItemAtPosition(position).toString();
            Toast.makeText(this, item2 , Toast.LENGTH_LONG).show();
        }


        if(spinner.getId() == R.id.spinnerProcList)
        {
            item = parent.getItemAtPosition(position).toString();
            Toast.makeText(this, item , Toast.LENGTH_LONG).show();
        }



    }


    //this function will submit the info to Firebase
    public void OnButtonSend(){

       DatabaseReference mRefChild = myRef.child("Piece");
       mRefChild.setValue(item4);

       myRef.child("Piece").child("Face");
       mRefChild.setValue(item3);

       myRef.child("Piece").child("Face").child("Diagnostic");
       mRefChild.setValue(item2);

       myRef.child("Piece").child("Face").child("Procedures");
       mRefChild.setValue(item);


   }





}
