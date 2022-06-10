package com.example.se114_healthcareapplication.view.components;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.*;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentManager;
import com.example.se114_healthcareapplication.R;
import com.example.se114_healthcareapplication.custom.RunningEntityAdapter;
import com.example.se114_healthcareapplication.generalinterfaces.IView;
import com.example.se114_healthcareapplication.model.entity.RunningEntity;
import com.example.se114_healthcareapplication.presenter.GoogleMapPresenter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link list_run#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class list_run extends Fragment implements IView<GoogleMapPresenter> {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private GoogleMapPresenter mainPresenter;
    private Button backbtn;
    private RunningEntityAdapter adapter;
    private ListView runninglistview;
    private TextView furthesttxt;
    ArrayList<RunningEntity> runningEntityList;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment list_run.
     */
    // TODO: Rename and change types and number of parameters
    public static list_run newInstance(String param1, String param2) {
        list_run fragment = new list_run();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public list_run() {
        // Required empty public constructor
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
        View v = inflater.inflate(R.layout.fragment_list_run, container, false);
        runningEntityList = new ArrayList<>();
        runninglistview = v.findViewById(R.id.listRun);
        adapter = new RunningEntityAdapter(getActivity(),runningEntityList);
        backbtn = v.findViewById(R.id.buttonturnback);
        furthesttxt = v.findViewById(R.id.run_record_txv);
        runninglistview.setAdapter(adapter);
        setMainPresenter(new GoogleMapPresenter(this));
        mainPresenter.getRunningLimit6(System.currentTimeMillis());
        mainPresenter.triggerGetFurthest();
        runninglistview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE
                        && (runninglistview.getLastVisiblePosition() - runninglistview.getHeaderViewsCount() -
                        runninglistview.getFooterViewsCount()) >= (adapter.getCount() - 1)) {

                    mainPresenter.getRunningLimit6(runningEntityList.get((int)runningEntityList.stream().count()-1).createdTime);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainPresenter.NotifyPresenter(GoogleMapPresenter.BACK_TO_RUNNING);
            }
        });
        return v;
    }


    @Override
    public void UpdateView(int code, Object entity) {
        if(code == GoogleMapPresenter.UPDATE_LIST_RUN){
            ArrayList<RunningEntity> ls = (ArrayList<RunningEntity>)entity;
            adapter.setRunningEntityList(ls);
            adapter.notifyDataSetChanged();
        }
        if(code == GoogleMapPresenter.APPEND_LIST_RUN){
            ArrayList<RunningEntity> ls = (ArrayList<RunningEntity>)entity;
            runningEntityList.addAll(ls);
            adapter.setRunningEntityList(runningEntityList);
            adapter.notifyDataSetChanged();
        }
        if(code == GoogleMapPresenter.UPDATE_FURTHEST_DISTANCE){
            float dis = (float) entity;
            DecimalFormat decimalFormat = new DecimalFormat("0.00");
            furthesttxt.setText(decimalFormat.format(dis)+" m");
        }
    }

    @Override
    public void SwitchView(int code) {

    }

    @Override
    public void setMainPresenter(GoogleMapPresenter presenter) {
        this.mainPresenter = presenter;
    }

    @Override
    public GoogleMapPresenter getMainpresnter() {
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