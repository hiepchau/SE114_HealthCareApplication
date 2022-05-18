package com.example.se114_healthcareapplication.presenter;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.provider.AlarmClock;
import com.example.se114_healthcareapplication.generalinterfaces.IPresenter;
import com.example.se114_healthcareapplication.generalinterfaces.IView;

import java.util.Calendar;

public class AlarmPresenter extends PresenterBase implements IPresenter {

    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    public AlarmPresenter(IView view) {
        super(view);
    }

    @Override
    public void NotifyPresenter(int code) {

    }

    public void setAlarm(int Hour, int Minute, String message){
        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
        intent.putExtra(AlarmClock.EXTRA_HOUR,Hour);
        intent.putExtra(AlarmClock.EXTRA_MINUTES,Minute);
        intent.putExtra(AlarmClock.EXTRA_MESSAGE, message);
        _view.getAppActivity().startActivity(intent);
    }
}
