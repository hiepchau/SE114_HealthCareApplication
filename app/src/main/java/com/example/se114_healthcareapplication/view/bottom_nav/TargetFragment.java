package com.example.se114_healthcareapplication.view.bottom_nav;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentManager;
import com.example.se114_healthcareapplication.R;
import com.example.se114_healthcareapplication.generalinterfaces.IView;
import com.example.se114_healthcareapplication.model.entity.StatisticEntity;
import com.example.se114_healthcareapplication.model.entity.UserEntity;
import com.example.se114_healthcareapplication.presenter.TargetPresenter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TargetFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TargetFragment extends Fragment implements IView<TargetPresenter> {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TargetPresenter mainPresenter;
    private Button calendarbtn;
    private ProgressBar progressBarwater, progressBarsteps, progressBarsleep;
    private TextView datetxt, minhrs, maxhrs, minsteps, maxsteps, minwater, maxwater;

    public TargetFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TargetFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TargetFragment newInstance(String param1, String param2) {
        TargetFragment fragment = new TargetFragment();
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
        View v = inflater.inflate(R.layout.fragment_target, container, false);

        calendarbtn = v.findViewById(R.id.btn_calendar);
        progressBarwater = v.findViewById(R.id.water_prs);
        progressBarsleep = v.findViewById(R.id.sleep_prs);
        progressBarsteps = v.findViewById(R.id.steps_prs);
        datetxt = v.findViewById(R.id.date_txv);
        minhrs = v.findViewById(R.id.minhour);
        maxhrs = v.findViewById(R.id.maxhour);
        minsteps = v.findViewById(R.id.minsteps);
        maxsteps = v.findViewById(R.id.maxsteps);
        minwater = v.findViewById(R.id.minwater);
        maxwater = v.findViewById(R.id.maxwater);
        DateTimeFormatter format = DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy");
        datetxt.setText(format.format(LocalDateTime.now()));

        calendarbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocalDate date = LocalDate.parse(datetxt.getText().toString(),format);
                DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        DateTimeFormatter format = DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy");
                        DateTimeFormatter formatsearch = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                        LocalDateTime local = LocalDateTime.of(year,month,dayOfMonth,0,0);
                        datetxt.setText(format.format(local));
                        mainPresenter.getSpecificData(formatsearch.format(local));
                    }
                },date.getYear(),date.getMonthValue(),date.getDayOfMonth());
                dialog.show();
            }
        });
        setMainPresenter(new TargetPresenter(this));
        return v;
    }

    @Override
    public void UpdateView(int code, Object entity) {
        if(code == TargetPresenter.UPDATE_USER_INFO){
            UserEntity user = (UserEntity) entity;
            if(user.Gender==0)
                progressBarwater.setMax(2500);
            else
                progressBarwater.setMax(3500);
            progressBarsteps.setMax(3000);
            progressBarsleep.setMax(8);
        }
        if(code == TargetPresenter.UPDATE_DAILY_STATISTIC){
            if(entity!=null) {
                StatisticEntity stat = (StatisticEntity) entity;
                progressBarwater.setProgress(stat.Water);
                progressBarsteps.setProgress(stat.Steps);
                double sleeptime = Math.abs((double) stat.SleepTime / (3600 * 1000));
                int partime = (int) sleeptime;
                progressBarsleep.setProgress(partime);
                minwater.setText(String.valueOf(stat.Water) + " ml");
                minsteps.setText(String.valueOf(stat.Steps) + " steps");
                minhrs.setText(String.valueOf(partime) + " hours");
            }
            else {
                progressBarwater.setProgress(0);
                progressBarsteps.setProgress(0);
                progressBarsleep.setProgress(0);
            }
        }
    }

    @Override
    public void SwitchView(int code) {

    }

    @Override
    public void setMainPresenter(TargetPresenter presenter) {
        mainPresenter = presenter;
    }

    @Override
    public TargetPresenter getMainpresnter() {
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