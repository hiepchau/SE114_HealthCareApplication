package com.example.se114_healthcareapplication.presenter;

import android.content.Context;
import android.widget.Toast;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.se114_healthcareapplication.R;
import com.example.se114_healthcareapplication.generalinterfaces.IPresenter;
import com.example.se114_healthcareapplication.generalinterfaces.IView;
import com.example.se114_healthcareapplication.model.StatisticModel;
import com.example.se114_healthcareapplication.model.entity.StatisticEntity;
import com.example.se114_healthcareapplication.view.MenuFragment;
import com.example.se114_healthcareapplication.view.bottom_nav.HomeFragment;

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
        if(code == BACK_ON_MENU){
            FragmentManager fragmentManager = _view.GetFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer_function, MenuFragment.class,null).addToBackStack("").commit();
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
