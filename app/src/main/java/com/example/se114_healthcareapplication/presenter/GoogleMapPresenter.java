package com.example.se114_healthcareapplication.presenter;

import android.content.Context;
import com.example.se114_healthcareapplication.view.components.GoogleMapFragment;
import com.example.se114_healthcareapplication.generalinterfaces.IPresenter;
import com.example.se114_healthcareapplication.generalinterfaces.IView;
import com.example.se114_healthcareapplication.model.RunningModel;
import com.example.se114_healthcareapplication.model.entity.RunningEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class GoogleMapPresenter extends PresenterBase implements IPresenter {
    Timer mTimer;
    public long starttime;
    private RunningModel runningModel;
    public boolean isTimerRunning;
    public GoogleMapPresenter(IView view) {
        super(view);
        isTimerRunning = false;
        runningModel = new RunningModel(this);
    }

    @Override
    public void NotifyPresenter(int code) {

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
                long millis = System.currentTimeMillis() - starttime;
                int seconds = (int) (millis / 1000);
                int minutes = seconds / 60;
                seconds     = seconds % 60;
                List<Integer> lstime = new ArrayList<>();
                lstime.add(minutes);
                lstime.add(seconds);
                _view.UpdateView(GoogleMapFragment.UPDATE_TIMER,lstime);
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
        RunningEntity entity = new RunningEntity(distance,System.currentTimeMillis()-starttime);
        runningModel.UpdateDatabase(entity);
    }

}
