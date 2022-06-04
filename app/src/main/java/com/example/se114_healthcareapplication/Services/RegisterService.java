package com.example.se114_healthcareapplication.Services;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.widget.Toast;
import androidx.annotation.Nullable;
import com.example.se114_healthcareapplication.Recievers.AlarmReciever;

public class RegisterService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        registerReceiver(new AlarmReciever(),new IntentFilter("CUSTOM_ALARM"));
        Toast.makeText(getApplicationContext(),"Registed alarm", Toast.LENGTH_SHORT).show();
        return START_STICKY;
    }
}
