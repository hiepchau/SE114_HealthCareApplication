package com.example.se114_healthcareapplication.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.example.se114_healthcareapplication.R;
import com.example.se114_healthcareapplication.Recievers.AlarmReciever;
import com.example.se114_healthcareapplication.Services.RegisterService;
import com.example.se114_healthcareapplication.Services.StepsCountServices;
import com.example.se114_healthcareapplication.generalinterfaces.IView;
import com.example.se114_healthcareapplication.presenter.HomePresenter;
import com.example.se114_healthcareapplication.presenter.StepsCountPresenter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import org.jetbrains.annotations.NotNull;


public class HomeActivity extends AppCompatActivity implements IView<HomePresenter> {

    private HomePresenter mainPresenter;
    private Intent serviceIntent;
    Intent registerintent;
    int selectedItemMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setMainPresenter(new HomePresenter(this));
        StepsCountPresenter stepsCountPresenter = new StepsCountPresenter(this);
        selectedItemMenu = R.id.action_home;

        BottomNavigationView navigationView = findViewById(R.id.bottom_nav);
        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                if(item.getItemId() == selectedItemMenu){
                    return true;
                }
                selectedItemMenu = item.getItemId();
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

//        registerintent = new Intent(HomeActivity.this, RegisterService.class);
//        startService(registerintent);
        registerReceiver(new AlarmReciever(),new IntentFilter("CUSTOM_ALARM"));
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
        return getSupportFragmentManager().findFragmentById(R.id.fragmentContainer_homeactivity);
    }

    @Override
    public FragmentManager GetFragmentManager() {
        return getSupportFragmentManager();
    }
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        if (getCurrentFocus() != null) {
//            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
//        }
//        return super.dispatchTouchEvent(ev);
//    }
}