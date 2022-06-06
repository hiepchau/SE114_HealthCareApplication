package com.example.se114_healthcareapplication.presenter;

import android.content.Context;
import android.widget.Toast;
import com.example.se114_healthcareapplication.generalinterfaces.IPresenter;
import com.example.se114_healthcareapplication.generalinterfaces.IView;
import com.example.se114_healthcareapplication.model.StatisticModel;
import com.example.se114_healthcareapplication.model.entity.StatisticEntity;

public class StatusPresenter extends PresenterBase implements IPresenter {
    StatisticModel statisticModel;
    public static final int DONE_INIT_STATUS = 312893;
    public StatisticEntity statisticEntity;
    public StatusPresenter(IView view) {
        super(view);
        statisticModel = new StatisticModel(this);
    }

    @Override
    public void NotifyPresenter(int code) {
        if(code == StatisticModel.DONE_INIT_DATA){
            statisticEntity = statisticModel.currentEntity;
            _view.UpdateView(DONE_INIT_STATUS,statisticEntity);
        }
    }

    public void UpdateStatus(String s,int emo){
        if(!s.equals(statisticEntity.Status)
        || !(emo==statisticEntity.EmotionalLevel))
            statisticModel.UpdateStatusEmo(s,emo);
    }

    @Override
    public Context getCurrentContext() {
        return _view.getAppActivity().getApplicationContext();
    }
}
