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
    /*
    private RecyclerView mTaskList;
    private DatabaseReference mDataBase;
    private FirebaseRecyclerOptions<RequestTask>  options;
    private FirebaseRecyclerAdapter<RequestTask,TaskViewHolder> FBRA;
    Query query;*/


   RecyclerView mTaskList;

    List<RequestTask> tasks;

    Adapter adapter;

    DateFormat df;
    String date;
 //   TextView textViewDate = (TextView) findViewById(R.id.date_text_view);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
 //       setSupportActionBar(toolbar);

        mTaskList = (RecyclerView) findViewById(R.id.add_task_list);
        mTaskList.setLayoutManager(new LinearLayoutManager(this));
        tasks = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://calendar-dentist.firebaseio.com/");

        adapter = new Adapter(tasks);

        mTaskList.setAdapter(adapter);


        // Set the banners
        TextView textViewBannerDay = (TextView) findViewById(R.id.DayBanner);
        TextView textViewBannerDate = (TextView) findViewById(R.id.DateBanner);

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

      Intent intent = getIntent();
      date = intent.getStringExtra(CalendarApp.EXTRA_DATE);

        database.getReference().child(date).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tasks.removeAll(tasks);
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){


                    String taskF = snapshot.child("task").getValue(String.class);
                    String time = snapshot.child("time").getValue(String.class);//snapshot.getValue(RequestTask.class);
                    RequestTask task = new RequestTask(time, taskF);
                   // task.setTask(taskF);
                    //task.setTask(time);
                    tasks.add(task);
                    Log.d("THE CREATED DATE", date);


                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


       /* mTaskList = (RecyclerView) findViewById(R.id.add_task_list);
      //  mTaskList.setHasFixedSize(true);
        mTaskList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRef = FirebaseDatabase.getInstance().getReference().child("New Task");*/


       /* mTaskList = (RecyclerView) findViewById(R.id.add_task_list);
        mTaskList.setHasFixedSize(true);
        mTaskList.setLayoutManager(new LinearLayoutManager(this));
        mDataBase = FirebaseDatabase.getInstance().getReference().child("New Task");
        query = mDataBase.orderByKey();

        options = new FirebaseRecyclerOptions.Builder<RequestTask>().setQuery(query, RequestTask.class).build();*/

       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_task);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "You are going to add a new task!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                Intent intent = new Intent(getApplicationContext(), AddTask.class);
                intent.putExtra(EXTRA_DATE, date);
                startActivity(intent);

            }


        });
    }
/*
    public static class TaskViewHolder extends RecyclerView.ViewHolder{


        public TaskViewHolder(View itemView) {
            super(itemView);
            View mView = itemView;
        }

        public void setName(String name){
            TextView task_name = (TextView) itemView.findViewById(R.id.task_name);
            task_name.setText(name);

        }

        public void setTime(String time){
            TextView time_selected = (TextView) itemView.findViewById(R.id.time_selected);
            time_selected.setText(time);

        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<RequestTask, TaskViewHolder> FBRA = new FirebaseRecyclerAdapter<RequestTask, TaskViewHolder>(RequestTask.class, R.layout.task_list_row, TaskViewHolder.class, mRef) {
            @Override
            protected void populateViewHolder(TaskViewHolder viewHolder, RequestTask model, int position) {
                viewHolder.setName(model.getName());
                viewHolder.setTime(model.getTime());
                Toast.makeText(getApplicationContext(), "THIS IS POPULATE",Toast.LENGTH_LONG).show();
            }
        };


        Toast.makeText(this,"This is OnStart",Toast.LENGTH_LONG).show();
        mTaskList.setAdapter(FBRA);*/
        /*FBRA = new FirebaseRecyclerAdapter<RequestTask, TaskViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull TaskViewHolder holder, int position, @NonNull RequestTask model) {
                holder.setName(model.getName());
                holder.setTime(model.getTime());
            }

            @Override
            public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_list_row, parent, false);
                return new TaskViewHolder(view);
            }
        };
        mTaskList.setAdapter(FBRA);*/
        //FBRA.startListening();
   // }
}
