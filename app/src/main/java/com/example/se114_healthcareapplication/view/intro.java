package com.example.se114_healthcareapplication.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.widget.Button;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.se114_healthcareapplication.R;
import com.example.se114_healthcareapplication.view.authentication.login;
import com.example.se114_healthcareapplication.generalinterfaces.IPresenter;
import com.example.se114_healthcareapplication.generalinterfaces.IView;
import com.example.se114_healthcareapplication.presenter.IntroPresenter;
import com.example.se114_healthcareapplication.view.authentication.signup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link intro#newInstance} factory method to
 * create an instance of this fragment.
 */
public class intro extends Fragment implements IView<IntroPresenter> {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private IPresenter mainPresenter;
    private Button nextBtn,registbtn;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public intro() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment intro.
     */
    // TODO: Rename and change types and number of parameters
    public static intro newInstance(String param1, String param2) {
        intro fragment = new intro();
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
        View v = inflater.inflate(R.layout.fragment_intro, container, false);
        nextBtn = v.findViewById(R.id.StartBtn);
        registbtn = v.findViewById(R.id.btnRegister);
        setMainPresenter(new IntroPresenter(this));
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                IntroPresenter presenter = (IntroPresenter) mainPresenter;
                presenter.getRequiredPermission();

                mainPresenter.NotifyPresenter(0);
            }
        });

        registbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.authenticateContainer, signup.class,null).commit();
                IntroPresenter prstr = (IntroPresenter) mainPresenter;
                ((IntroPresenter) mainPresenter).getRequiredPermission();
            }
        });

        return v;
    }

    @Override
    public void UpdateView(int code, Object entity) {

    }

    @Override
    public void SwitchView(int code) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.authenticateContainer, login.class,null).commit();
    }

    @Override
    public void setMainPresenter(IntroPresenter presenter) {
        mainPresenter = presenter;
    }

    @Override
    public IntroPresenter getMainpresnter() {
        return null;
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
        return null;
    }

    @Override
    public FragmentManager GetFragmentManager() {
        return null;
    }

}