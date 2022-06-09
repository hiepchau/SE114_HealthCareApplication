package com.example.se114_healthcareapplication.view.components;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentManager;
import com.example.se114_healthcareapplication.R;
import com.example.se114_healthcareapplication.generalinterfaces.IView;
import com.example.se114_healthcareapplication.model.entity.StatisticEntity;
import com.example.se114_healthcareapplication.presenter.StatisticPresenter;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.TimeZone;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WalkChartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WalkChartFragment extends Fragment implements IView<StatisticPresenter> {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private StatisticPresenter mainPresenter;
    private Button backBtn;
    LineChart mchart;

    public WalkChartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WalkChartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WalkChartFragment newInstance(String param1, String param2) {
        WalkChartFragment fragment = new WalkChartFragment();
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
        View v = inflater.inflate(R.layout.fragment_walk_chart, container, false);

        mchart = v.findViewById(R.id.linechart);
        backBtn = v.findViewById(R.id.buttonturnback);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainPresenter.NotifyPresenter(StatisticPresenter.BACK_TO_MAIN_STATISTICS);
            }
        });
        setMainPresenter(new StatisticPresenter(this));
        return v;
    }

    @Override
    public void UpdateView(int code, Object entity) {
        if(code == StatisticPresenter.DATA_RETRIEVED){
            ArrayList<StatisticEntity> ls = (ArrayList<StatisticEntity>) entity;
            ArrayList<Entry> yvalues = new ArrayList<>();
            for(StatisticEntity en: ls){
                yvalues.add(new Entry(en.CreatedTime,en.Steps));
            }
            LineDataSet lineDataSet = new LineDataSet(yvalues,"Water amount (ml)");

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(lineDataSet);
            LineData line = new LineData(dataSets);
            mchart.setData(line);
            XAxis xAxis = mchart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setValueFormatter(new ValueFormatter() {
                @Override
                public String getAxisLabel(float value, AxisBase axis) {
                    String s="";
                    LocalDateTime localtime = LocalDateTime.ofInstant(Instant.ofEpochMilli((long)value), TimeZone.getDefault().toZoneId());
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
                    s+=formatter.format(localtime);
                    return s;
                }
            });
            line.notifyDataChanged();
            mchart.notifyDataSetChanged();
            mchart.invalidate();
        }
    }

    @Override
    public void SwitchView(int code) {

    }

    @Override
    public void setMainPresenter(StatisticPresenter presenter) {
        mainPresenter = presenter;
    }

    @Override
    public StatisticPresenter getMainpresnter() {
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