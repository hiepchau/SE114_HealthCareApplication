package com.example.se114_healthcareapplication.model;

import android.widget.Toast;
import androidx.annotation.NonNull;
import com.example.se114_healthcareapplication.generalinterfaces.IModel;
import com.example.se114_healthcareapplication.generalinterfaces.IPresenter;
import com.example.se114_healthcareapplication.model.ModelBase;
import com.example.se114_healthcareapplication.model.entity.RunningEntity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RunningModel extends ModelBase implements IModel<RunningEntity> {
    FirebaseAuth auth;
    DatabaseReference ref;
    final String path = "RunningStat";
    public static final int RUNNING_ENTITY_RETRIEVED = 91283;
    public static final int RUNNIG_ENTITY_APPEND = 1921321;
    List<RunningEntity> runningEntityList;

    public RunningModel(IPresenter _presenter) {
        super(_presenter);
        auth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference().child(auth.getCurrentUser().getUid())
                .child(path);
        runningEntityList = new ArrayList<>();
    }

    @Override
    public List<RunningEntity> getEntity() {
        return runningEntityList;
    }

    @Override
    public void NotifyModel(int code) {

    }

    @Override
    public void UpdateDatabase(RunningEntity entity) {
        ref.child(String.valueOf(System.currentTimeMillis())).setValue(entity);
    }

    public void retrieveRunningData(){
        runningEntityList.clear();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for (DataSnapshot running:snapshot.getChildren()){
                        float di = running.child("distance").getValue(float.class);
                        long ti = running.child("time").getValue(long.class);
                        long cre = running.child("createdTime").getValue(long.class);
                        RunningEntity tmp = new RunningEntity(di,ti,cre);
                        runningEntityList.add(tmp);
                    }
                }
                Collections.reverse(runningEntityList);
                _presenter.NotifyPresenter(RUNNING_ENTITY_RETRIEVED);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(_presenter.getCurrentContext(),"Failed to retrieve data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void retrieveDataLimitedTo6(long cond){
        runningEntityList.clear();
        Query query = ref.orderByChild("createdTime")
                .endAt(cond-1).limitToLast(6);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for (DataSnapshot running:snapshot.getChildren()){
                        float di = running.child("distance").getValue(float.class);
                        long ti = running.child("time").getValue(long.class);
                        long cre = running.child("createdTime").getValue(long.class);
                        RunningEntity tmp = new RunningEntity(di,ti,cre);
                        runningEntityList.add(tmp);
                    }
                }
                Collections.reverse(runningEntityList);
                _presenter.NotifyPresenter(RUNNIG_ENTITY_APPEND);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(_presenter.getCurrentContext(),"Failed to retrieve data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
