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



        TextView teethNum = (TextView) findViewById(R.id.pieceNumber);
        teethNum.setText(getIntent().getExtras().getString("teethNumber"));




/**

            // Call this to load the info from Firebase
            private void loadInfo() {
                myRef.child("Odontograma").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            Tooth t = new Tooth();
                            t.piece = ds.child("Piece").child("Piece").getValue().toString();
                            t.diagnostic = ds.child("Piece").child("Diagnostic").getValue().toString();
                            t.face = ds.child("Piece").child("Face").child("Face").getValue().toString();
                            t.procedure = ds.child("Piece").child("Face").child("Procedure").child("Procedure").getValue().toString();
                            teeth.put(t.piece, t);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }





            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

 **/

    }

}