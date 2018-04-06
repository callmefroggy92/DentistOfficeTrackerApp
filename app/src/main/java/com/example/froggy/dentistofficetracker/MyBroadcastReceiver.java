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
 * Created by froggy on 4/5/18.
 */

public class MyBroadcastReceiver extends BroadcastReceiver {

    public static final String ANDROID_CHANNEL_NAME = "ANDROID CHANNEL";
    public static final String ANDROID_CHANNEL_ID = "com.example.froggy.dentistofficetracker";

    @Override
    public void onReceive(Context context, Intent intent){


        Calendar c = new GregorianCalendar(2018,4,5,6,40);

        NotificationCompat.Builder builder =
                (new NotificationCompat.Builder(context, ANDROID_CHANNEL_ID))
                        .setSmallIcon(R.drawable.icon)
                        .setContentTitle("Dentist Tracker")
                        .setContentText("This is a reminder for your appointment!")
                        .setWhen(c.getTimeInMillis());

        // Add as notification
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel androidChannel = new NotificationChannel(ANDROID_CHANNEL_ID,
                    ANDROID_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(androidChannel);
        }

        manager.notify(0, builder.build());
        Toast.makeText(context, "Your appointment reminder!", Toast.LENGTH_LONG).show();

    }


}
