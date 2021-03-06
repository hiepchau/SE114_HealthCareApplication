package com.example.se114_healthcareapplication.presenter;

import android.content.Context;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.se114_healthcareapplication.R;
import com.example.se114_healthcareapplication.generalinterfaces.IPresenter;
import com.example.se114_healthcareapplication.generalinterfaces.IView;
import com.example.se114_healthcareapplication.model.StatisticModel;
import com.example.se114_healthcareapplication.model.UserModel;
import com.example.se114_healthcareapplication.model.entity.StatisticEntity;
import com.example.se114_healthcareapplication.model.entity.UserEntity;
import com.example.se114_healthcareapplication.view.MenuFragment;
import com.example.se114_healthcareapplication.view.bottom_nav.HomeFragment;
import com.example.se114_healthcareapplication.view.components.status_segment;
import com.example.se114_healthcareapplication.view.components.water_segment;
import com.google.firebase.database.*;

public class WaterPresenter extends PresenterBase implements IPresenter {
    private StatisticModel statisticModel;
    public StatisticEntity initEntity;
    public UserModel userModel;
    public static final int UPDATE_DAILY_GOAL=192834;
    public WaterPresenter(IView view) {

        super(view);
        statisticModel = new StatisticModel(this);
        initEntity = statisticModel.getCurrentStatistic();
        userModel = new UserModel(this);
        userModel.getCurrentUser();
    }

    @Override
    public void NotifyPresenter(int code) {
        if(code==123){
            _view.UpdateView(water_segment.UPDATE_COMPLETE,statisticModel.currentEntity.Water);
        }
        if(code == UserModel.RETRIEVE_USER_SUCCESS){
            _view.UpdateView(UPDATE_DAILY_GOAL,userModel.currentUser.Gender);
        }
        if(code == BACK_ON_MENU){
            FragmentManager fragmentManager = _view.GetFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer_function, MenuFragment.class,null).addToBackStack("").commit();
        }
    }

    @Override
    public Context getCurrentContext() {
        return _view.getAppActivity();
    }

    public void addWater(int amt){
        StatisticEntity entity = statisticModel.getCurrentStatistic();
        if(entity!= null) {
                int amtupdate = entity.Water+amt;
                statisticModel.UpdateWater(amtupdate);
                _view.UpdateView(water_segment.UPDATE_COMPLETE,amtupdate);
        }
    }
}
