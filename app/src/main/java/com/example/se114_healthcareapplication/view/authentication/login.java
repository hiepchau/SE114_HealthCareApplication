package com.example.se114_healthcareapplication.view.authentication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
 * Use the {@link login#newInstance} factory method to
 * create an instance of this fragment.
 */
public class login extends Fragment implements IView<AuthenticatePresenter> {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Button LoginBtn, SignUpBtn;
    private ImageButton GoogleBtn;
    private EditText email,pass;
    private IPresenter mainPresenter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public login() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment login.
     */
    // TODO: Rename and change types and number of parameters
    public static login newInstance(String param1, String param2) {
        login fragment = new login();
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
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        LoginBtn = v.findViewById(R.id.btn_login);
        SignUpBtn = v.findViewById(R.id.btn_goto_signin);
        GoogleBtn = v.findViewById(R.id.Google_btn);
        email = v.findViewById(R.id.et_login_username);
        pass = v.findViewById(R.id.et_login_password);


        setMainPresenter(new AuthenticatePresenter(this));
        AuthenticatePresenter authen = (AuthenticatePresenter) mainPresenter;
        authen.checkSignedin();
        authen.registerGoogleAuthen();
        LoginBtn.setEnabled(false);
        LoginBtn.setBackground(getResources().getDrawable(R.drawable.btn_disabled));
        SignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwitchView(R.id.btn_goto_signin);
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
                checkCanLogin();
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
                checkCanLogin();
            }
        });

        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = email.getText().toString();
                String psw = pass.getText().toString();
                if(!mail.isEmpty() && !psw.isEmpty()){
                    AuthenticatePresenter authen = (AuthenticatePresenter) mainPresenter;
                    authen.Authenticate(mail,psw);
                }
            }
        });






        GoogleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authen.AuthenticateWithGoogle();
            }
        });

        return v;
    }



    @Override
    public void UpdateView(int code, Object entity) {
        if(code == EMPTY_CODE){
            email.setText("");
            pass.setText("");
        }
    }

    @Override
    public void SwitchView(int code) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (code){
            case R.id.btn_goto_signin:
                fragmentTransaction.replace(R.id.authenticateContainer, signup.class,null).addToBackStack("").commit();
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

    private void checkCanLogin(){
        if(!email.getText().toString().isEmpty()
          && !pass.getText().toString().isEmpty()){
            LoginBtn.setEnabled(true);
            LoginBtn.setBackground(getResources().getDrawable(R.drawable.btn_intro));
        }
        else {
            LoginBtn.setEnabled(false);
            LoginBtn.setBackground(getResources().getDrawable(R.drawable.btn_disabled));
        }
    }


}