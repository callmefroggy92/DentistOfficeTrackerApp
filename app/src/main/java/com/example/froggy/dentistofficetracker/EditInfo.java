package com.example.froggy.dentistofficetracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditInfo extends AppCompatActivity {

    private EditText nameText;
    private EditText emailText;
    private EditText telephoneText;
    private EditText addressText;
    private EditText fathersNameText;
    private EditText fathersTelephoneText;
    private EditText mothersNameText;
    private EditText mothersTelephoneText;
    private EditText schoolsNameText;

    private Patient p;

    private String username;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference myRef;

    private static final String TAG = "EditInfo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);

        nameText = findViewById(R.id.EditInfoName);
        emailText = findViewById(R.id.EditInfoEmail);
        telephoneText = findViewById(R.id.EditInfoTelephone);
        addressText = findViewById(R.id.EditInfoAddress);
        fathersNameText = findViewById(R.id.EditInfoFathersName);
        fathersTelephoneText = findViewById(R.id.EditInfoFathersTelephone);
        mothersNameText = findViewById(R.id.EditInfoMothersName);
        mothersTelephoneText = findViewById(R.id.EditInfoMothersTelephone);
        schoolsNameText = findViewById(R.id.EditInfoNameOfSchool);

        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference().getRoot();

        p = new Patient();

        username = getIntent().getExtras().getString("username", "");

        if(username.compareTo("") == 0){
            Toast.makeText(getApplicationContext(), "Unable to access user file", Toast.LENGTH_SHORT).show();
            finish();
        }

        retrieveUserInfo();

    }

    private void fillForms(){
        nameText.setText(p.getName());
        emailText.setText(p.getEmail());
        addressText.setText(p.getAddress());
        telephoneText.setText(p.getTelephone());
        fathersNameText.setText(p.getFathersName());
        fathersTelephoneText.setText(p.getFathersTelephone());
        mothersNameText.setText(p.getMothersName());
        mothersTelephoneText.setText(p.getMothersTelephone());
        schoolsNameText.setText(p.getNameOfSchool());

    }

    private void retrieveUserInfo(){
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                if(ds.hasChild(username)){
                    ds = ds.child(username);
                    p.setName(ds.child("name").getValue().toString());
                    p.setEmail(ds.child("email").getValue().toString());
                    p.setTelephone(ds.child("telephone").getValue().toString());
                    p.setAddress(ds.child("address").getValue().toString());
                    p.setNameOfSchool(ds.child("name_school").getValue().toString());
                    p.setFathersName(ds.child("father_name").getValue().toString());
                    p.setFathersTelephone(ds.child("father_telephone").getValue().toString());
                    p.setMothersName(ds.child("mother_name").getValue().toString());
                    p.setMothersTelephone(ds.child("mother_telephone").getValue().toString());
                    fillForms();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void changeUserInfo(View view){
        p.setName(nameText.getText().toString());
        p.setAddress(addressText.getText().toString());
        p.setTelephone(telephoneText.getText().toString());
        p.setEmail(emailText.getText().toString());
        p.setFathersTelephone(fathersTelephoneText.getText().toString());
        p.setFathersName(fathersNameText.getText().toString());
        p.setMothersName(mothersNameText.getText().toString());
        p.setMothersTelephone(mothersTelephoneText.getText().toString());
        p.setNameOfSchool(schoolsNameText.getText().toString());
        updateUserInfo();
    }

    private void updateUserInfo(){
        DatabaseReference ref = myRef.child(username);
        ref.child("name").setValue(p.getName());
        ref.child("email").setValue(p.getEmail());
        ref.child("address").setValue(p.getAddress());
        ref.child("telephone").setValue(p.getTelephone());
        ref.child("father_name").setValue(p.getFathersName());
        ref.child("father_telephone").setValue(p.getFathersTelephone());
        ref.child("mother_name").setValue(p.getMothersName());
        ref.child("mother_telephone").setValue(p.getMothersTelephone());
        ref.child("scool_name").setValue(p.getNameOfSchool());
    }
}
