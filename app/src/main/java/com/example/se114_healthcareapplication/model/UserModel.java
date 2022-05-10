package com.example.se114_healthcareapplication.model;

import com.example.se114_healthcareapplication.generalinterfaces.IModel;
import com.example.se114_healthcareapplication.generalinterfaces.IPresenter;
import com.example.se114_healthcareapplication.model.entity.UserEntity;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class UserModel extends ModelBase implements IModel<UserEntity> {

    UserEntity currentUser;
    public UserModel(IPresenter presenter){
        super(presenter);
        //create user
    }
    @Override
    public List<UserEntity> getEntity() {
        return null;
    }

    @Override
    public void NotifyModel(int code) {

    }

    @Override
    public void UpdateDatabase(UserEntity entity) {

    }


}
