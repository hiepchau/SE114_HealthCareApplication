package com.example.se114_healthcareapplication.presenter;

import android.content.Context;
import com.example.se114_healthcareapplication.view.components.bmi_segment;
import com.example.se114_healthcareapplication.generalinterfaces.IPresenter;
import com.example.se114_healthcareapplication.generalinterfaces.IView;
import com.example.se114_healthcareapplication.model.StatisticModel;

import java.util.ArrayList;
import java.util.List;

public class BMIPresenter extends PresenterBase implements IPresenter {
    private double Weight, Height;
    List<Double> bmilist;
    private StatisticModel statisticModel;
    public BMIPresenter(IView view) {
        super(view);
        Weight = 0;
        Height = 0;
        bmilist = new ArrayList<>();
        statisticModel = new StatisticModel(this);
    }

    @Override
    public void NotifyPresenter(int code) {
        if(code == StatisticModel.DONE_INIT_DATA){
            Weight = statisticModel.currentEntity.Weight;
            Height = statisticModel.currentEntity.Height;
            bmilist.add(Weight);
            bmilist.add(Height);
            _view.UpdateView(bmi_segment.UPDATE_BMI,bmilist);
        }
    }

    @Override
    public Context getCurrentContext() {
        return _view.getAppActivity();
    }
}
