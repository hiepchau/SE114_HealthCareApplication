package com.example.se114_healthcareapplication.view.authentication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.se114_healthcareapplication.R;
import com.example.se114_healthcareapplication.generalinterfaces.IView;
import com.example.se114_healthcareapplication.presenter.AuthenticatePresenter;
import com.example.se114_healthcareapplication.presenter.UserPresenter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link resetpassword#newInstance} factory method to
 * create an instance of this fragment.
 */
public class resetpassword extends Fragment  implements IView<UserPresenter> {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button backbtn, resetpassbtn;
    TextView state;
    UserPresenter mainPresenter;


    public resetpassword() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment resetpassword.
     */
    // TODO: Rename and change types and number of parameters
    public static resetpassword newInstance(String param1, String param2) {
        resetpassword fragment = new resetpassword();
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
        View v = inflater.inflate(R.layout.fragment_resetpassword, container, false);
        backbtn = v.findViewById(R.id.buttonturnback);
        resetpassbtn = v.findViewById(R.id.btn_resetpass);
        state = v.findViewById(R.id.resetstate_txv);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainPresenter.NotifyPresenter(UserPresenter.SWITCH_TO_MANAGEDATA);
            }
        });

        resetpassbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainPresenter.ResetPassword();
            }
        });
        setMainPresenter(new UserPresenter(this));
        return v;
    }


    @Override
    public void UpdateView(int code, Object entity) {
        if(code == UserPresenter.RESET_PASSWORD_MAIL_FAILED){
            state.setText(entity.toString());
        }
    }

    @Override
    public void SwitchView(int code) {
        if(code == UserPresenter.RESET_PASSWORD_MAIL_SENT){
            state.setText("We have sent a password reset email to your email address. Please have a look at your inboxes including spams.");
        }
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