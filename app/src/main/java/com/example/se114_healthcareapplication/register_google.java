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
import com.example.se114_healthcareapplication.presenter.AuthenticatePresenter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link register_google#newInstance} factory method to
 * create an instance of this fragment.
 */
public class register_google extends Fragment implements IView<AuthenticatePresenter> {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private AuthenticatePresenter mainPresenter;
    private Button  register;
    private EditText firstname,age,height,weight;

    public register_google() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment register_google.
     */
    // TODO: Rename and change types and number of parameters
    public static register_google newInstance(String param1, String param2) {
        register_google fragment = new register_google();
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
        View v = inflater.inflate(R.layout.fragment_register_google, container, false);


        register = v.findViewById(R.id.google_register_btn);
        firstname = v.findViewById(R.id.firstname_edt);
        age = v.findViewById(R.id.age_edt);
        height = v.findViewById(R.id.height_edt);
        weight = v.findViewById(R.id.weight_edt);

        setMainPresenter(new AuthenticatePresenter(this));
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fname = firstname.getText().toString();
                int Age = Integer.parseInt(age.getText().toString());
                double Height = Double.parseDouble(height.getText().toString());
                double Weight = Double.parseDouble(weight.getText().toString());
                mainPresenter.RegisterGoogle(fname,Age,Height,Weight);
            }
        });

        return v;
    }

    @Override
    public void UpdateView(int code, Object entity) {

    }

    @Override
    public void SwitchView(int code) {

    }

    @Override
    public void setMainPresenter(AuthenticatePresenter presenter) {
        mainPresenter = presenter;
    }

    @Override
    public AuthenticatePresenter getMainpresnter() {
        return null;
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
        return null;
    }

    @Override
    public FragmentManager GetFragmentManager() {
        return getActivity().getSupportFragmentManager();
    }
}