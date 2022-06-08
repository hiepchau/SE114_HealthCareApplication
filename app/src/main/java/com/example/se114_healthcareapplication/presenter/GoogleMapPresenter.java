package com.example.se114_healthcareapplication.presenter;

import android.content.Context;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.se114_healthcareapplication.R;
import com.example.se114_healthcareapplication.view.MenuFragment;
import com.example.se114_healthcareapplication.view.bottom_nav.HomeFragment;
import com.example.se114_healthcareapplication.view.components.GoogleMapFragment;
import com.example.se114_healthcareapplication.generalinterfaces.IPresenter;
import com.example.se114_healthcareapplication.generalinterfaces.IView;
import com.example.se114_healthcareapplication.model.RunningModel;
import com.example.se114_healthcareapplication.model.entity.RunningEntity;
import com.example.se114_healthcareapplication.view.components.list_run;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class GoogleMapPresenter extends PresenterBase implements IPresenter {
    Timer mTimer;
    public long starttime;
    private RunningModel runningModel;
    public static final int SWITCH_TO_RUNNING_LIST = 198234;
    public static final int UPDATE_LIST_RUN = 8123978;
    public static final int BACK_TO_RUNNING = 182391;
    public static final int APPEND_LIST_RUN = 9128347;
    public boolean isTimerRunning;
    public GoogleMapPresenter(IView view) {
        super(view);
        isTimerRunning = false;
        runningModel = new RunningModel(this);
    }

    @Override
    public void NotifyPresenter(int code) {
        if(code == BACK_ON_MENU){
            FragmentManager fragmentManager = _view.GetFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer_function, MenuFragment.class,null).addToBackStack("").commit();
        }
        if(code == SWITCH_TO_RUNNING_LIST){
            FragmentManager fragmentManager = _view.GetFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer_function, list_run.class,null).commit();
        }

        if(code == RunningModel.RUNNING_ENTITY_RETRIEVED){
            _view.UpdateView(UPDATE_LIST_RUN, runningModel.getEntity());
        }

        if(code == RunningModel.RUNNIG_ENTITY_APPEND){
            _view.UpdateView(APPEND_LIST_RUN, runningModel.getEntity());
        }

        if(code == BACK_TO_RUNNING){
            FragmentManager fragmentManager = _view.GetFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer_function, GoogleMapFragment.class,null).addToBackStack("").commit();
        }
    }

    @Override
    public Context getCurrentContext() {
        return _view.getAppActivity().getApplicationContext();
    }
    public void startClock(){
        starttime = System.currentTimeMillis();
        mTimer = new Timer();
        mTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                _view.getAppActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        long millis = System.currentTimeMillis() - starttime;
                        int seconds = (int) (millis / 1000);
                        int minutes = seconds / 60;
                        seconds     = seconds % 60;
                        List<Integer> lstime = new ArrayList<>();
                        lstime.add(minutes);
                        lstime.add(seconds);
                        _view.UpdateView(GoogleMapFragment.UPDATE_TIMER,lstime);
                    }
                });

            }
        },0,1000);
        isTimerRunning = true;
    }

    public void stopClock(){
        mTimer.cancel();
        mTimer.purge();
        isTimerRunning = false;
    }


    public void UpdateRunning(float distance){
        RunningEntity entity = new RunningEntity(distance,System.currentTimeMillis()-starttime, System.currentTimeMillis());
        runningModel.UpdateDatabase(entity);
    }
    public void getRunningList(){
        runningModel.retrieveRunningData();
    }

    public void getRunningLimit6(long cond){
        runningModel.retrieveDataLimitedTo6(cond);
    }
}
