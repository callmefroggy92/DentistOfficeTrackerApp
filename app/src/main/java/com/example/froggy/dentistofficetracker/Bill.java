package com.example.froggy.dentistofficetracker;

import java.util.Calendar;

/**
 * Created by froggy on 3/18/18.
 * This is a class designed to capture all the information contained in a dentist office bill.  It only
 * has a default, public constructor which sets the value of 'paid' to 'false'.  Since there is no
 * verification of data, there are no getters or setters.
 */

public class Bill {

    float amount;
    String patient_name;
    Calendar date;
    String details;
    Boolean paid;

    public Bill() { paid = false; }

}
