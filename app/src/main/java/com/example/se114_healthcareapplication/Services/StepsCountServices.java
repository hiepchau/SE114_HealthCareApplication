package com.example.se114_healthcareapplication.Services;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.example.se114_healthcareapplication.R;
import com.example.se114_healthcareapplication.generalinterfaces.IPresenter;
import com.example.se114_healthcareapplication.model.StatisticModel;
import com.example.se114_healthcareapplication.presenter.StepsCountPresenter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Calendar;


public class StepsCountServices extends Service implements SensorEventListener, IPresenter {

    @Override
    public void NotifyPresenter(int code) {
        if(code==STEPS_COUNT_UPDATED){
            currentSteps = statisticModel.getCurrentSteps();
        }
    }

    class DayChangedReciver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(Intent.ACTION_DATE_CHANGED))
            {
                statisticModel.registerListenerForSteps();
            }
        }
    }

    private SensorManager sensorManager;
    private int currentSteps;
    private FirebaseAuth auth;
    private StatisticModel statisticModel;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sensorManager = (SensorManager) getSystemService(getApplicationContext().SENSOR_SERVICE);
        auth = FirebaseAuth.getInstance();
        Toast.makeText(getApplicationContext(),"Service created",Toast.LENGTH_SHORT);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        RegisterSensor();
        statisticModel = new StatisticModel(this);
        statisticModel.registerListenerForSteps();
        DayChangedReciver reciver = new DayChangedReciver();
        getApplicationContext().registerReceiver(reciver,new IntentFilter(Intent.ACTION_DATE_CHANGED));
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(getApplicationContext(), "Service destroyed!", Toast.LENGTH_SHORT).show();
        sensorManager.unregisterListener(this);
        super.onDestroy();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        currentSteps ++;
        statisticModel.UpdateSteps(currentSteps);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void RegisterSensor(){
        Sensor stepCounter = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        if(stepCounter == null){
            Toast.makeText(getApplicationContext(),"No senesor found",Toast.LENGTH_SHORT).show();
        }
        else {
            sensorManager.registerListener(this,stepCounter, SensorManager.SENSOR_DELAY_UI);
        }
    }
}
