package com.example.froggy.dentistofficetracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class TodoList extends AppCompatActivity {

   public static final String EXTRA_DATE = "com.example.froggy.dentistofficetracker.EXTRA_DATE";

    private RecyclerView mTaskList;

    private List<RequestTask> tasks;
    private Adapter adapter;
    private DateFormat df;
    private String date;
    private String appointmentDate;

    private Calendar chosenDate;

    private String username;

    private FirebaseDatabase database;

    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);

        // Get the username of the previous activity.
        username = getIntent().getExtras().getString("username");
        database = FirebaseDatabase.getInstance("https://calendar-dentist.firebaseio.com/");


        // Get the date of the previous activity.
        appointmentDate = getIntent().getExtras().get("date").toString();
        df = new SimpleDateFormat("d\\M\\yyyy");
        date = getIntent().getStringExtra(CalendarApp.EXTRA_DATE);

        fab = (FloatingActionButton) findViewById(R.id.add_task);


        setAdapter();
        setBanners();
        createList();

        // As long as the usertype is NOT patient, he or she may add a new appointment
        if(getIntent().getExtras().getString("type", "").compareToIgnoreCase("patient") != 0)
            createFloatingButton();
        else
            fab.setVisibility(View.GONE);

    }

    private void setAdapter(){

        // Set the recycle view in rows
        mTaskList = (RecyclerView) findViewById(R.id.add_task_list);
        mTaskList.setLayoutManager(new LinearLayoutManager(this));
        tasks = new ArrayList<>();

        // Create a new class Adapter.
        adapter = new Adapter(tasks);

        mTaskList.setAdapter(adapter);
    }

    // Pulls all tasks from the database for the current user
    private void createList(){

        // Find the appointment of the week, under the username and under the date.
        database.getReference().child(username).child(date).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tasks.removeAll(tasks);
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){

                    // Find the tasks and the time
                    String taskF = snapshot.child("task").getValue(String.class);
                    String time = snapshot.child("time").getValue(String.class);
                    RequestTask task = new RequestTask(time, taskF);
                    tasks.add(task);
                    Log.d("THE CREATED DATE", date);

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    // Creates the floating button which will add a new task
    private void createFloatingButton(){

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "You are going to add a new task!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                Intent intent = new Intent(getApplicationContext(), AddTask.class);
                intent.putExtra(EXTRA_DATE, date);
                intent.putExtra("date", appointmentDate);
                intent.putExtra("username", username);
                startActivity(intent);

            }


        });
    }
    // Creates the banner showing the day and date
    private void setBanners(){

        Gson gson = new Gson();

        // Retrieves the chosen date from the previous activity
        chosenDate = gson.fromJson(getIntent().getExtras().getString("date"), GregorianCalendar.class);

        // Set the banners
        TextView textViewBannerDay = (TextView) findViewById(R.id.DayBanner);
        TextView textViewBannerDate = (TextView) findViewById(R.id.DateBanner);

        // Format and display the day of week
        SimpleDateFormat dfBanner = new SimpleDateFormat("EEEE");
        String dayOfWeek = dfBanner.format(chosenDate.getTime());
        textViewBannerDay.setText(dayOfWeek);

        // Format and display the date
        SimpleDateFormat dfBannerDate = new SimpleDateFormat("MMM dd, yyy");
        String dateOfMonth = dfBannerDate.format(chosenDate.getTime());
        textViewBannerDate.setText(dateOfMonth  );
    }
}
