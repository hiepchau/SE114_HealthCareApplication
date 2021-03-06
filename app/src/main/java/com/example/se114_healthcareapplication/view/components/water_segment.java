package com.example.se114_healthcareapplication.view.components;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.*;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentManager;
import com.example.se114_healthcareapplication.R;
import com.example.se114_healthcareapplication.generalinterfaces.IView;
import com.example.se114_healthcareapplication.presenter.PresenterBase;
import com.example.se114_healthcareapplication.presenter.WaterPresenter;
import com.webianks.library.scroll_choice.ScrollChoice;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link water_segment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class water_segment extends Fragment implements IView<WaterPresenter> {
List<String> datas = new ArrayList<>();
TextView complete;
Button confirm;
WaterPresenter mainPresenter;
ScrollChoice waterchoice ;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static final int UPDATE_COMPLETE = 101;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextView goalWater, percent;
    Button backbtn;
    ProgressBar progressBar;
    int progress = 1;

    public water_segment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment water_segment.
     */
    // TODO: Rename and change types and number of parameters
    public static water_segment newInstance(String param1, String param2) {
        water_segment fragment = new water_segment();
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


        View v= inflater.inflate(R.layout.fragment_water_segment, container, false);
        loadDatas();

        complete = v.findViewById(R.id.CompleteValue);
        confirm = v.findViewById(R.id.btn_confirm);
        waterchoice = v.findViewById(R.id.Scrollchoice123);
        goalWater = v.findViewById(R.id.DailyGoalWaterValue);
        backbtn = v.findViewById(R.id.buttonturnback);
        progressBar = v.findViewById(R.id.progressbar_water);
        percent = v.findViewById(R.id.percent_txv);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainPresenter.NotifyPresenter(PresenterBase.BACK_ON_MENU);
            }
        });
        waterchoice.addItems(datas,0);//default choice


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int amt = waterchoice.getCurrentItemPosition();
                amt = amt*2*100;
                mainPresenter.addWater(amt);
            }
        });
        Log.e("Water","Register receiver");
        setMainPresenter(new WaterPresenter(this));
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void loadDatas(){
        datas.add("0");
        datas.add("200");
        datas.add("400");
        datas.add("600");
        datas.add("800");
        datas.add("1000");
    }

    @Override
    public void UpdateView(int code, Object entity) {
        if(code == UPDATE_COMPLETE){
            int update = (int)entity;
            float tmpper = ((float)update/progress)*100;
            percent.setText(String.valueOf((int)tmpper)+"%");
            complete.setText(String.valueOf(update)+" ml");
            progressBar.setProgress((int)entity);
        }
        if(code == WaterPresenter.UPDATE_DAILY_GOAL){
            int gen = (int)entity;
            if(gen==0){
                goalWater.setText("2500 ml");
                progressBar.setMax(2500);
                progress = 2500;
                int update = (int)entity;
                float tmpper = ((float)update/progress)*100;
                percent.setText(String.valueOf((int)tmpper)+"%");
            }
            else{
                goalWater.setText("3500 ml");
                progressBar.setMax(3500);
                progress = 3500;
                int update = (int)entity;
                float tmpper = ((float)update/progress)*100;
                percent.setText(String.valueOf((int)tmpper)+"%");
            }
        }
    }

    @Override
    public void SwitchView(int code) {

    }

    @Override
    public void setMainPresenter(WaterPresenter presenter) {
        mainPresenter = presenter;
    }

    @Override
    public WaterPresenter getMainpresnter() {
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
}