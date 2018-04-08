package com.example.froggy.dentistofficetracker;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.opengl.EGLExt;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * <p>This class is designed to generate a calendar and
 * display it to the user. It also contains and implementation
 * to pop up the date selected by the user.</p>
 */
public class CalendarApp extends AppCompatActivity {

    public static final String EXTRA_DATE = "com.example.froggy.dentistofficetracker.EXTRA_DATE";

    // Create the Calendar widget
    private CalendarView calendar;
    private DatePickerDialog.OnDateSetListener mDateSetListerner;
    private Calendar c;
    private Gson gson;
    private String username;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_app);

        // Get the username of the current person
        username = getIntent().getExtras().getString("username", "");
        type = getIntent().getExtras().getString("type", "");

        // Find the interface to display the calendar
        calendar = (CalendarView) findViewById(R.id.calendar1);

        c = new GregorianCalendar();
        gson = new Gson();

        createCalendar();

    }

    // Creates the interactive calendar
    private void createCalendar(){


        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, final int year, final int month, final int dayOfMonth) {

                // Set the correct month
                Toast.makeText(getBaseContext(), "You have selected: " + dayOfMonth + "/" + (month + 1) + "/" + year, Toast.LENGTH_LONG).show();
                c.set(year, month, dayOfMonth);

                // Create a DatePicker dialog widget
                DatePickerDialog dialog = new DatePickerDialog(CalendarApp.this, android.R.style.Theme_Holo_Light_Dialog, mDateSetListerner, year, month, dayOfMonth);

                // Display the DatePicker
                dialog.show();

                // Find the button to execute the next activity. The TODO LIST.
                Button button1 = (Button) dialog.getButton(dialog.BUTTON_POSITIVE);

                button1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // Open the next activity
                        Intent intent = new Intent(getApplicationContext(), TodoList.class);

                        // Store the day, month and year.
                        String dayS = Integer.toString(dayOfMonth);
                        String monthS = Integer.toString(month + 1);
                        String yearS = Integer.toString(year);
                        String date = dayS  + "\\" +  monthS + "\\" + yearS;

                        c = new GregorianCalendar(year, month, dayOfMonth);

                        // Pass some values to the next activity.
                        intent.putExtra(EXTRA_DATE, date);
                        intent.putExtra("date", gson.toJson(c));
                        intent.putExtra("username", username);
                        intent.putExtra("type", type);
                        startActivity(intent);
                    }
                });

            }
        });

    }
}
