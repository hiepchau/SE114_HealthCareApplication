package com.example.se114_healthcareapplication.presenter;

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
import com.example.se114_healthcareapplication.step_segment;

public class StepsCountPresenter extends PresenterBase implements IPresenter {

    class StepReciever extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals("SEND_NEW_STEPS"))
            {
                int step = intent.getIntExtra("steps",0);
                _view.UpdateView(step_segment.UPDATE_STEPS,step);
                float percent = (float) step/3000;
                _view.UpdateView(step_segment.UPDATE_PERCENT,percent);
                // Show it in GraphView
            }
        }
    }

    private StepReciever stepReciever;
    public StepsCountPresenter(IView view) {
        super(view);
        stepReciever = new StepReciever();
        _view.getAppActivity().registerReceiver(stepReciever,new IntentFilter("SEND_NEW_STEPS"));
    }

    @Override
    public void NotifyPresenter(int code) {

    }

}
