package com.example.se114_healthcareapplication.presenter;

import android.content.Context;
import androidx.fragment.app.FragmentManager;
import com.example.se114_healthcareapplication.R;
import com.example.se114_healthcareapplication.generalinterfaces.IPresenter;
import com.example.se114_healthcareapplication.generalinterfaces.IView;
import com.example.se114_healthcareapplication.model.StatisticModel;
import com.example.se114_healthcareapplication.model.UserModel;
import com.example.se114_healthcareapplication.view.bottom_nav.StatisticsFragment;
import com.example.se114_healthcareapplication.view.components.EmotionalChartFragment;
import com.example.se114_healthcareapplication.view.components.SleepChartFragment;
import com.example.se114_healthcareapplication.view.components.WalkChartFragment;
import com.example.se114_healthcareapplication.view.components.WaterChartFragment;

public class StatisticPresenter extends PresenterBase implements IPresenter {
    StatisticModel statisticModel;
    UserModel userModel;
    public static final int USER_RETRIEVED = 19237;
    public static final int DATA_RETRIEVED = 182379;
    public static final int SWITCH_TO_WATER =21234;
    public static final int SWITCH_TO_STEPS =9128347;
    public static final int SWITCH_TO_EMOTION =947;
    public static final int SWITCH_TO_SLEEP =182371;
    public static final int BACK_TO_MAIN_STATISTICS = 195341;
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

        if(code == SWITCH_TO_STEPS){
            FragmentManager manager = _view.GetFragmentManager();
            manager.beginTransaction().replace(R.id.fragmentContainer_homeactivity, new WalkChartFragment()).addToBackStack("").commit();
        }

        if(code == SWITCH_TO_SLEEP){
            FragmentManager manager = _view.GetFragmentManager();
            manager.beginTransaction().replace(R.id.fragmentContainer_homeactivity, new SleepChartFragment()).addToBackStack("").commit();
        }

        if(code == BACK_TO_MAIN_STATISTICS){
            FragmentManager manager = _view.GetFragmentManager();
            manager.beginTransaction().replace(R.id.fragmentContainer_homeactivity, new StatisticsFragment()).addToBackStack("").commit();
        }
        if(code == SWITCH_TO_EMOTION){
            FragmentManager manager = _view.GetFragmentManager();
            manager.beginTransaction().replace(R.id.fragmentContainer_homeactivity, new EmotionalChartFragment()).addToBackStack("").commit();
        }
    }

    @Override
    public Context getCurrentContext() {
        return _view.getAppActivity();
    }
}
