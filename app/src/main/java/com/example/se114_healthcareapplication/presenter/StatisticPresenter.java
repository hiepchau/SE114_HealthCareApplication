package com.example.se114_healthcareapplication.presenter;

import android.content.Context;
import androidx.fragment.app.FragmentManager;
import com.example.se114_healthcareapplication.R;
import com.example.se114_healthcareapplication.generalinterfaces.IPresenter;
import com.example.se114_healthcareapplication.generalinterfaces.IView;
import com.example.se114_healthcareapplication.model.StatisticModel;
import com.example.se114_healthcareapplication.model.UserModel;
import com.example.se114_healthcareapplication.view.components.WaterChartFragment;

public class StatisticPresenter extends PresenterBase implements IPresenter {
    StatisticModel statisticModel;
    UserModel userModel;
    public static final int USER_RETRIEVED = 19237;
    public static final int DATA_RETRIEVED = 182379;
    public static final int SWITCH_TO_WATER =21234;
    public StatisticPresenter(IView view) {
        super(view);
        userModel = new UserModel(this);
        userModel.getCurrentUser();
        statisticModel = new StatisticModel(this);
    }

    @Override
    public void NotifyPresenter(int code) {
        if(code == StatisticModel.DONE_RETRIEVE_WEEK_LIST){
            _view.UpdateView(DATA_RETRIEVED,statisticModel.statlist);
        }

        if(code == UserModel.RETRIEVE_USER_SUCCESS){
            statisticModel.getLastWeekStatistic();
            _view.UpdateView(USER_RETRIEVED,userModel.currentUser);
        }
        if(code == SWITCH_TO_WATER){
            FragmentManager manager = _view.GetFragmentManager();
            manager.beginTransaction().replace(R.id.fragmentContainer_homeactivity, new WaterChartFragment()).addToBackStack("").commit();
        }
    }

    @Override
    public Context getCurrentContext() {
        return _view.getAppActivity();
    }
}
