package com.example.se114_healthcareapplication.presenter;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.provider.AlarmClock;
import android.widget.Toast;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.se114_healthcareapplication.R;
import com.example.se114_healthcareapplication.generalinterfaces.IPresenter;
import com.example.se114_healthcareapplication.generalinterfaces.IView;
import com.example.se114_healthcareapplication.model.StatisticModel;
import com.example.se114_healthcareapplication.view.MenuFragment;
import com.example.se114_healthcareapplication.view.bottom_nav.HomeFragment;

import java.util.Calendar;

public class AlarmPresenter extends PresenterBase implements IPresenter {

    private AlarmManager alarmMgr;
    private StatisticModel statisticModel;
    long CurrentSleeping;
    public AlarmPresenter(IView view) {

        super(view);
        alarmMgr = (AlarmManager) _view.getAppActivity().getSystemService(_view.getAppActivity().ALARM_SERVICE);
        CurrentSleeping = 0;
        statisticModel = new StatisticModel(this);
    }

    @Override
    public void NotifyPresenter(int code) {
        if(code == StatisticModel.DONE_INIT_DATA){
            CurrentSleeping = statisticModel.currentEntity.SleepTime;
        }
        if(code == BACK_ON_MENU){
            FragmentManager fragmentManager = _view.GetFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer_function, MenuFragment.class,null).addToBackStack("").commit();
        }
    }

    @Override
    public Activity getCurrentContext() {
        return _view.getAppActivity();
    }

    public void setAlarm(int Hour, int Minute, String message, Boolean isrepeated){

        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
        intent.putExtra(AlarmClock.EXTRA_HOUR,Hour);
        intent.putExtra(AlarmClock.EXTRA_MINUTES,Minute);
        intent.putExtra(AlarmClock.EXTRA_MESSAGE, message);
        intent.putExtra(AlarmClock.EXTRA_SKIP_UI,true);
        if(isrepeated) {
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
        }
        _view.getAppActivity().startActivity(intent);

    }

    public void DismissAlarm(String message){
        Intent intent1 = new Intent(AlarmClock.ACTION_DISMISS_ALARM);
        intent1.putExtra(AlarmClock.EXTRA_MESSAGE, message);
//        intent1.putExtra(AlarmClock.EXTRA_SKIP_UI,true);
        _view.getAppActivity().startActivity(intent1);
        Toast.makeText(_view.getAppActivity(),"Dismiss alarm",Toast.LENGTH_SHORT).show();
    }


    public void cancelCurrentAlarm(){
        stopSleeping();
        Intent alarmIntent = new Intent("CUSTOM_ALARM");
        PendingIntent fireIntent = PendingIntent.getBroadcast(_view.getAppActivity(),0,alarmIntent,0);
        fireIntent.cancel();
        alarmMgr.cancel(fireIntent);
    }
    public void triggerCustomAlarm(int HRS, int MIN,int BeginHRS, int BeginMIN){

        Intent alarmIntent = new Intent("CUSTOM_ALARM");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY,HRS);
        calendar.set(Calendar.MINUTE,MIN);
        calendar.set(Calendar.SECOND,0);
        if(calendar.getTimeInMillis()< System.currentTimeMillis()){
            calendar.add(Calendar.DATE,1);
        }

        Calendar calendarbegin = Calendar.getInstance();
        calendarbegin.setTimeInMillis(System.currentTimeMillis());
        calendarbegin.set(Calendar.HOUR_OF_DAY,BeginHRS);
        calendarbegin.set(Calendar.MINUTE,BeginMIN);
        beginSleeping(calendarbegin.getTimeInMillis(),calendar.getTimeInMillis());

        PendingIntent fireIntent = PendingIntent.getBroadcast(_view.getAppActivity(),0,alarmIntent,0);
        alarmMgr.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis() ,fireIntent);
    }

    public boolean isTurnedOnSleeping(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(_view.getAppActivity().getApplicationContext());
        long sleeping = prefs.getLong(_view.getAppActivity().getString(R.string.pref_sleep_time), 0);
        if(sleeping == 0)
            return false;
        return true;
    }
    public long getBedTime(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(_view.getAppActivity().getApplicationContext());
        long sleeping = prefs.getLong(_view.getAppActivity().getString(R.string.pref_sleep_time), 0);
        return sleeping;
    }
    public long getWakeTIme(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(_view.getAppActivity().getApplicationContext());
        long wake = prefs.getLong(_view.getAppActivity().getString(R.string.pref_wake_time), 0);
        return wake;
    }
    private void beginSleeping(long begintime, long endtime){
        setSleeping(begintime);
        setwakeup(endtime);
    }
    public void setSleeping(long begintime){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(_view.getAppActivity().getApplicationContext());

        SharedPreferences.Editor edit = prefs.edit();
        edit.putLong(_view.getAppActivity().getString(R.string.pref_sleep_time), begintime);
        edit.commit();

    }

    public void setwakeup(long waketime){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(_view.getAppActivity().getApplicationContext());

        SharedPreferences.Editor edit = prefs.edit();
        edit.putLong(_view.getAppActivity().getString(R.string.pref_wake_time), waketime);
        edit.commit();

    }
    private void stopSleeping(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(_view.getAppActivity().getApplicationContext());
        SharedPreferences.Editor edit = prefs.edit();
        edit.putLong(_view.getAppActivity().getString(R.string.pref_sleep_time), 0);
        edit.putLong(_view.getAppActivity().getString(R.string.pref_wake_time), 0);
        edit.commit();
    }

    public void updateSleepingTime(){
        if(isTurnedOnSleeping()){
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(_view.getAppActivity().getApplicationContext());
            long sleeping = prefs.getLong(_view.getAppActivity().getString(R.string.pref_sleep_time), 0);
            long sleeptoupdate = Calendar.getInstance().getTimeInMillis() - sleeping;
            sleeptoupdate += CurrentSleeping;
            statisticModel.UpdateSleepTime(sleeptoupdate);
            stopSleeping();
        }
    }
}
