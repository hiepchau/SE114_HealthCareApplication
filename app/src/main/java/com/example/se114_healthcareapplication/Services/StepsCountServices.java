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
import com.example.se114_healthcareapplication.presenter.StepsCountPresenter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Calendar;


public class StepsCountServices extends Service implements SensorEventListener {

    class DayChangedReciver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(Intent.ACTION_DATE_CHANGED))
            {
                Toast.makeText(getApplicationContext(),"Day changed!",Toast.LENGTH_SHORT).show();
                getCurrentSteps();
            }
        }
    }

    private SensorManager sensorManager;
    private int currentSteps;
    private FirebaseAuth auth;
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
        getCurrentSteps();
        testSend();
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
        Intent intent = new Intent("SEND_NEW_STEPS");
        currentSteps ++;
        intent.putExtra("steps",currentSteps);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(auth.getCurrentUser().getUid())
                    .child(format.format(LocalDateTime.now())).child("CurrentSteps");
            ref.setValue(currentSteps);
        }

        // You can also include some extra data.
        sendBroadcast(intent);


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

    private void getCurrentSteps(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(auth.getCurrentUser().getUid())
                    .child(format.format(LocalDateTime.now())).child("CurrentSteps");

            ValueEventListener listener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    if(snapshot.getValue(int.class)==null){
                        ref.setValue(0);
                        currentSteps = 0;
                        Toast.makeText(getApplicationContext(),String.valueOf(currentSteps),Toast.LENGTH_SHORT).show();
                    }
                    else {
                        currentSteps = snapshot.getValue(int.class);
                    }
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            };
            ref.addValueEventListener(listener);
            ref.removeEventListener(listener);
        }

    }

    private void testSend(){
        Intent intent = new Intent("SEND_NEW_STEPS");
        intent.putExtra("steps",(int)100);
        // You can also include some extra data.
        sendBroadcast(intent);
    }
}
