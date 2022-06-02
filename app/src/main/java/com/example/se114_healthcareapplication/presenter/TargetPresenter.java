package com.example.se114_healthcareapplication.presenter;

import android.content.Context;
import com.example.se114_healthcareapplication.generalinterfaces.IPresenter;
import com.example.se114_healthcareapplication.generalinterfaces.IView;
import com.example.se114_healthcareapplication.model.StatisticModel;
import com.example.se114_healthcareapplication.model.UserModel;
import com.example.se114_healthcareapplication.model.entity.StatisticEntity;
import com.example.se114_healthcareapplication.model.entity.UserEntity;

public class TargetPresenter extends PresenterBase implements IPresenter {
    public static final int UPDATE_DAILY_STATISTIC = 128379;
    public static final int UPDATE_USER_INFO = 1283812;
    private UserModel userModel;
    private StatisticModel statisticModel;
    UserEntity userEntity;
    StatisticEntity statisticEntity;
    public TargetPresenter(IView view) {

        super(view);
        userModel = new UserModel(this);
        userModel.getCurrentUser();

    }

    @Override
    public void NotifyPresenter(int code) {
        if(code == StatisticModel.DONE_INIT_DATA){
            statisticEntity = statisticModel.currentEntity;
            _view.UpdateView(UPDATE_DAILY_STATISTIC,statisticEntity);
        }
        if(code == UserModel.RETRIEVE_USER_SUCCESS){
            userEntity = userModel.currentUser;
            statisticModel = new StatisticModel(this);
            _view.UpdateView(UPDATE_USER_INFO, userEntity);
        }
    }

    @Override
    public Context getCurrentContext() {
        return _view.getAppActivity();
    }
    public void getSpecificData(String str){
        statisticModel.getStatisticFromPath(str);
    }
}
