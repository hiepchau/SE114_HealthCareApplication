package com.example.se114_healthcareapplication.Services;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.example.se114_healthcareapplication.R;
import com.example.se114_healthcareapplication.presenter.StepsCountPresenter;


public class StepsCountServices extends Service implements SensorEventListener {

    private SensorManager sensorManager;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sensorManager = (SensorManager) getSystemService(getApplicationContext().SENSOR_SERVICE);
        Toast.makeText(getApplicationContext(),"Service created",Toast.LENGTH_SHORT);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        RegisterSensor();
        testSend();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(getApplicationContext(), "Service destroyed!", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Intent intent = new Intent("SEND_NEW_STEPS");
        intent.putExtra("steps",(int)event.values[0]);
        // You can also include some extra data.
        sendBroadcast(intent);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void RegisterSensor(){
        Sensor stepCounter = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if(stepCounter == null){
            Toast.makeText(getApplicationContext(),"No senesor found",Toast.LENGTH_SHORT).show();
        }
        else {
            sensorManager.registerListener(this,stepCounter, SensorManager.SENSOR_DELAY_UI);
        }
    }

    private void testSend(){
        Intent intent = new Intent("SEND_NEW_STEPS");
        intent.putExtra("steps",(int)100);
        // You can also include some extra data.
        sendBroadcast(intent);
    }

}
