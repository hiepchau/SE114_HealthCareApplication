package com.example.se114_healthcareapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentManager;
import com.example.se114_healthcareapplication.generalinterfaces.IView;
import com.example.se114_healthcareapplication.presenter.StepsCountPresenter;
import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link step_segment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class step_segment extends Fragment implements IView<StepsCountPresenter> {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private StepsCountPresenter mainPresenter;
    private TextView completetxt, percent;
    public static int UPDATE_STEPS = 1;
    public static int UPDATE_PERCENT = 2;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public step_segment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment step_segment.
     */
    // TODO: Rename and change types and number of parameters
    public static step_segment newInstance(String param1, String param2) {
        step_segment fragment = new step_segment();
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
        View view = inflater.inflate(R.layout.fragment_step_segment, container, false);

        completetxt = view.findViewById(R.id.CompleteValue);
        percent = view.findViewById(R.id.percentage);

        setMainPresenter(new StepsCountPresenter(this));
        return view;
    }

    @Override
    public void UpdateView(int code, Object entity) {
        if(code == UPDATE_STEPS){
            int tmp = (int)entity;
            completetxt.setText(String.valueOf(tmp)+" steps");
        }
        if(code== UPDATE_PERCENT){
            float tmp =(float) entity;
            percent.setText(String.valueOf(tmp)+"%");
        }
    }

    @Override
    public void SwitchView(int code) {

    }

    @Override
    public void setMainPresenter(StepsCountPresenter presenter) {
        this.mainPresenter = presenter;
    }

    @Override
    public StepsCountPresenter getMainpresnter() {
        return mainPresenter;
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
}