package com.example.se114_healthcareapplication.presenter;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.widget.Toast;
import com.example.se114_healthcareapplication.generalinterfaces.IPresenter;
import com.example.se114_healthcareapplication.generalinterfaces.IView;
import com.example.se114_healthcareapplication.model.StatisticModel;
import com.example.se114_healthcareapplication.step_segment;

public class StepsCountPresenter extends PresenterBase implements IPresenter {

    private StatisticModel statisticModel;
    private int currentSteps;

    public StepsCountPresenter(IView view) {
        super(view);
        statisticModel = new StatisticModel(this);
    }

    @Override
    public void NotifyPresenter(int code) {
        if(code == StatisticModel.DONE_INIT_DATA){
            currentSteps = statisticModel.currentEntity.Steps;
            _view.UpdateView(step_segment.UPDATE_STEPS,currentSteps);
            _view.UpdateView(step_segment.UPDATE_PERCENT,((double)currentSteps/3000)*100);
        }
    }

    @Override
    public Context getCurrentContext() {
        return _view.getAppActivity();
    }

}
