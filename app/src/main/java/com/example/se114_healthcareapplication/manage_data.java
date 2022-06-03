package com.example.se114_healthcareapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentManager;
import com.example.se114_healthcareapplication.generalinterfaces.IView;
import com.example.se114_healthcareapplication.model.entity.StatisticEntity;
import com.example.se114_healthcareapplication.model.entity.UserEntity;
import com.example.se114_healthcareapplication.presenter.UserPresenter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link manage_data#newInstance} factory method to
 * create an instance of this fragment.
 */
public class manage_data extends Fragment implements IView<UserPresenter> {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private EditText heighttxt, weighttxt, firstnametxt, lastnametxt, agetxt;
    private Button acceptbtn;
    private UserPresenter mainPresenter;

    public manage_data() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment manage_data.
     */
    // TODO: Rename and change types and number of parameters
    public static manage_data newInstance(String param1, String param2) {
        manage_data fragment = new manage_data();
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
        View v = inflater.inflate(R.layout.fragment_manage_data, container, false);
        heighttxt = v.findViewById(R.id.height_edt);
        weighttxt = v.findViewById(R.id.weight_edt);
        firstnametxt = v.findViewById(R.id.firstname_edt);
        lastnametxt = v.findViewById(R.id.lastname_edt);
        agetxt = v.findViewById(R.id.age_edt);
        acceptbtn = v.findViewById(R.id.btn_accept);
        setMainPresenter(new UserPresenter(this));
        return v;
    }

    @Override
    public void UpdateView(int code, Object entity) {
        if(code == UserPresenter.UPDATE_USER_INFO){
            UserEntity user = (UserEntity)entity;
            agetxt.setText(String.valueOf(user.Age));
            firstnametxt.setText(user.FirstName);
            lastnametxt.setText(user.LastName);
        }
        if(code == UserPresenter.UPDATE_STATISTIC){
            StatisticEntity stat = (StatisticEntity)entity;
            heighttxt.setText(String.valueOf(stat.Height));
            weighttxt.setText(String.valueOf(stat.Weight));
        }
    }

    @Override
    public void SwitchView(int code) {

    }

    @Override
    public void setMainPresenter(UserPresenter presenter) {
        mainPresenter = presenter;
    }

    @Override
    public UserPresenter getMainpresnter() {
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