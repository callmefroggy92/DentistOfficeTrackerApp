package com.example.froggy.dentistofficetracker;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddTask extends AppCompatActivity {

    // Store the username
    private String username;

    //Edit text to get the user input
    private EditText editTask;
    private EditText editTime;

    // Read and write data from firebase
    private FirebaseDatabase database;
    private DatabaseReference myRef;


    // Store the date.
    private String text_date;
    private Calendar c;

    private Gson gson;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        database = FirebaseDatabase.getInstance();

        // Get the date of the previous activity.
        Intent intent = getIntent();
        text_date = intent.getStringExtra(TodoList.EXTRA_DATE);

        // Set the date of the previous activity on the screen
        TextView textView_date = (TextView) findViewById(R.id.date_text_view);
        textView_date.setText(text_date);

        gson = new Gson();
        c = gson.fromJson(getIntent().getExtras().getString("date").toString(), GregorianCalendar.class);

        // Retrieve the username of the previous activity.
        username = getIntent().getExtras().getString("username");

    }


    //A simple method to verify that the time has been entered in HH:MM format
    private boolean timeIsCorrect(){
        editTime = findViewById(R.id.editText_time);
        String s = editTime.getText().toString();
        Pattern p = Pattern.compile("[0-9][0-9][:][0-9][0-9]");
        Matcher m = p.matcher(s);
        if(m.find())
            return true;
        return false;
    }

    /**
     * This method is called when the ADD button is clicked.  It submits the new
     * task to the database.
     * @param view
     */
    public void addButtonClicked(View view){

        //Get the task from the user
        editTask = (EditText) findViewById(R.id.editText_task);
        editTime = (EditText) findViewById(R.id.editText_time);

        // The time has to be in the correct format. . .
        if(!timeIsCorrect()){
            Toast.makeText(getApplicationContext(), "Make sure time entered matches HH:MM, example: 09:30", Toast.LENGTH_SHORT).show();
            return;
        }

        // Store the activity
        final String taskActivity = editTask.getText().toString();
        String timeActivity = editTime.getText().toString();

        // Adds the time to the date
        c.set(Calendar.HOUR_OF_DAY, new Integer(timeActivity.split(":")[0]));
        c.set(Calendar.MINUTE, new Integer(timeActivity.split(":")[1]));

        submit();

        addNotification();

        Toast.makeText(getApplicationContext(), "Appointment succesfully added!", Toast.LENGTH_SHORT).show();
        finish();
    }

    // Store in Firebase
    private void submit(){
        myRef = database.getInstance("https://calendar-dentist.firebaseio.com/").getReference().child(username).child(text_date);

        DatabaseReference newOne = myRef.push();
        newOne.child("task").setValue(editTask.getText().toString());
        newOne.child("time").setValue(editTime.getText().toString());
    }

    // This private method adds a notification and alarm to reminder the user of the new appointment
    private void addNotification() {

        SharedPreferences prefs = getSharedPreferences("settings", Context.MODE_PRIVATE);

        // The user has to have notifications turned on. . .
        if (prefs.contains("notications") && prefs.getString("notications", "").compareToIgnoreCase("true") == 0) {

            // MyBroadcastReceiver.class should be called in order to display the notification
            Intent i = new Intent(getApplicationContext(), MyBroadcastReceiver.class);
            PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

            // If the user hasn't set a time for the reminder, the default value should be 15 minutes
            int reminder = prefs.getInt("reminder", 15000);

            // The characteristics of alarmManager depends on the API level.  Thus, we have two
            // different options of how to handle the alarmManager. . .
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis() - reminder, pi);
            else
                alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis() - reminder, pi);
        }
    }
}
