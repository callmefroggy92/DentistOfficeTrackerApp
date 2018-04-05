package com.example.froggy.dentistofficetracker;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * This class is designed to create a new task and
 * store it in the database Firebase.
 */
public class AddTask extends AppCompatActivity {


    //Edit text to get the user input
    EditText editTask;
    EditText editTime;

    // Read and write data from firebase
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    String text_date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        database = FirebaseDatabase.getInstance();

        Intent intent = getIntent();
        text_date = intent.getStringExtra(TodoList.EXTRA_DATE);

        TextView textView_date = (TextView) findViewById(R.id.date_text_view);

        textView_date.setText(text_date);


    }


    public void addButtonClicked(View view){

        //Get the task from the user
        editTask = (EditText) findViewById(R.id.editText_task);
        editTime = (EditText) findViewById(R.id.editText_time);


        // Store the activity
        final String taskActivity = editTask.getText().toString();
        String timeActivity = editTime.getText().toString();

        // Establish the current data
       // long date = System.currentTimeMillis();
        //SimpleDateFormat sdf = new SimpleDateFormat("MMMM MM dd, yyyyy h:mm a");

        //Hold the date
       // String dateString = sdf.format(date);


        // Store in Firebase
        myRef = database.getInstance("https://calendar-dentist.firebaseio.com/").getReference().child(text_date);

        // Store the information inside of the mew created task
     //  DatabaseReference newSomething = myRef.push()
      // newSomething.child("task").setValue(taskActivity);

        DatabaseReference newOne = myRef.push();
        newOne.child("task").setValue(editTask.getText().toString());
        newOne.child("time").setValue(editTime.getText().toString());

        addNotification();

      //  myRef.child("task").push();
       // myRef.child("task").setValue(editTask.getText().toString() + " Time: " + editTime.getText().toString());
      //  myRef.child("time").push();
       // myRef.child("time").setValue(editTime.getText().toString());

    }

    private void addNotification(){

        Intent i = new Intent(getApplicationContext(), MyBroadcastReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, Calendar.getInstance().getTimeInMillis() + 5000, pi);
    }
}
