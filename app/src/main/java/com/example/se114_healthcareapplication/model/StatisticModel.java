package com.example.se114_healthcareapplication.model;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.example.se114_healthcareapplication.generalinterfaces.IModel;
import com.example.se114_healthcareapplication.generalinterfaces.IPresenter;
import com.example.se114_healthcareapplication.model.entity.StatisticEntity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class StatisticModel extends ModelBase implements IModel<StatisticEntity> {

    class DayChangedReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(Intent.ACTION_DATE_CHANGED))
            {
                getCurrentStatistic();
                Toast.makeText(_presenter.getCurrentContext(), "day changed",Toast.LENGTH_SHORT).show();
            }
        }
    }

    public ArrayList<StatisticEntity> statlist;
    FirebaseAuth auth;
    public StatisticEntity currentEntity;
    public static int DONE_INIT_DATA = 123;
    public static int DONE_RETRIEVE_WEEK_LIST = 12093;
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
        DayChangedReceiver receiver = new DayChangedReceiver();
        _presenter.getCurrentContext().registerReceiver(receiver,new IntentFilter(Intent.ACTION_DATE_CHANGED));
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

    public void UpdateStatus(String str){
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(auth.getCurrentUser().getUid())
                .child("Statistic")
                .child(format.format(LocalDateTime.now()))
                .child("Status");
        ref.setValue(str);
    }

    public void UpdateEmotionalLevel(int lel){
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(auth.getCurrentUser().getUid())
                .child("Statistic")
                .child(format.format(LocalDateTime.now()))
                .child("EmotionalLevel");
        ref.setValue(lel);

    }

    public void UpdateStatusEmo(String str, int emo){
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(auth.getCurrentUser().getUid())
                .child("Statistic")
                .child(format.format(LocalDateTime.now()))
                .child("Status");
        ref.setValue(str).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                DatabaseReference refemo = FirebaseDatabase.getInstance().getReference().child(auth.getCurrentUser().getUid())
                        .child("Statistic")
                        .child(format.format(LocalDateTime.now()))
                        .child("EmotionalLevel");
                refemo.setValue(emo).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(_presenter.getCurrentContext(), "Status updated!",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
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
                    ValueEventListener listener = new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            if (!snapshot.exists()) {
                                currentEntity = new StatisticEntity(height,weight,0,0,0,3,"");
                                ref.setValue(currentEntity);
                            } else {
                                double we = snapshot.child("Weight").getValue(double.class);
                                double he = snapshot.child("Height").getValue(double.class);
                                int water = snapshot.child("Water").getValue(int.class);
                                int steps = snapshot.child("Steps").getValue(int.class);
                                long sleep = snapshot.child("SleepTime").getValue(int.class);
                                int emo = snapshot.child("EmotionalLevel").getValue(int.class);
                                String sta = snapshot.child("Status").getValue(String.class);
                                currentEntity = new StatisticEntity(he,we,water,steps,sleep,emo,sta);
                                _presenter.NotifyPresenter(DONE_INIT_DATA);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {
                            currentEntity = null;
                        }
                    };
                    ref.addValueEventListener(listener);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        return currentEntity;
    }

    public void getStatisticFromPath(String pth){
        DatabaseReference tmpref = FirebaseDatabase.getInstance().getReference().child(auth.getCurrentUser().getUid())
                .child("Statistic")
                .child(pth);

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                    currentEntity = null;
                    _presenter.NotifyPresenter(DONE_INIT_DATA);
                } else {
                    double we = snapshot.child("Weight").getValue(double.class);
                    double he = snapshot.child("Height").getValue(double.class);
                    int water = snapshot.child("Water").getValue(int.class);
                    int steps = snapshot.child("Steps").getValue(int.class);
                    long sleep = snapshot.child("SleepTime").getValue(int.class);
                    int emo = snapshot.child("EmotionalLevel").getValue(int.class);
                    String sta = snapshot.child("Status").getValue(String.class);
                    currentEntity = new StatisticEntity(he,we,water,steps,sleep,emo,sta);
                    _presenter.NotifyPresenter(DONE_INIT_DATA);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                currentEntity = null;
            }
        };
        tmpref.addListenerForSingleValueEvent(listener);

    }

    public void getLastWeekStatistic(){
        statlist.clear();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        Query query = FirebaseDatabase.getInstance().getReference().child(auth.getCurrentUser().getUid())
                .child("Statistic").orderByChild("CreatedTime").endAt(calendar.getTimeInMillis())
                .limitToLast(7);

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    for(DataSnapshot snap:snapshot.getChildren()) {
                        double we = snap.child("Weight").getValue(double.class);
                        double he = snap.child("Height").getValue(double.class);
                        int water = snap.child("Water").getValue(int.class);
                        int steps = snap.child("Steps").getValue(int.class);
                        long sleep = snap.child("SleepTime").getValue(int.class);
                        int emo = snap.child("EmotionalLevel").getValue(int.class);
                        long cre = snap.child("CreatedTime").getValue(long.class);
                        String sta = snap.child("Status").getValue(String.class);
                        StatisticEntity entity = new StatisticEntity(he, we, water, steps, sleep, emo, sta,cre);
                        statlist.add(entity);
                    }
                    _presenter.NotifyPresenter(DONE_RETRIEVE_WEEK_LIST);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                _presenter.NotifyPresenter(DONE_RETRIEVE_WEEK_LIST);
            }
        };
        query.addListenerForSingleValueEvent(listener);
    }


}
