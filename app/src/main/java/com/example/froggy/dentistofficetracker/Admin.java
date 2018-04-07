package com.example.froggy.dentistofficetracker;

import com.google.gson.Gson;

import java.io.FileWriter;
import java.util.Scanner;

/**
 * A sub-class of User, designed to be the administrator of the application.
 *
 * @author Logan Holland
 */
public class Admin extends User{

    private String data;

    public Admin(){ type = UserType.Admin;}

    public void decryptPatient(Patient p){
        try {
            EncryptionMachine eMachine = new EncryptionMachine(this.decKey);
            String aKey = eMachine.decrypt(p.getAdminKey());
            eMachine = new EncryptionMachine(aKey);
            p.decrypt(aKey);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
