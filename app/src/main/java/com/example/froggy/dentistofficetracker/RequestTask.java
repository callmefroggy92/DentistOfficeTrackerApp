package com.example.froggy.dentistofficetracker;

public class RequestTask {

    private String time, task;


    public RequestTask()
    {

    }

    public RequestTask(String time, String task)
    {

        this.task = task;
        this.time = time;
    }

    public void setTime(String time) {
        this.time = time;
    }



    public void setTask(String task) {
        this.task = task;
    }

    public String getTime() {
        return time;
    }


    public String getTask() {
        return task;
    }

}
