package com.example.froggy.dentistofficetracker;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * The Add_Patient activity handles creating patients and sending the information
 * to the database.
 *
 * @author Logan Holland
 * @since 4-7-2018
 */
public class Add_Patient extends AppCompatActivity {

    EditText patientName;
    EditText patientAddress;
    EditText patientNumber;
    EditText patientEmail;
    EditText patientAge;
    EditText patientUsername;
    EditText patientPassword;
    EditText patientsFathersName;
    EditText patientsFathersTelephone;
    EditText patientsMothersName;
    EditText patientsMothersTelephone;
    EditText patientsSchoolsName;
    EditText patientsSchoolsGrade;
    EditText patientsBirthday;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__patient);

        // Text fields for all values. . .
        patientName = findViewById(R.id.patient_name);
        patientAddress = findViewById(R.id.patient_address);
        patientNumber = findViewById(R.id.patient_number);
        patientEmail = findViewById(R.id.patient_email);
        patientAge = findViewById(R.id.patient_age);
        patientUsername = findViewById(R.id.patient_username);
        patientPassword = findViewById(R.id.patient_password);
        patientsFathersName = findViewById(R.id.patient_father);
        patientsFathersTelephone = findViewById(R.id.patients_fathers_telephone);
        patientsMothersName = findViewById(R.id.patients_mother);
        patientsMothersTelephone = findViewById(R.id.patients_mothers_telephone);
        patientsSchoolsName = findViewById(R.id.patient_school);
        patientsSchoolsGrade = findViewById(R.id.patient_grade);
        patientsBirthday = findViewById(R.id.patients_birth_date);

    }

    public void onSubmit(View view){

        // Verify textfields have been populated
        if(!patientName.getText().toString().isEmpty() && !patientAddress.getText().toString().isEmpty() && !patientNumber.getText().toString().isEmpty()
                && !patientEmail.getText().toString().isEmpty() && !patientAge.getText().toString().isEmpty() && !patientUsername.getText().toString().isEmpty()
                && !patientPassword.getText().toString().isEmpty()) {

            // Creates and fills patient object
            Patient p = new Patient();

            p.setAddress(patientAddress.getText().toString());
            p.setName(patientName.getText().toString());
            p.setTelephone(patientNumber.getText().toString());
            p.setAge(Integer.valueOf(patientAge.getText().toString()));
            p.setUsername(patientUsername.getText().toString());
            p.setPassword(patientPassword.getText().toString());
            p.setFathersName(patientsFathersName.getText().toString());
            p.setFathersTelephone(patientsFathersTelephone.getText().toString());
            p.setMothersName(patientsMothersName.getText().toString());
            p.setMothersTelephone(patientsMothersTelephone.getText().toString());
            p.setNameOfSchool(patientsSchoolsName.getText().toString());
            p.setGradeInSchool(patientsSchoolsGrade.getText().toString());
            p.setEmail(patientEmail.getText().toString());

            try {
                // Create the new user's password
                LoginController.hashUserPassword(p);
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                return;
            }

            // Creates Firebase instance and creates a new child for the patient to be added
            firebaseDatabase = FirebaseDatabase.getInstance();
            myRef = firebaseDatabase.getReference().child(patientUsername.getText().toString());

            // Only final objects can be used in the EventListener...
            final Patient u = p;

            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // Adds all values to the Firebase
                    myRef.child("username").push();
                    myRef.child("username").setValue(u.getUsername());
                    myRef.child("hash").push();
                    myRef.child("hash").setValue(u.getHash());
                    myRef.child("address").push();
                    myRef.child("address").setValue(u.getAddress());
                    myRef.child("telephone").push();
                    myRef.child("telephone").setValue(u.getTelephone());
                    myRef.child("age").push();
                    myRef.child("age").setValue(u.getAge());
                    myRef.child("key").push();
                    myRef.child("key").setValue(u.getKey());
                    myRef.child("name").push();
                    myRef.child("name").setValue(u.getName());
                    myRef.child("father_name").push();
                    myRef.child("father_name").setValue(u.getFathersName());
                    myRef.child("father_telephone").push();
                    myRef.child("father_telephone").setValue(u.getFathersTelephone());
                    myRef.child("mother_name").push();
                    myRef.child("mother_name").setValue(u.getMothersName());
                    myRef.child("mother_telephone").push();
                    myRef.child("mother_telephone").setValue(u.getMothersTelephone());
                    myRef.child("school_name").push();
                    myRef.child("school_name").setValue(u.getNameOfSchool());
                    myRef.child("school_grade").push();
                    myRef.child("school_grade").setValue(u.getGradeInSchool());
                    myRef.child("email").push();
                    myRef.child("email").setValue(u.getEmail());
                    myRef.child("salt").push();
                    myRef.child("salt").setValue(u.getSalt());
                    myRef.child("type").push();
                    myRef.child("type").setValue(UserType.Patient);
                    Toast.makeText(getApplicationContext(), "Patient succesfuly added!", Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        // If a field hasn't been populated. . .
        else
            Toast.makeText(getApplicationContext(), "Please fill in all fields!", Toast.LENGTH_SHORT).show();
    }
}
