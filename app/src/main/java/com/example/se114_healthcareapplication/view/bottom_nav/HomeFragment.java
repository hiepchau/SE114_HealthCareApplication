package com.example.se114_healthcareapplication.view.bottom_nav;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentManager;
import com.example.se114_healthcareapplication.R;
import com.example.se114_healthcareapplication.generalinterfaces.IView;
import com.example.se114_healthcareapplication.model.StatisticModel;
import com.example.se114_healthcareapplication.model.entity.StatisticEntity;
import com.example.se114_healthcareapplication.model.entity.UserEntity;
import com.example.se114_healthcareapplication.presenter.HomePresenter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements IView<HomePresenter> {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private HomePresenter mainPresenter;
    private ImageView avtImage;
    private TextView avtText,username,date,statustxt;
    public static final int UPDATE_AVATAR = 932845;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        avtImage = v.findViewById(R.id.profile_image);
        avtText = v.findViewById(R.id.avt_txt);
        username = v.findViewById(R.id.user_name);
        date = v.findViewById(R.id.Datetime);
        DateTimeFormatter format = DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy");
        date.setText(format.format(LocalDateTime.now()));
        statustxt = v.findViewById(R.id.statustext);
        setMainPresenter(new HomePresenter(this));
        avtText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainPresenter.changeAvatar();
            }
        });
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mainPresenter.getAvatar()!=null){
            avtImage.setImageBitmap(mainPresenter.getAvatar());
        }
    }

    @Override
    public void UpdateView(int code, Object entity) {
        if(code == UPDATE_AVATAR){
            avtImage.setImageBitmap((Bitmap) entity);
        }
        if(code == HomePresenter.UPDATE_USER_INFO){
            UserEntity user = (UserEntity) entity;
            username.setText("Hello, "+user.FirstName);
        }
        if(code == HomePresenter.UPDATE_STATISTIC){
            StatisticEntity statisticEntity = (StatisticEntity) entity;
            statustxt.setText(statisticEntity.Status);
        }
    }

    @Override
    public void SwitchView(int code) {

    }

    @Override
    public void setMainPresenter(HomePresenter presenter) {
        this.mainPresenter = presenter;
    }

    @Override
    public HomePresenter getMainpresnter() {
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