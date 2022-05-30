package com.example.se114_healthcareapplication.model;

import com.example.se114_healthcareapplication.generalinterfaces.IModel;
import com.example.se114_healthcareapplication.generalinterfaces.IPresenter;
import com.example.se114_healthcareapplication.model.ModelBase;
import com.example.se114_healthcareapplication.model.entity.RunningEntity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class RunningModel extends ModelBase implements IModel<RunningEntity> {
    FirebaseAuth auth;
    DatabaseReference ref;
    final String path = "RunningStat";
    public RunningModel(IPresenter _presenter) {
        super(_presenter);
        auth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference().child(auth.getCurrentUser().getUid())
                .child(path);
    }

    @Override
    public List<RunningEntity> getEntity() {
        return null;
    }

    @Override
    public void NotifyModel(int code) {

    }

    @Override
    public void UpdateDatabase(RunningEntity entity) {
        ref.child(String.valueOf(System.currentTimeMillis())).setValue(entity);
    }
}
