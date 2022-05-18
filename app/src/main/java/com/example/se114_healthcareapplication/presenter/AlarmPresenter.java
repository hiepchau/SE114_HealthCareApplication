package com.example.se114_healthcareapplication.presenter;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.AlarmClock;
import android.util.Log;
import com.example.se114_healthcareapplication.generalinterfaces.IPresenter;
import com.example.se114_healthcareapplication.generalinterfaces.IView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AlarmPresenter extends PresenterBase implements IPresenter {

    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    public AlarmPresenter(IView view) {
        super(view);
    }

    @Override
    public void NotifyPresenter(int code) {

    }

    public void setAlarm(int Hour, int Minute, String message, Boolean isrepeated){

        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
        intent.putExtra(AlarmClock.EXTRA_HOUR,Hour);
        intent.putExtra(AlarmClock.EXTRA_MINUTES,Minute);
        intent.putExtra(AlarmClock.EXTRA_MESSAGE, message);
        intent.putExtra(AlarmClock.EXTRA_DAYS,
                new int[]{
                        Calendar.MONDAY,
                        Calendar.TUESDAY,
                        Calendar.WEDNESDAY,
                        Calendar.THURSDAY,
                        Calendar.FRIDAY,
                        Calendar.SATURDAY,
                        Calendar.SUNDAY
                }
        );
        _view.getAppActivity().startActivity(intent);
    }
}
