package com.example.froggy.dentistofficetracker;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.TaskViewHolder>{


  List<RequestTask> tasks;

  public Adapter(List<RequestTask> tasks){
    this.tasks = tasks;
  }


  @Override
  public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_list_row,parent,false);
    TaskViewHolder holder = new TaskViewHolder(view);
    return holder;
  }

  @Override
  public void onBindViewHolder(TaskViewHolder holder, int position) {
    RequestTask task = tasks.get(position);

//        holder.textViewDate.setText(task.getDate());
    holder.textViewTask.setText(task.getTask());
    holder.textViewTime.setText(task.getTime());
  }

  @Override
  public int getItemCount() {
    return tasks.size();
  }

  public static class TaskViewHolder extends RecyclerView.ViewHolder{

    TextView textViewTask, textViewTime, textViewDate;

    public TaskViewHolder(View itemView) {
      super(itemView);
      textViewTask = (TextView) itemView.findViewById(R.id.task_name);
      textViewTime = (TextView) itemView.findViewById(R.id.time_selected);
      textViewDate = (TextView) itemView.findViewById(R.id.date_text_view);

    }

  }
}