package com.example.se114_healthcareapplication.Recievers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import com.example.se114_healthcareapplication.AlarmActivity;

public class AlarmReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"Recieved alarm",Toast.LENGTH_SHORT).show();
        if(intent.getAction().equals("CUSTOM_ALARM")) {
            Intent alarmIntent = new Intent();
            alarmIntent.setClass(context, AlarmActivity.class);
            alarmIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            alarmIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            alarmIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(alarmIntent);
        }

    }
}
