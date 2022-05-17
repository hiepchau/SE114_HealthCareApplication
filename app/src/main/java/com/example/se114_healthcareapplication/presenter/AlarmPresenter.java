package com.example.se114_healthcareapplication.presenter;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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

    public void setAlarm(){
        alarmMgr = (AlarmManager)_view.getAppActivity().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(_view.getAppActivity(), AlarmPresenter.class);
        alarmIntent = PendingIntent.getBroadcast(_view.getAppActivity(), 0, intent, 0);

// Set the alarm to start at 8:30 a.m.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 30);

// setRepeating() lets you specify a precise custom interval--in this case,
// 20 minutes.
        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                1000 * 60 * 20, alarmIntent);
    }
}
