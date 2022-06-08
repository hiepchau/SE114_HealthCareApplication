package com.example.se114_healthcareapplication.model;

import androidx.annotation.NonNull;
import com.example.se114_healthcareapplication.generalinterfaces.IModel;
import com.example.se114_healthcareapplication.generalinterfaces.IPresenter;
import com.example.se114_healthcareapplication.model.ModelBase;
import com.example.se114_healthcareapplication.model.entity.RunningEntity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class RunningModel extends ModelBase implements IModel<RunningEntity> {
    FirebaseAuth auth;
    DatabaseReference ref;
    final String path = "RunningStat";
    public static final int RUNNING_ENTITY_RETRIEVED = 91283;
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
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot running:snapshot.getChildren()){
                    float di = running.child("distance").getValue(float.class);
                    long ti = running.child("time").getValue(long.class);
                    long cre = running.child("createdTime").getValue(long.class);
                    RunningEntity tmp = new RunningEntity(di,ti,cre);
                    runningEntityList.add(tmp);
                }
                _presenter.NotifyPresenter(RUNNING_ENTITY_RETRIEVED);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
}
