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

import java.util.Map;

public class ToothInfo extends Activity implements ValueEventListener {// this change lets the requestWindowFeature(Window.FEATURE_NO_TITLE) to work
    //public class ToothInfo extends AppCompatActivity {
    TextView teethNum; // used
    TextView textFaces;
    TextView textDiagnost;
    TextView textProcedure;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference myRef = firebaseDatabase.getReference(); //MrootReference
    DatabaseReference mTextViewPiece = myRef.child(getIntent().getExtras().getString("teethNumber")); //mheadingreference


    // This map will hold all the info from Firebase
    Map<String, Tooth> teeth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // this removes the title
        setContentView(R.layout.activity_tooth_info);

        // sets by intent the number on top of the piece diagram
        TextView teethNum = (TextView) findViewById(R.id.pieceNumber);
        teethNum.setText(getIntent().getExtras().getString("teethNumber"));

        // textviews that will be populated from fireBase
        textFaces = (TextView) findViewById((R.id.textFace));
        //textDiagnost = (TextView) findViewById((R.id.textDiag));
        //textProcedure = (TextView) findViewById((R.id.textProced));


    }


    /***
     // Call this to load the info from Firebase
      private void loadInfo() {
      myRef.child("Odontograma").addListenerForSingleValueEvent(new ValueEventListener() {

     @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
      for (DataSnapshot ds : dataSnapshot.getChildren()) {
      Tooth t = new Tooth();

      t.diagnostic = ds.child(getIntent().getExtras().getString("teethNumber")).child("Diagnostic").getValue().toString();
      t.face = ds.child(getIntent().getExtras().getString("teethNumber")).child("Face").child("Face").getValue().toString();
      t.procedure = ds.child(getIntent().getExtras().getString("teethNumber")).child("Face").child("Procedure").child("Procedure").getValue().toString();
     teeth.put(t.piece, t);
      }

      }
      @Override
    public void onCancelled(DatabaseError databaseError) {

      }
      });
      }
     *******/


    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        if (dataSnapshot.getValue(String.class) != null)
        {
            String key = dataSnapshot.getKey();

            if (key.equals("Face Name")) ;
            {
                String faceText = dataSnapshot.getValue(String.class);
                textFaces.setText(faceText);
            }

        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        System.out.println("The read failed: " + databaseError.getCode());
    }

    @Override
    protected void onStart() {


        super.onStart();
        mTextViewPiece.addValueEventListener(this);

    }



}





