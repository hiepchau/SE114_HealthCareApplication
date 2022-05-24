package com.example.se114_healthcareapplication;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.example.se114_healthcareapplication.Recievers.AlarmReciever;
import com.example.se114_healthcareapplication.Services.RegisterService;
import com.example.se114_healthcareapplication.Services.StepsCountServices;
import com.example.se114_healthcareapplication.generalinterfaces.IPresenter;
import com.example.se114_healthcareapplication.generalinterfaces.IView;
import com.example.se114_healthcareapplication.presenter.AlarmPresenter;
import com.example.se114_healthcareapplication.presenter.HomePresenter;
import com.example.se114_healthcareapplication.presenter.StepsCountPresenter;
import com.google.firebase.auth.FirebaseAuth;
import org.w3c.dom.Text;

import java.util.Calendar;


public class HomeActivity extends AppCompatActivity implements IView<HomePresenter> {

    private HomePresenter mainPresenter;
    private Intent serviceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setMainPresenter(new HomePresenter(this));
        StepsCountPresenter stepsCountPresenter = new StepsCountPresenter(this);
        serviceIntent = new Intent(HomeActivity.this,StepsCountServices.class);
        startService(serviceIntent);

        Intent registerintent = new Intent(HomeActivity.this, RegisterService.class);
        startService(registerintent);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void UpdateView(int code, Object entity) {

    }

    @Override
    public void SwitchView(int code) {

    }

    @Override
    public void setMainPresenter(HomePresenter presenter) {
        this.mainPresenter = presenter;
    }

    @Override
    public HomePresenter getMainpresnter() {
        return null;
    }

    @Override
    public void StartNewActivity(Intent intent) {

    }

    @Override
    public Activity getAppActivity() {
        return this;
    }

    @Override
    public Fragment getCurrentFragment() {
        return null;
    }

    @Override
    public FragmentManager GetFragmentManager() {
        return null;
    }
}