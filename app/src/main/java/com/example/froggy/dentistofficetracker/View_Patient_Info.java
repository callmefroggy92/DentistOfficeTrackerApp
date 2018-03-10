package com.example.froggy.dentistofficetracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.google.gson.Gson;

public class View_Patient_Info extends AppCompatActivity {

    Bundle extras;
    private Patient p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__patient__info);

        extras = getIntent().getExtras();

        Gson gson = new Gson();

        p = gson.fromJson(extras.getString("patient"), Patient.class);

        {
            TextView patient_name = findViewById(R.id.view_patient_name);
            patient_name.setText("Patient name" + p.getName());
            TextView patient_address = findViewById(R.id.view_patient_address);
            patient_address.setText("Patient address: " + p.getAddress());
            TextView patient_email = findViewById(R.id.view_patient_email);
            patient_email.setText("Patient email: " + p.getEmail());
            TextView patient_telephone = findViewById(R.id.view_patient_telephone);
            patient_telephone.setText("Patient telephone: " + p.getTelephone());
            TextView patient_age = findViewById(R.id.view_patient_age);
            patient_age.setText("Patient age: " + p.getAge());
            TextView patients_fathers_name = findViewById(R.id.view_patient_fathers_name);
            patients_fathers_name.setText("Fathers name: " + p.getFathersName());
            TextView patients_fathers_telephone = findViewById(R.id.view_patients_fathers_telephone);
            patients_fathers_telephone.setText("Fathers telephone number: " + p.getFathersTelephone());
            TextView patients_mothers_name = findViewById(R.id.view_patients_mothers_name);
            patients_mothers_name.setText("Mothers name: " + p.getMothersName());
            TextView patients_mothers_telephone = findViewById(R.id.view_patients_mothers_telephone);
            patients_mothers_telephone.setText("Mothers telephone number: " + p.getMothersTelephone());
            TextView patients_schools_name = findViewById(R.id.view_patients_school_name);
            patients_schools_name.setText("Name of patients school: " + p.getNameOfSchool());
            TextView patients_school_grade = findViewById(R.id.view_patients_school_grade);
            patients_school_grade.setText("Patients grade in school: " + p.getGradeInSchool());
        }
    }
}
