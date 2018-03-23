package com.example.froggy.dentistofficetracker;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

/**
 *
 *
 *
 *
 *
 */
public class TeethDiagram extends Activity {// this change lets the requestWindowFeature(Window.FEATURE_NO_TITLE) to work
    //public class TeethDiagram extends AppCompatActivity {


    String bottonVal = null;

    // Adult teeth buttons
    private Button botton11;
    private Button botton12;
    private Button botton13;
    private Button botton14;
    private Button botton15;
    private Button botton16;
    private Button botton17;
    private Button botton18;

    private Button botton21;
    private Button botton22;
    private Button botton23;
    private Button botton24;
    private Button botton25;
    private Button botton26;
    private Button botton27;
    private Button botton28;

    private Button botton31;
    private Button botton32;
    private Button botton33;
    private Button botton34;
    private Button botton35;
    private Button botton36;
    private Button botton37;
    private Button botton38;

    private Button botton41;
    private Button botton42;
    private Button botton43;
    private Button botton44;
    private Button botton45;
    private Button botton46;
    private Button botton47;
    private Button botton48;

    //Kids teeth buttons
    private Button botton51;
    private Button botton52;
    private Button botton53;
    private Button botton54;
    private Button botton55;

    private Button botton61;
    private Button botton62;
    private Button botton63;
    private Button botton64;
    private Button botton65;

    private Button botton71;
    private Button botton72;
    private Button botton73;
    private Button botton74;
    private Button botton75;

    private Button botton81;
    private Button botton82;
    private Button botton83;
    private Button botton84;
    private Button botton85;


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



        botton11 = (Button) findViewById(R.id.a11ActionButton);
        botton12 = (Button) findViewById(R.id.a12ActionButton);
        botton13 = (Button) findViewById(R.id.a13ActionButton);
        botton14 = (Button) findViewById(R.id.a14ActionButton);
        botton15 = (Button) findViewById(R.id.a15ActionButton);
        botton16 = (Button) findViewById(R.id.a16ActionButton);
        botton17 = (Button) findViewById(R.id.a17ActionButton);
        botton18 = (Button) findViewById(R.id.a18ActionButton);

        botton21 = (Button) findViewById(R.id.b21ActionButton);
        botton22 = (Button) findViewById(R.id.b22ActionButton);
        botton23 = (Button) findViewById(R.id.b23ActionButton);
        botton24 = (Button) findViewById(R.id.b24ActionButton);
        botton25 = (Button) findViewById(R.id.b25ActionButton);
        botton26 = (Button) findViewById(R.id.b26ActionButton);
        botton27 = (Button) findViewById(R.id.b27ActionButton);
        botton28 = (Button) findViewById(R.id.b28ActionButton);

        botton31 = (Button) findViewById(R.id.d31ActionButton);
        botton32 = (Button) findViewById(R.id.d32ActionButton);
        botton33 = (Button) findViewById(R.id.d33ActionButton);
        botton34 = (Button) findViewById(R.id.d34ActionButton);
        botton35 = (Button) findViewById(R.id.d35ActionButton);
        botton36 = (Button) findViewById(R.id.d36ActionButton);
        botton37 = (Button) findViewById(R.id.d37ActionButton);
        botton38 = (Button) findViewById(R.id.d38ActionButton);

        botton41 = (Button) findViewById(R.id.c41ActionButton);
        botton42 = (Button) findViewById(R.id.c42ActionButton);
        botton43 = (Button) findViewById(R.id.c43ActionButton);
        botton44 = (Button) findViewById(R.id.c44ActionButton);
        botton45 = (Button) findViewById(R.id.c45ActionButton);
        botton46 = (Button) findViewById(R.id.c46ActionButton);
        botton47 = (Button) findViewById(R.id.c47ActionButton);
        botton48 = (Button) findViewById(R.id.c48ActionButton);

        botton51 = (Button) findViewById(R.id.e51kidAButton);
        botton52 = (Button) findViewById(R.id.e52kidAButton);
        botton53 = (Button) findViewById(R.id.e53kidAButton);
        botton54 = (Button) findViewById(R.id.e54kidAButton);
        botton55 = (Button) findViewById(R.id.e55kidAButton);

        botton61 = (Button) findViewById(R.id.f61kidAButton);
        botton62 = (Button) findViewById(R.id.f62kidAButton);
        botton63 = (Button) findViewById(R.id.f63kidAButton);
        botton64 = (Button) findViewById(R.id.f64kidAButton);
        botton65 = (Button) findViewById(R.id.f65kidAButton);

        botton71 = (Button) findViewById(R.id.g81kidAButton);
        botton72 = (Button) findViewById(R.id.g82kidAButton);
        botton73 = (Button) findViewById(R.id.g83kidAButton);
        botton74 = (Button) findViewById(R.id.g84kidAButton);
        botton75 = (Button) findViewById(R.id.g85kidAButton);

        botton81 = (Button) findViewById(R.id.h71kidAButton);
        botton82 = (Button) findViewById(R.id.h72kidAButton);
        botton83 = (Button) findViewById(R.id.h73kidAButton);
        botton84 = (Button) findViewById(R.id.h74kidAButton);
        botton85 = (Button) findViewById(R.id.h75kidAButton);



    }

    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.a11ActionButton:
                bottonVal = "11";

                break;

            case R.id.a12ActionButton:
                bottonVal = "12";

                break;

            default:
                break;
        }

        Toast.makeText(this, "you clicked " +  bottonVal, Toast.LENGTH_LONG).show();
        Intent i = new Intent(this,ToothInfo.class);
        startActivity(i);
        // Remember to use in the XML android:onClick="" and add the method to use at click
    }



}
