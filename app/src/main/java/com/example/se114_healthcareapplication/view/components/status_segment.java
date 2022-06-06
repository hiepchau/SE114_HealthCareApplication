package com.example.se114_healthcareapplication.view.components;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentManager;
import com.example.se114_healthcareapplication.R;
import com.example.se114_healthcareapplication.generalinterfaces.IView;
import com.example.se114_healthcareapplication.model.entity.StatisticEntity;
import com.example.se114_healthcareapplication.presenter.StatusPresenter;
import com.hsalf.smilerating.SmileRating;
import com.hsalf.smileyrating.SmileyRating;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link status_segment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class status_segment extends Fragment implements IView<StatusPresenter> {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private EditText statusEdt;
    private Button confirmBtn;
    private SmileyRating ratingbar;
    StatusPresenter mainPresenter;

    public status_segment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment status_segment.
     */
    // TODO: Rename and change types and number of parameters
    public static status_segment newInstance(String param1, String param2) {
        status_segment fragment = new status_segment();
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
        View v = inflater.inflate(R.layout.fragment_status_segment, container, false);
        statusEdt = v.findViewById(R.id.Status_edt);
        confirmBtn = v.findViewById(R.id.btn_confirm);
        ratingbar = v.findViewById(R.id.emotion_ratingbar);

        setMainPresenter(new StatusPresenter(this));
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!statusEdt.getText().toString().isEmpty())
                    mainPresenter.UpdateStatus(statusEdt.getText().toString(),ratingbar.getSelectedSmiley().getRating());
            }
        });
        return v;
    }

    @Override
    public void UpdateView(int code, Object entity) {
        StatisticEntity statisticEntity = (StatisticEntity) entity;
        if(code == StatusPresenter.DONE_INIT_STATUS){
            statusEdt.setText(statisticEntity.Status);
            ratingbar.setRating(statisticEntity.EmotionalLevel,true);
        }
    }

    @Override
    public void SwitchView(int code) {

    }

    @Override
    public void setMainPresenter(StatusPresenter presenter) {
        this.mainPresenter = presenter;
    }

    @Override
    public StatusPresenter getMainpresnter() {
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