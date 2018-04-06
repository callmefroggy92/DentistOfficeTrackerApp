package com.example.froggy.dentistofficetracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ToggleButton;

public class Settings extends AppCompatActivity {

    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    ToggleButton notificationSetting;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        notificationSetting = findViewById(R.id.NotificationsSettingButton);

        prefs = getSharedPreferences("settings", Context.MODE_PRIVATE);
        editor = prefs.edit();

        username = getIntent().getExtras().getString("username");

        loadSettings();
    }

    public void loadSettings(){

        if(prefs.getString("notifications", "").compareToIgnoreCase("true") == 0)
            notificationSetting.setChecked(true);
    }

    public void onNotificationsSettings(View view){

        if(notificationSetting.isChecked() == true)
            editor.putString("notifications", "true");
        else
            editor.putString("notifications", "false");
        editor.apply();

    }

    public void onChangePassword(View view){
        Intent i = new Intent(getApplicationContext(), ChangePassword.class);
        i.putExtra("username", username);
        startActivity(i);
    }

    public void onSetReminderTime(View view){
        EditText editText = findViewById(R.id.reminderTimeText);
        editor.putInt("reminder", Integer.getInteger(editText.getText().toString()));
    }
}
