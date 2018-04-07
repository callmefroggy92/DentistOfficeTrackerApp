package com.example.froggy.dentistofficetracker;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * A broadcast receiver that receives alarm notifications from the TodoList activity.  The purpose
 * of these notifications are to notify the User that an appointment is upcoming.
 * @author Logan Holland
 * @since 4-7-2018
 */

public class MyBroadcastReceiver extends BroadcastReceiver {

    public static final String ANDROID_CHANNEL_NAME = "ANDROID CHANNEL";
    public static final String ANDROID_CHANNEL_ID = "com.example.froggy.dentistofficetracker";

    @Override
    public void onReceive(Context context, Intent intent){


        NotificationCompat.Builder builder =
                (new NotificationCompat.Builder(context, ANDROID_CHANNEL_ID))
                        .setSmallIcon(R.drawable.icon)
                        .setContentTitle("Dentist Tracker")
                        .setContentText("This is a reminder for your appointment!");
        // Add as notification
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel androidChannel = new NotificationChannel(ANDROID_CHANNEL_ID,
                    ANDROID_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(androidChannel);
        }

        manager.notify(0, builder.build());

    }


}
