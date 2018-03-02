package com.example.froggy.dentistofficetracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class activity_d_add_appointment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d_add_appointment);
    }

    public void addAppointment(View view){

        Appointment app = new Appointment();
        EditText patient_name = findViewById(R.id.patient_name);
        EditText appointment_date = findViewById(R.id.appointment_date);
        EditText appointment_time = findViewById(R.id.appointment_time);
        EditText appointment_notes = findViewById(R.id.appointment_notes);

        app.setNotes("Patient name: " + patient_name.getText().toString() + " Notes: " + appointment_notes.getText().toString());


    }
}
