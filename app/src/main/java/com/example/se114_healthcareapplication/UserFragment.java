package com.example.se114_healthcareapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentManager;
import com.example.se114_healthcareapplication.generalinterfaces.IView;
import com.example.se114_healthcareapplication.model.StatisticModel;
import com.example.se114_healthcareapplication.model.entity.StatisticEntity;
import com.example.se114_healthcareapplication.model.entity.UserEntity;
import com.example.se114_healthcareapplication.presenter.MenuPresenter;
import com.example.se114_healthcareapplication.presenter.UserPresenter;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserFragment extends Fragment implements IView<UserPresenter> {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextView emailtxt, agetxt, weighttxt, heighttxt, gendertxt, nametxt;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private UserPresenter mainPresenter;
    private Button logoutbtn, managebtn;

    public UserFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment user.
     */
    // TODO: Rename and change types and number of parameters
    public static UserFragment newInstance(String param1, String param2) {
        UserFragment fragment = new UserFragment();
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
        View v = inflater.inflate(R.layout.fragment_user, container, false);
        emailtxt = v.findViewById(R.id.email_txv);
        agetxt = v.findViewById(R.id.age_txv);
        heighttxt = v.findViewById(R.id.height_txv);
        weighttxt = v.findViewById(R.id.weight_txv);
        gendertxt = v.findViewById(R.id.gender_txv);
        nametxt = v.findViewById(R.id.name_txv);
        managebtn = v.findViewById(R.id.btn_managedata);
        logoutbtn = v.findViewById(R.id.btn_logout);

        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainPresenter.NotifyPresenter(UserPresenter.LOGOUT);
            }
        });

        managebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainPresenter.NotifyPresenter(UserPresenter.SWITCH_TO_MANAGEDATA);
            }
        });
        setMainPresenter(new UserPresenter(this));
        emailtxt.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        return v;
    }

    @Override
    public void UpdateView(int code, Object entity) {
        if(code == UserPresenter.UPDATE_USER_INFO){
            UserEntity user = (UserEntity)entity;
            agetxt.setText(String.valueOf(user.Age));
            int tmpgen = user.Gender;
            if(tmpgen==0){
                gendertxt.setText("Female");
            }
            else {
                gendertxt.setText("Male");
            }
            nametxt.setText(user.FirstName+" "+ user.LastName);
        }
        if(code == UserPresenter.UPDATE_STATISTIC){
            StatisticEntity stat = (StatisticEntity)entity;
            heighttxt.setText(String.valueOf(stat.Height) + " cm");
            weighttxt.setText(String.valueOf(stat.Weight) + " kg");
        }
    }

    @Override
    public void SwitchView(int code) {

    }

    @Override
    public void setMainPresenter(UserPresenter presenter) {
        this.mainPresenter = presenter;
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