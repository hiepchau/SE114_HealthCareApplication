package com.example.se114_healthcareapplication.presenter;

import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.example.se114_healthcareapplication.generalinterfaces.IPresenter;
import com.example.se114_healthcareapplication.generalinterfaces.IView;
import com.example.se114_healthcareapplication.model.StatisticModel;
import com.example.se114_healthcareapplication.model.entity.StatisticEntity;
import com.example.se114_healthcareapplication.water_segment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WaterPresenter extends PresenterBase implements IPresenter {
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    private StatisticModel statisticModel;
    public StatisticEntity initEntity;
    public static final int GET_CURRENT_WATER = 100;
    public WaterPresenter(IView view) {

        super(view);
        statisticModel = new StatisticModel(this);
        initEntity = statisticModel.getCurrentStatistic();
        if(initEntity == null);
    }

    @Override
    public void NotifyPresenter(int code) {
        if(code==123){
            _view.UpdateView(water_segment.UPDATE_COMPLETE,statisticModel.getCurrentStatistic().Water);
        }
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
