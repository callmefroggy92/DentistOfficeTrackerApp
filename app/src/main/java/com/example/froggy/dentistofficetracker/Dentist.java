package com.example.froggy.dentistofficetracker;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Vector;
import com.google.gson.Gson;

/**
 * <p>A sub-class of User, designed to hold information for a Dentist object</p>
 *
 * @author Logan Holland
 */

public class Dentist extends User{

    private Vector<Patient> patients;

    private Appointment[] calendar;

    public Dentist(){
        patients = new Vector<Patient>();
        this.type = UserType.Dentist;
    }

    public void addPatient(Patient pt){
        // Needs validation that vector does not already contain patient
           patients.add(pt);
    }

    public void removePatient(Patient pt){
        // Needs created
    }

    public Appointment[] getCalendar() {
        return calendar;
    }

    public void setCalendar(Appointment[] calendar) {
        this.calendar = calendar;
    }
}
