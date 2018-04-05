package com.example.froggy.dentistofficetracker;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
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

    private Calendar c;

    Gson gson;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        database = FirebaseDatabase.getInstance();

        Intent intent = getIntent();
        text_date = intent.getStringExtra(TodoList.EXTRA_DATE);

        TextView textView_date = (TextView) findViewById(R.id.date_text_view);

        textView_date.setText(text_date);

        gson = new Gson();

        c = gson.fromJson(getIntent().getExtras().getString("date").toString(), GregorianCalendar.class);

    }

    private boolean timeIsCorrect(){
        editTime = findViewById(R.id.editText_time);
        String s = editTime.getText().toString();
        Pattern p = Pattern.compile("[0-9][0-9][:][0-9][0-9]");
        Matcher m = p.matcher(s);
        if(m.find())
            return true;
        return false;
    }

    public void addButtonClicked(View view){

        //Get the task from the user
        editTask = (EditText) findViewById(R.id.editText_task);
        editTime = (EditText) findViewById(R.id.editText_time);

        if(!timeIsCorrect()){
            Toast.makeText(getApplicationContext(), "Make sure time entered matches HH:MM, example: 09:30", Toast.LENGTH_SHORT).show();
            return;
        }

        // Store the activity
        final String taskActivity = editTask.getText().toString();
        String timeActivity = editTime.getText().toString();

        c.set(Calendar.HOUR_OF_DAY, new Integer(timeActivity.split(":")[0]));
        c.set(Calendar.MINUTE, new Integer(timeActivity.split(":")[1]));

        // Store in Firebase
        myRef = database.getInstance("https://calendar-dentist.firebaseio.com/").getReference().child(text_date);

        DatabaseReference newOne = myRef.push();
        newOne.child("task").setValue(editTask.getText().toString());
        newOne.child("time").setValue(editTime.getText().toString());

        addNotification();

    }

    private void addNotification(){

        Intent i = new Intent(getApplicationContext(), MyBroadcastReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis() - 15000, pi);
        else
            alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis() - 15000, pi);
    }
}
