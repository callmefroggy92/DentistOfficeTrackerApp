package com.example.froggy.dentistofficetracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;

/**
 * This class is designed to create a new task and
 * store it in the database Firebase.
 */
public class AddTask extends AppCompatActivity {


    //Edit text to get the user input
    EditText editTask;

    // Read and write data from firebase
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        database = FirebaseDatabase.getInstance();
    }


    public void addButtonClicked(View view){

        //Get the task from the user
        editTask = (EditText) findViewById(R.id.editText_task);

        // Store the activity
        String taskActivity = editTask.getText().toString();

        // Establish the current data
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM MM dd, yyyyy h:mm a");

        //Hold the date
        String dateString = sdf.format(date);


        // Store in Firebase
        myRef = database.getInstance().getReference().child("New Task");

        // Store the information inside of the mew created task
        DatabaseReference newTask = myRef.push();

        newTask.child("Activity Task").setValue(taskActivity);
        newTask.child("Time ").setValue(dateString);


    }
}
