package com.example.froggy.dentistofficetracker;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

public class ToothInfo extends Activity {// this change lets the requestWindowFeature(Window.FEATURE_NO_TITLE) to work
    //public class ToothInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // this removes the title
        setContentView(R.layout.activity_tooth_info);
    }




}




