package com.example.se114_healthcareapplication.view.authentication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.*;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.se114_healthcareapplication.R;
import com.example.se114_healthcareapplication.generalinterfaces.IPresenter;
import com.example.se114_healthcareapplication.generalinterfaces.IView;
import com.example.se114_healthcareapplication.presenter.AuthenticatePresenter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link signup#newInstance} factory method to
 * create an instance of this fragment.
 */
public class signup extends Fragment implements IView<AuthenticatePresenter> {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final int SIGNUP_FAILED = 239841;
    private IPresenter mainPresenter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button SigninBtn, BackBtn;
    private EditText email,pass,repass,firstname,age,height,weight,lastnameedt;
    TextView error_text;
    RadioButton malebtn, femalebtn;

    public signup() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment signup.
     */
    // TODO: Rename and change types and number of parameters
    public static signup newInstance(String param1, String param2) {
        signup fragment = new signup();
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
        View v = inflater.inflate(R.layout.fragment_signup, container, false);
        setMainPresenter(new AuthenticatePresenter(this));
        SigninBtn = v.findViewById(R.id.btn_signup);
        BackBtn = v.findViewById(R.id.btn_back_login);
        email = v.findViewById(R.id.register_mail_edt);
        pass = v.findViewById(R.id.register_pass_edt);
        repass = v.findViewById(R.id.register_repass_edt);
        firstname = v.findViewById(R.id.firstname_edt);
        age = v.findViewById(R.id.age_edt);
        height = v.findViewById(R.id.height_edt);
        weight = v.findViewById(R.id.weight_edt);
        lastnameedt = v.findViewById(R.id.lastname_edt);
        malebtn = v.findViewById(R.id.male_rdo);
        femalebtn = v.findViewById(R.id.female_rdo);
        error_text = v.findViewById(R.id.error_stat);
        SigninBtn.setEnabled(false);
        SigninBtn.setBackground(getResources().getDrawable(R.drawable.btn_disabled));
        setMainPresenter(new AuthenticatePresenter(this));

        age.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkDisableSignUp();
            }
        });
        repass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!repass.getText().toString().equals(pass.getText().toString())){
                    error_text.setVisibility(View.VISIBLE);
                }
                else {
                    error_text.setVisibility(View.INVISIBLE);
                }
                checkDisableSignUp();
            }
        });

        pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkDisableSignUp();
            }
        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkDisableSignUp();
            }
        });

        firstname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkDisableSignUp();
            }
        });

        lastnameedt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkDisableSignUp();
            }
        });

        height.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkDisableSignUp();
            }
        });

        weight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkDisableSignUp();
            }
        });

        malebtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(malebtn.isChecked()){
                    femalebtn.setChecked(false);
                }
                checkDisableSignUp();
            }
        });

        femalebtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(femalebtn.isChecked())
                    malebtn.setChecked(false);
                checkDisableSignUp();
            }
        });


        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainPresenter.NotifyPresenter(R.id.btn_goto_signin);
            }
        });

        SigninBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthenticatePresenter authen = (AuthenticatePresenter) mainPresenter;
                String fname = firstname.getText().toString();
                String lname = lastnameedt.getText().toString();
                int tAge = Integer.parseInt(age.getText().toString());
                double tHeight = Double.parseDouble(height.getText().toString());
                double tWeight = Double.parseDouble(weight.getText().toString());
                int gender = 0;
                if(malebtn.isChecked()){
                    gender =1;
                }
                SigninBtn.setEnabled(false);
                authen.SignUp(email.getText().toString(),pass.getText().toString(),repass.getText().toString(),fname,lname,tAge,tHeight,tWeight,gender);
            }
        });

        return v;
    }

    @Override
    public void UpdateView(int code, Object entity) {
        if(code == EMPTY_CODE){
            email.setText("");
            pass.setText("");
            repass.setText("");
            firstname.setText("");
            lastnameedt.setText("");
            age.setText("");
            height.setText("");
            weight.setText("");
            malebtn.setChecked(false);
            femalebtn.setChecked(false);
            checkDisableSignUp();
        }
    }

    @Override
    public void SwitchView(int code) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (code){
            case R.id.btn_goto_signin:
                fragmentTransaction.replace(R.id.authenticateContainer, login.class,null).addToBackStack("").commit();
                break;
            case SIGNUP_FAILED:
                checkDisableSignUp();
                break;
        }
    }

    @Override
    public void setMainPresenter(AuthenticatePresenter presenter) {
        mainPresenter = presenter;
    }

    @Override
    public AuthenticatePresenter getMainpresnter() {
        return (AuthenticatePresenter) mainPresenter;
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

    private void checkDisableSignUp(){
        if(!email.getText().toString().isEmpty()
        && !pass.getText().toString().isEmpty()
        && !repass.getText().toString().isEmpty()
        && !firstname.getText().toString().isEmpty()
        && !lastnameedt.getText().toString().isEmpty()
        && !height.getText().toString().isEmpty()
        && !weight.getText().toString().isEmpty()
        && (malebtn.isChecked() || femalebtn.isChecked())){
            SigninBtn.setEnabled(true);
            SigninBtn.setBackground(getResources().getDrawable(R.drawable.btn_intro));
        }
        else {
            SigninBtn.setEnabled(false);
            SigninBtn.setBackground(getResources().getDrawable(R.drawable.btn_disabled));
        }
    }
}