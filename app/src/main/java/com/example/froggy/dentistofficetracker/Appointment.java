package com.example.froggy.dentistofficetracker;

import java.util.Calendar;

/**
 * Created by froggy on 3/12/18.
 */

public class Appointment {

    private Calendar date;
    private String patientName;
    private String dentistName;
    private String notes;

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public String getDentistName() {
        return dentistName;
    }

    public void setDentistName(String dentistName) {
        this.dentistName = dentistName;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
