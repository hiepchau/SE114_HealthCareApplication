package com.example.se114_healthcareapplication.view.components;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.*;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentManager;
import com.example.se114_healthcareapplication.R;
import com.example.se114_healthcareapplication.generalinterfaces.IView;
import com.example.se114_healthcareapplication.presenter.AlarmPresenter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link alarm_segment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class alarm_segment extends Fragment implements IView<AlarmPresenter> {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ToggleButton alarmToggle;
    private Button setsleepbtn, setwakebtn;
    private TextView sleephourtxt, sleepmintxt, wakehourtxt, wakemintxt;
    private AlarmPresenter mainPresenter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public alarm_segment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment alarm_segment.
     */
    // TODO: Rename and change types and number of parameters
    public static alarm_segment newInstance(String param1, String param2) {
        alarm_segment fragment = new alarm_segment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_alarm_segment, container, false);
        alarmToggle = view.findViewById(R.id.toggleBtnAlarm);
        setsleepbtn = view.findViewById(R.id.btn_select_sleep);
        setwakebtn = view.findViewById(R.id.btn_select_wake);
        sleephourtxt = view.findViewById(R.id.sleep_hrs_txv);
        sleepmintxt = view.findViewById(R.id.sleep_min_txv);
        wakehourtxt = view.findViewById(R.id.wake_hrs_txv);
        wakemintxt = view.findViewById(R.id.wake_min_txv);



        setMainPresenter(new AlarmPresenter(this));
        setupTurnedOnSleeping();

        setsleepbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocalDateTime localDateTime = LocalDateTime.now();
                TimePickerDialog dlg = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String hour = "", min = "";
                        if(hourOfDay/10==0)
                            hour+="0";
                        if(minute/10==0)
                            min+="0";
                        hour+=String.valueOf(hourOfDay);
                        min+=String.valueOf(minute);
                        sleephourtxt.setText(hour);
                        sleepmintxt.setText(min);
                    }
                },localDateTime.getHour(),localDateTime.getMinute(),true);
                dlg.show();
            }
        });

        setwakebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocalDateTime localDateTime = LocalDateTime.now();
                TimePickerDialog dlg = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String hour = "", min = "";
                        if(hourOfDay/10==0)
                            hour+="0";
                        if(minute/10==0)
                            min+="0";
                        hour+=String.valueOf(hourOfDay);
                        min+=String.valueOf(minute);
                        wakehourtxt.setText(hour);
                        wakemintxt.setText(min);
                    }
                },localDateTime.getHour(),localDateTime.getMinute(),true);
                dlg.show();
            }
        });
        alarmToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    if(sleephourtxt.getText().toString().isEmpty()
                    || sleepmintxt.getText().toString().isEmpty()
                    || wakehourtxt.getText().toString().isEmpty()
                    || wakemintxt.getText().toString().isEmpty()){
                        Toast.makeText(getContext(),"You have to select both bed time and wake up time",Toast.LENGTH_SHORT).show();
                        alarmToggle.setChecked(false);
                        return;
                    }
                    int bedhour = Integer.parseInt(sleephourtxt.getText().toString());
                    int bedmin = Integer.parseInt(sleepmintxt.getText().toString());
                    int wakehour = Integer.parseInt(wakehourtxt.getText().toString());
                    int wakemin = Integer.parseInt(wakemintxt.getText().toString());
                    mainPresenter.triggerCustomAlarm(wakehour,wakemin,bedhour,bedmin);
                }
                else{
                    setsleepbtn.setEnabled(true);
                    setwakebtn.setEnabled(true);
                    mainPresenter.cancelCurrentAlarm();
                }
            }
        });
        return view;
    }

    @Override
    public void UpdateView(int code, Object entity) {
    }

    @Override
    public void SwitchView(int code) {

    }

    @Override
    public void setMainPresenter(AlarmPresenter presenter) {
        mainPresenter = presenter;
    }

    @Override
    public AlarmPresenter getMainpresnter() {
        return mainPresenter;
    }

    @Override
    public void StartNewActivity(Intent intent) {

    }

    @Override
    public Activity getAppActivity() {
        return getActivity();
    }

    @Override
    public Fragment getCurrentFragment() {
        return this;
    }

    @Override
    public FragmentManager GetFragmentManager() {
        return getActivity().getSupportFragmentManager();
    }
    private void setupTurnedOnSleeping(){
        if(mainPresenter.isTurnedOnSleeping()){
            alarmToggle.setChecked(true);
            long tmpsleep = mainPresenter.getBedTime();
            long tmpwake = mainPresenter.getWakeTIme();
            LocalDateTime localsleep = LocalDateTime.ofInstant(Instant.ofEpochMilli(tmpsleep), TimeZone.getDefault().toZoneId());
            LocalDateTime localwake = LocalDateTime.ofInstant(Instant.ofEpochMilli(tmpwake), TimeZone.getDefault().toZoneId());

            sleephourtxt.setText(String.valueOf(localsleep.getHour()));
            sleepmintxt.setText(String.valueOf(localsleep.getMinute()));
            wakehourtxt.setText(String.valueOf(localwake.getHour()));
            wakemintxt.setText(String.valueOf(localwake.getMinute()));
            setwakebtn.setEnabled(false);
            setsleepbtn.setEnabled(false);
        }
    }
}