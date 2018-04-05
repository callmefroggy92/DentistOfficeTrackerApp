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
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * This class is designed to generate a calendar and
 * display it to the user. It also contains and implementation
 * to pop up the date selected by the user.
 */
public class CalendarApp extends AppCompatActivity {

    public static final String EXTRA_DATE = "com.example.froggy.dentistofficetracker.EXTRA_DATE";
    CalendarView calendar;
    private DatePickerDialog.OnDateSetListener mDateSetListerner;
    Calendar c;

    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_app);

        calendar = (CalendarView) findViewById(R.id.calendar1);

        c = new GregorianCalendar();
        gson = new Gson();

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, final int year, final int month, final int dayOfMonth) {

                // Set the correct month

                Toast.makeText(getBaseContext(), "You have selected" + dayOfMonth + "/" + (month + 1) + "/" + year, Toast.LENGTH_LONG).show();

                c.set(year, month, dayOfMonth);

                DatePickerDialog dialog = new DatePickerDialog(CalendarApp.this, android.R.style.Theme_Holo_Light_Dialog, mDateSetListerner, year, month, dayOfMonth);

                dialog.show();

                Button button1 = (Button) dialog.getButton(dialog.BUTTON_POSITIVE);

                button1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), TodoList.class);
                        String dayS = Integer.toString(dayOfMonth);
                        String monthS = Integer.toString(month + 1);
                        String yearS = Integer.toString(year);
                        String date = dayS  + "\\" +  monthS + "\\" + yearS;
                        intent.putExtra(EXTRA_DATE,date);
                        intent.putExtra("date", gson.toJson(c));
                        startActivity(intent);
                    }
                });

            }
        });

    }
}
