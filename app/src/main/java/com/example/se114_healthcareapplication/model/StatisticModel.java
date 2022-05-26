package com.example.se114_healthcareapplication.model;

import android.util.Log;
import androidx.annotation.NonNull;
import com.example.se114_healthcareapplication.generalinterfaces.IModel;
import com.example.se114_healthcareapplication.generalinterfaces.IPresenter;
import com.example.se114_healthcareapplication.model.entity.StatisticEntity;
import com.example.se114_healthcareapplication.model.entity.UserEntity;
import com.example.se114_healthcareapplication.water_segment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class StatisticModel extends ModelBase implements IModel<StatisticEntity> {

    ArrayList<StatisticEntity> statlist;
    FirebaseAuth auth;
    StatisticEntity currentEntity;
    double height, weight;
    int currentWater;
    int currentSteps;

    public StatisticModel(IPresenter presenter) {
        super(presenter);
        statlist = new ArrayList<>();
        auth = FirebaseAuth.getInstance();
        height =0;
        weight = 0;
        currentWater = 0;
        currentSteps = 0;
        getCurrentStatistic();
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
        if(entity!=null) {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(auth.getCurrentUser().getUid())
                    .child("Statistic")
                    .child(format.format(LocalDateTime.now()));
            ref.setValue(entity);
        }
    }
    public void UpdateWater(int amt){
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(auth.getCurrentUser().getUid())
                .child("Statistic")
                .child(format.format(LocalDateTime.now()))
                .child("Water");
        ref.setValue(amt);
    }

    public void UpdateSteps(int stp){
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(auth.getCurrentUser().getUid())
                .child("Statistic")
                .child(format.format(LocalDateTime.now()))
                .child("Steps");
        ref.setValue(stp);
    }

    public void UpdateHeight(double hei){
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(auth.getCurrentUser().getUid())
                .child("Statistic")
                .child(format.format(LocalDateTime.now()))
                .child("Height");
        ref.setValue(hei);
    }

    public void UpdateWeight(double wei){
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(auth.getCurrentUser().getUid())
                .child("Statistic")
                .child(format.format(LocalDateTime.now()))
                .child("Weight");
        ref.setValue(wei);
    }

    public void UpdateSleepTime(long slp){
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(auth.getCurrentUser().getUid())
                .child("Statistic")
                .child(format.format(LocalDateTime.now()))
                .child("SleepTime");
        ref.setValue(slp);
    }

    public void UpdateSleep(long slp){
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(auth.getCurrentUser().getUid())
                .child("Statistic")
                .child(format.format(LocalDateTime.now()))
                .child("Steps");
        ref.setValue(slp);
    }

    public void registerListenerForSteps(){
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(auth.getCurrentUser().getUid())
                .child("Statistic")
                .child(format.format(LocalDateTime.now()))
                .child("Steps");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(!snapshot.exists()){
                    getCurrentStatistic();
                    _presenter.NotifyPresenter(IPresenter.STEPS_COUNT_UPDATED);
                }
                else {
                    currentSteps = snapshot.getValue(int.class);
                    _presenter.NotifyPresenter(IPresenter.STEPS_COUNT_UPDATED);
                }
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    public int getCurrentSteps(){
        return currentSteps;
    }

    public StatisticEntity getCurrentStatistic() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(auth.getCurrentUser().getUid())
                .child("Statistic")
                .child(format.format(LocalDateTime.now()));
        Query query = FirebaseDatabase.getInstance().getReference().child(auth.getCurrentUser().getUid())
                .child("Statistic").orderByChild("CreatedTime").limitToLast(1);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for(DataSnapshot snap : snapshot.getChildren()){
                    height = snap.child("Height").getValue(double.class);
                    weight = snap.child("Weight").getValue(double.class);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                    currentEntity = new StatisticEntity(weight,height,0,0,0);
                    ref.setValue(currentEntity);
                } else {
                    double we = snapshot.child("Weight").getValue(double.class);
                    double he = snapshot.child("Height").getValue(double.class);
                    int water = snapshot.child("Water").getValue(int.class);
                    int steps = snapshot.child("Steps").getValue(int.class);
                    long sleep = snapshot.child("SleepTime").getValue(int.class);
                    currentEntity = new StatisticEntity(we,he,water,steps,sleep);
                    _presenter.NotifyPresenter(123);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                currentEntity = null;
            }
        };
        ref.addListenerForSingleValueEvent(listener);
        return currentEntity;
    }
}
