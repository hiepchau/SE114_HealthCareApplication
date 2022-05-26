package com.example.se114_healthcareapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.widget.EditText;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentManager;
import com.example.se114_healthcareapplication.generalinterfaces.IView;
import com.example.se114_healthcareapplication.presenter.BMIPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link bmi_segment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class bmi_segment extends Fragment implements IView<BMIPresenter> {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final int UPDATE_BMI =19233;
    BMIPresenter mainPresenter;
    TextView heighttxt, weighttxt, bmitxt;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public bmi_segment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment bmi_segment.
     */
    // TODO: Rename and change types and number of parameters
    public static bmi_segment newInstance(String param1, String param2) {
        bmi_segment fragment = new bmi_segment();
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
        View v = inflater.inflate(R.layout.fragment_bmi_segment, container, false);
        heighttxt = v.findViewById(R.id.height_edt);
        weighttxt = v.findViewById(R.id.weight_edt);
        bmitxt = v.findViewById(R.id.bmi_edt);
        setMainPresenter(new BMIPresenter(this));
        return v;
    }

    @Override
    public void UpdateView(int code, Object entity) {
        if(code == UPDATE_BMI){
            ArrayList<Double> bmilist = (ArrayList<Double>) entity;
            double wei = bmilist.get(0);
            double hei = bmilist.get(1);
            double bmi = hei/wei;
            weighttxt.setText(String.valueOf(wei));
            heighttxt.setText(String.valueOf(hei));
            bmitxt.setText(String.valueOf(bmi));
        }
    }

    @Override
    public void SwitchView(int code) {

    }

    @Override
    public void setMainPresenter(BMIPresenter presenter) {
        this.mainPresenter = presenter;
    }

    @Override
    public BMIPresenter getMainpresnter() {
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