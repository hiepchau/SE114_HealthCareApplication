package com.example.se114_healthcareapplication;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.firebase.auth.FirebaseAuth;
import org.w3c.dom.Text;


public class HomeActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    TextView textView1;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_home);
//
//        Button btn = findViewById(R.id.button);
//        TextView textView = findViewById(R.id.textView);
//        textView1 = findViewById(R.id.textView2);
//        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FirebaseAuth.getInstance().signOut();
//                startActivity(new Intent(getApplicationContext(),AuthenticateActivity.class));
//            }
//        });
//
//        textView.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
//    }

    @Override
    protected void onResume() {
        super.onResume();
        Sensor stepCounter = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if(stepCounter == null){
            Toast.makeText(this,"No senesor found",Toast.LENGTH_SHORT).show();
        }
        else {
            sensorManager.registerListener(this,stepCounter,SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        int count = (int) sensorEvent.values[0];
        textView1.setText(count);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}