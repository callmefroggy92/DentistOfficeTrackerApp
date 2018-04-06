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

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TodoList extends AppCompatActivity {

   public static final String EXTRA_DATE = "com.example.froggy.dentistofficetracker.EXTRA_DATE";

    private RecyclerView mTaskList;

    private List<RequestTask> tasks;
    private Adapter adapter;
    private DateFormat df;
    private String date;
    private String appointmentDate;

    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);

        // Get the username of the previous activity.
        username = getIntent().getExtras().getString("username");

        // Set the recycle view in rows
        mTaskList = (RecyclerView) findViewById(R.id.add_task_list);
        mTaskList.setLayoutManager(new LinearLayoutManager(this));
        tasks = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://calendar-dentist.firebaseio.com/");

        // Create a new class Adapter.
        adapter = new Adapter(tasks);

        mTaskList.setAdapter(adapter);

        // Get the date of the previous activity.
        appointmentDate = getIntent().getExtras().get("date").toString();

        // Set the banners
        TextView textViewBannerDay = (TextView) findViewById(R.id.DayBanner);
        TextView textViewBannerDate = (TextView) findViewById(R.id.DateBanner);

        // Format the date
        SimpleDateFormat dfBanner = new SimpleDateFormat("EEEE");
        Date dateBanner = new Date();
        String dayOfWeek = dfBanner.format(dateBanner);
        textViewBannerDay.setText(dayOfWeek);

        long bannerDate = System.currentTimeMillis();
        SimpleDateFormat dfBannerDate = new SimpleDateFormat("MMM MM dd, yyy");
        String dateOfMonth = dfBannerDate.format(bannerDate);
        textViewBannerDate.setText(dateOfMonth  );

        df = new SimpleDateFormat("d\\M\\yyyy");
        date = df.format(Calendar.getInstance().getTime());
        Log.d("THE_CREATED_DATE", date);
        System.out.println(date);

        // Get the previous date.
      Intent intent = getIntent();
      date = intent.getStringExtra(CalendarApp.EXTRA_DATE);


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

       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_task);
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
}
