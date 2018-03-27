package com.example.froggy.dentistofficetracker;

import android.app.DatePickerDialog;
import android.opengl.EGLExt;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;

import java.util.Calendar;

/**
 * This class is designed to generate a calendar and
 * display it to the user. It also contains and implementation
 * to pop up the date selected by the user.
 */
public class CalendarApp extends AppCompatActivity {

    CalendarView calendar;
    private DatePickerDialog.OnDateSetListener mDateSetListerner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_app);

        calendar = (CalendarView) findViewById(R.id.calendar1);

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                // Just for testing purposes.
                Toast.makeText(getBaseContext(), "You have selected" + dayOfMonth + "/" + month + "/" + year, Toast.LENGTH_LONG).show();
                final Calendar c = Calendar.getInstance();
                int yearC = c.get(Calendar.YEAR);
                int monthC = c.get(Calendar.MONTH);
                int dayC = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(CalendarApp.this, android.R.style.Theme_Holo_Light_Dialog, mDateSetListerner, year, month, dayOfMonth);

                dialog.show();


                // Create a new instance of DatePickerDialog and return it

            }
        });

      /*  calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int yearC = c.get(Calendar.YEAR);
                int monthC = c.get(Calendar.MONTH);
                int dayC = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(CalendarApp.this, android.R.style.Theme_Holo_Light_Dialog, mDateSetListerner, yearC, monthC, dayC);

                dialog.show();

            }
        });*/




    }
}
