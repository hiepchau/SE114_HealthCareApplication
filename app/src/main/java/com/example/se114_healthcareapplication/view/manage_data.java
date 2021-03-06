package com.example.se114_healthcareapplication.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentManager;
import com.example.se114_healthcareapplication.generalinterfaces.IView;
import com.example.se114_healthcareapplication.custom.CustomEdittext;
import com.example.se114_healthcareapplication.model.entity.StatisticEntity;
import com.example.se114_healthcareapplication.model.entity.UserEntity;
import com.example.se114_healthcareapplication.presenter.UserPresenter;
import com.example.se114_healthcareapplication.R;


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
    private CustomEdittext heighttxt, weighttxt, firstnametxt, lastnametxt, agetxt;
    private Button acceptbtn, resetpassbtn, backbtn;
    private UserPresenter mainPresenter;
    private UserEntity user;
    private StatisticEntity stat;

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
        weighttxt =  v.findViewById(R.id.weight_edt);
        firstnametxt =v.findViewById(R.id.firstname_edt);
        lastnametxt = v.findViewById(R.id.lastname_edt);
        agetxt = v.findViewById(R.id.age_edt);
        acceptbtn = v.findViewById(R.id.btn_accept);
        resetpassbtn = v.findViewById(R.id.resetpass_btn);
        backbtn = v.findViewById(R.id.buttonturnback);
        setMainPresenter(new UserPresenter(this));
        acceptbtn.setEnabled(false);
        acceptbtn.setBackground(getResources().getDrawable(R.drawable.btn_disabled));

        resetpassbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainPresenter.NotifyPresenter(UserPresenter.SWITCH_TO_RESET_PASS);
            }
        });
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainPresenter.NotifyPresenter(UserPresenter.BACK_TO_DATA);
            }
        });
        heighttxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkIfCanUpdate();
            }
        });

        weighttxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkIfCanUpdate();
            }
        });

        firstnametxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkIfCanUpdate();
            }
        });

        lastnametxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkIfCanUpdate();
            }
        });

        agetxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkIfCanUpdate();
            }
        });

        acceptbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(stat!= null && user!=null) {
                    user.FirstName = firstnametxt.getText().toString();
                    user.LastName = lastnametxt.getText().toString();
                    user.Age = Integer.parseInt(agetxt.getText().toString());
                    stat.Height = Double.parseDouble(heighttxt.getText().toString());
                    stat.Weight = Double.parseDouble(weighttxt.getText().toString());
                    mainPresenter.UpdateData(user,stat);
                    mainPresenter.refresh();
                }
            }
        });
        return v;
    }

    @Override
    public void UpdateView(int code, Object entity) {
        if(code == UserPresenter.UPDATE_USER_INFO){
            user = (UserEntity)entity;
            agetxt.setText(String.valueOf(user.Age));
            firstnametxt.setText(user.FirstName);
            lastnametxt.setText(user.LastName);
        }
        if(code == UserPresenter.UPDATE_STATISTIC){
            stat = (StatisticEntity)entity;
            heighttxt.setText(String.valueOf(stat.Height));
            weighttxt.setText(String.valueOf(stat.Weight));
        }
    }

    private void checkIfCanUpdate(){
        if(stat!=null && user!=null) {
            if (!heighttxt.getText().toString().isEmpty()
                    && !weighttxt.getText().toString().isEmpty()
                    && !lastnametxt.getText().toString().isEmpty()
                    && !firstnametxt.getText().toString().isEmpty()
                    && !agetxt.getText().toString().isEmpty()) {
                double hei = Double.parseDouble(heighttxt.getText().toString());
                double wei = Double.parseDouble(weighttxt.getText().toString());
                String lname = lastnametxt.getText().toString();
                String finame = firstnametxt.getText().toString();
                int age = Integer.parseInt(agetxt.getText().toString());
                if (
                        hei != stat.Height
                                || wei != stat.Weight
                                || !lname.equals(user.LastName)
                                || !finame.equals(user.FirstName)
                                || age != user.Age
                ) {
                    acceptbtn.setEnabled(true);
                    acceptbtn.setBackground(getResources().getDrawable(R.drawable.btn_intro));
                } else {
                    acceptbtn.setEnabled(false);
                    acceptbtn.setBackground(getResources().getDrawable(R.drawable.btn_disabled));
                }
            } else {
                acceptbtn.setEnabled(false);
                acceptbtn.setBackground(getResources().getDrawable(R.drawable.btn_disabled));
            }
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