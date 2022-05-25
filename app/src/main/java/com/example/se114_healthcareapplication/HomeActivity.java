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
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.se114_healthcareapplication.Recievers.AlarmReciever;
import com.example.se114_healthcareapplication.Services.RegisterService;
import com.example.se114_healthcareapplication.Services.StepsCountServices;
import com.example.se114_healthcareapplication.generalinterfaces.IPresenter;
import com.example.se114_healthcareapplication.generalinterfaces.IView;
import com.example.se114_healthcareapplication.presenter.AlarmPresenter;
import com.example.se114_healthcareapplication.presenter.HomePresenter;
import com.example.se114_healthcareapplication.presenter.MenuPresenter;
import com.example.se114_healthcareapplication.presenter.StepsCountPresenter;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import org.jetbrains.annotations.NotNull;
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

        BottomNavigationView navigationView = findViewById(R.id.bottom_nav);
        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_home:
                        mainPresenter.NotifyPresenter(HomePresenter.SWITCH_TO_HOME);
                        break;
                    case R.id.action_target:
                        mainPresenter.NotifyPresenter(HomePresenter.SWITCH_TO_TARGET);
                        break;
                    case R.id.action_notification:
                        mainPresenter.NotifyPresenter(HomePresenter.SWITCH_TO_NOTIFICATIONS);
                        break;
                    case R.id.action_user:
                        mainPresenter.NotifyPresenter(HomePresenter.SWITCH_TO_USER);
                        break;
                }
                return true;
            }
        });

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