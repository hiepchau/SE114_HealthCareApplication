package com.example.se114_healthcareapplication.view.components;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentManager;
import com.example.se114_healthcareapplication.R;
import com.example.se114_healthcareapplication.generalinterfaces.IView;
import com.example.se114_healthcareapplication.presenter.AuthenticatePresenter;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link notify_email_send#newInstance} factory method to
 * create an instance of this fragment.
 */
public class notify_email_send extends Fragment implements IView<AuthenticatePresenter> {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button backtologinbtn, resendbtn;
    private TextView announcetxv, timertxt;
    private AuthenticatePresenter mainPresenter;

    public notify_email_send() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment notify_email_send.
     */
    // TODO: Rename and change types and number of parameters
    public static notify_email_send newInstance(String param1, String param2) {
        notify_email_send fragment = new notify_email_send();
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
        View v = inflater.inflate(R.layout.fragment_notify_email_send, container, false);
        backtologinbtn = v.findViewById(R.id.btn_back_login);
        resendbtn = v.findViewById(R.id.btn_email_resend);
        announcetxv = v.findViewById(R.id.announcement_txv);
        timertxt = v.findViewById(R.id.timer_txv);
        CountDownTimer countDownTimer = new CountDownTimer(180000 + 1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long seconds = millisUntilFinished/1000;
                long min = seconds/60;
                long sec = seconds%60;
                String strmin = "0" + String.valueOf(min);
                String strsec = "";
                if(sec/10==0){
                    strsec+="0";
                }
                strsec += String.valueOf(sec);
                timertxt.setText(strmin+":"+strsec);
            }

            @Override
            public void onFinish() {
                resendbtn.setEnabled(true);
                resendbtn.setBackground(getResources().getDrawable(R.drawable.btn_normal));
            }
        }.start();

        announcetxv.setText("Welcome, we have sent a verification email to "+ FirebaseAuth.getInstance().getCurrentUser().getEmail()+
        ". Please check the spam if you couldn't find our mail in your main inboxes");

        resendbtn.setEnabled(false);
        resendbtn.setBackground(getResources().getDrawable(R.drawable.btn_disabled));
        resendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainPresenter.resenndEmail();
            }
        });

        setMainPresenter(new AuthenticatePresenter(this));
        backtologinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainPresenter.NotifyPresenter(AuthenticatePresenter.GO_TO_LOGIN);
            }
        });
        return v;
    }

    @Override
    public void UpdateView(int code, Object entity) {

    }

    @Override
    public void SwitchView(int code) {
        if(code == AuthenticatePresenter.EMAIL_RESENT){
            resendbtn.setEnabled(false);
            resendbtn.setBackground(getResources().getDrawable(R.drawable.btn_disabled));
            CountDownTimer countDownTimer = new CountDownTimer(180000 + 1000,1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    long seconds = millisUntilFinished/1000;
                    long min = seconds/60;
                    long sec = seconds%60;
                    String strmin = "0" + String.valueOf(min);
                    String strsec = "";
                    if(sec/10==0){
                        strsec+="0";
                    }
                    strsec += String.valueOf(sec);
                    timertxt.setText(strmin+":"+strsec);
                }

                @Override
                public void onFinish() {
                    resendbtn.setEnabled(true);
                    resendbtn.setBackground(getResources().getDrawable(R.drawable.btn_normal));
                }
            }.start();
        }
    }

    @Override
    public void setMainPresenter(AuthenticatePresenter presenter) {
        mainPresenter = presenter;
    }

    @Override
    public AuthenticatePresenter getMainpresnter() {
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