package com.example.se114_healthcareapplication.view.bottom_nav;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentManager;
import com.example.se114_healthcareapplication.R;
import com.example.se114_healthcareapplication.generalinterfaces.IView;
import com.example.se114_healthcareapplication.model.StatisticModel;
import com.example.se114_healthcareapplication.model.entity.StatisticEntity;
import com.example.se114_healthcareapplication.model.entity.UserEntity;
import com.example.se114_healthcareapplication.presenter.StatisticPresenter;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StatisticsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StatisticsFragment extends Fragment implements IView<StatisticPresenter> {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RelativeLayout waterRelative, walkRelative, sleepRelative, emoRelative;
    TextView waterstatus, watervalue, waterrate, walkstatus, walkvalue,walkrate, sleepstatus, sleepvalue, sleeprate,
            emotionvalue;
    private StatisticPresenter mainPresenter;
    private UserEntity user;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public StatisticsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment notifications.
     */
    // TODO: Rename and change types and number of parameters
    public static StatisticsFragment newInstance(String param1, String param2) {
        StatisticsFragment fragment = new StatisticsFragment();
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
        View v = inflater.inflate(R.layout.fragment_statistics, container, false);

        waterRelative = v.findViewById(R.id.statistic_water_rlt);
        walkRelative = v.findViewById(R.id.statistic_steps_rlt);
        sleepRelative = v.findViewById(R.id.statistic_sleep_rlt);
        emoRelative = v.findViewById(R.id.statistic_emotional_rlt);

        waterstatus = v.findViewById(R.id.water_status_txv);
        waterrate = v.findViewById(R.id.water_rate_txv);
        watervalue = v.findViewById(R.id.water_value_txv);

        sleepstatus = v.findViewById(R.id.sleep_status_txv);
        sleeprate = v.findViewById(R.id.sleep_rate_txv);
        sleepvalue = v.findViewById(R.id.sleep_value_txv);

        walkrate = v.findViewById(R.id.walk_rate_txv);
        walkstatus = v.findViewById(R.id.walk_status_txv);
        walkvalue = v.findViewById(R.id.walk_value_txv);

        emotionvalue = v.findViewById(R.id.emotion_value_txv);
        emoRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainPresenter.NotifyPresenter(StatisticPresenter.SWITCH_TO_EMOTION);
            }
        });

        walkRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainPresenter.NotifyPresenter(StatisticPresenter.SWITCH_TO_STEPS);
            }
        });

        sleepRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainPresenter.NotifyPresenter(StatisticPresenter.SWITCH_TO_SLEEP);
            }
        });
        waterRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainPresenter.NotifyPresenter(StatisticPresenter.SWITCH_TO_WATER);
            }
        });
        setMainPresenter(new StatisticPresenter(this));
        return v;
    }

    @Override
    public void UpdateView(int code, Object entity) {
        if(code == StatisticPresenter.USER_RETRIEVED){
            user = (UserEntity) entity;
        }
        if(code == StatisticPresenter.DATA_RETRIEVED){
            DecimalFormat decimalFormat = new DecimalFormat("0.00");
            ArrayList<StatisticEntity> entityArrayList = (ArrayList<StatisticEntity>) entity;
            int primaryWaterGoal = 2500;
            if(user.Gender ==1)
                primaryWaterGoal = 3500;
            double avgWater = 0;
            for(StatisticEntity statisticEntity : entityArrayList){
                avgWater+=statisticEntity.Water;
            }
            avgWater/=entityArrayList.size();
            double waterpor = avgWater/primaryWaterGoal;
            String waterstate = "";
            if(waterpor>1.3){
                waterstate = "Ok";
            }
            if(waterpor<=1.3&&waterpor>0.7){
                waterstate = "Good";
            }
            if(waterpor<=0.7&&waterpor>0.5){
                waterstate = "Ok";
            }
            if(waterpor<=0.5){
                waterstate = "Bad";
            }
            waterrate.setText(decimalFormat.format(waterpor*100)+"%");
            waterstatus.setText(waterstate);
            watervalue.setText(decimalFormat.format(avgWater)+" ml");


            double avgSteps = 0;
            for(StatisticEntity statisticEntity : entityArrayList){
                avgSteps+=statisticEntity.Steps;
            }
            avgSteps/=entityArrayList.size();
            double stepsPor = avgSteps/3000;
            String stepsState = "";
            if(stepsPor>1.3){
                stepsState = "Great";
            }
            if(stepsPor<=1.3&&stepsPor>0.7){
                stepsState = "Good";
            }
            if(stepsPor<=0.7&&stepsPor>0.5){
                stepsState = "Ok";
            }
            if(stepsPor<=0.5){
                stepsState = "Bad";
            }
            walkrate.setText(decimalFormat.format(stepsPor*100)+"%");
            walkvalue.setText(String.valueOf((int)avgSteps));
            walkstatus.setText(stepsState);

            double avgsleep = 0;
            for(StatisticEntity statisticEntity : entityArrayList){
                avgsleep+=statisticEntity.SleepTime;
            }
            avgsleep/=entityArrayList.size();
            double sleepPor = avgsleep/(8*3600*1000);
            String SleepState = "";
            if(sleepPor>1.3){
                SleepState = "OK";
            }
            if(sleepPor<=1.3&&sleepPor>0.7){
                SleepState = "Good";
            }
            if(sleepPor<=0.7&&sleepPor>0.5){
                SleepState = "Ok";
            }
            if(sleepPor<=0.5){
                SleepState = "Bad";
            }
            int seconds = (int)avgsleep/1000;
            int minute = seconds/60;
            int hrs = minute/60;
            int min = minute%60;
            sleeprate.setText(decimalFormat.format(sleepPor*100)+"%");
            sleepvalue.setText(String.valueOf(hrs)+" hours "+ String.valueOf(min)+" minutes");
            sleepstatus.setText(SleepState);

            double emo =0;
            for(StatisticEntity statisticEntity : entityArrayList){
                emo+=statisticEntity.EmotionalLevel;
            }
            emo/=entityArrayList.size();
            int estatus = (int) Math.round(emo);
            String emoState = "";
            if(estatus==1){
                emoState = "Terrible";
            }

            if(estatus==2){
                emoState = "Bad";
            }

            if(estatus==3){
                emoState = "OK";
            }

            if(estatus==4){
                emoState = "Good";
            }
            if(estatus==5){
                emoState = "Great";
            }
            emotionvalue.setText(emoState);
        }
    }

    @Override
    public void SwitchView(int code) {

    }

    @Override
    public void setMainPresenter(StatisticPresenter presenter) {
        mainPresenter = presenter;
    }

    @Override
    public StatisticPresenter getMainpresnter() {
        return mainPresenter;
    }

    @Override
    public void StartNewActivity(Intent intent) {
        startActivity(intent);
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
}