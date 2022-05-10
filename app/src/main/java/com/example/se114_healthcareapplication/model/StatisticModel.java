package com.example.se114_healthcareapplication.model;

import com.example.se114_healthcareapplication.generalinterfaces.IModel;
import com.example.se114_healthcareapplication.generalinterfaces.IPresenter;
import com.example.se114_healthcareapplication.model.entity.StatisticEntity;

import java.util.ArrayList;
import java.util.List;

public class StatisticModel extends ModelBase implements IModel<StatisticEntity> {

    ArrayList<StatisticEntity> statlist;
    public StatisticModel(IPresenter presenter){
        super(presenter);
        statlist = new ArrayList<>();
    }
    @Override
    public List<StatisticEntity> getEntity() {
        return null;
    }

    @Override
    public void NotifyModel(int code) {

    }

    @Override
    public void UpdateDatabase(StatisticEntity entity) {

    }
}
