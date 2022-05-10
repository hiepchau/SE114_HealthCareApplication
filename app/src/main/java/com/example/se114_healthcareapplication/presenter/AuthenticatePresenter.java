package com.example.se114_healthcareapplication.presenter;

import android.view.Display;
import com.example.se114_healthcareapplication.generalinterfaces.IModel;
import com.example.se114_healthcareapplication.generalinterfaces.IPresenter;
import com.example.se114_healthcareapplication.generalinterfaces.IView;
import com.example.se114_healthcareapplication.model.UserModel;
import com.example.se114_healthcareapplication.model.entity.BaseModelEntity;
import com.example.se114_healthcareapplication.model.entity.UserEntity;

public class AuthenticatePresenter extends PresenterBase implements IPresenter {
    IModel<UserEntity> Model;
    public AuthenticatePresenter(IView view) {
        super(view);
        Model = new UserModel(this);
    }

    @Override
    public void NotifyPresenter(int code) {

    }
}
