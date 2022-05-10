package com.example.se114_healthcareapplication;

import android.os.Bundle;

import android.widget.Button;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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
    private IPresenter mainPresenter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button SigninBtn, BackBtn;

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
        SigninBtn = v.findViewById(R.id.btn_back_login);
        BackBtn = v.findViewById(R.id.btn_back_login);
        SigninBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainPresenter.NotifyPresenter(R.id.btn_goto_signin);
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
        switch (code){
            case R.id.btn_goto_signin:
                fragmentTransaction.replace(R.id.authenticateContainer,login.class,null).commit();
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
}