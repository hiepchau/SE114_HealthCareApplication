package com.example.se114_healthcareapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ToggleButton;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentManager;
import com.example.se114_healthcareapplication.generalinterfaces.IPresenter;
import com.example.se114_healthcareapplication.generalinterfaces.IView;
import com.example.se114_healthcareapplication.presenter.AlarmPresenter;
import com.webianks.library.scroll_choice.ScrollChoice;

import java.util.ArrayList;
import java.util.List;

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




        setMainPresenter(new AlarmPresenter(this));
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
}