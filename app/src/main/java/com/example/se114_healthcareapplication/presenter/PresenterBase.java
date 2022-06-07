package com.example.se114_healthcareapplication.presenter;

import com.example.se114_healthcareapplication.generalinterfaces.IModel;
import com.example.se114_healthcareapplication.generalinterfaces.IView;
import com.example.se114_healthcareapplication.model.ModelBase;
import com.example.se114_healthcareapplication.model.UserModel;
import com.example.se114_healthcareapplication.model.entity.BaseModelEntity;

public class PresenterBase {
    protected IView _view;
    public PresenterBase(IView view){
        this._view = view;
    }
    public IView getView(){
        return _view;
    };
    public static final int BACK_ON_MENU = 9645171;
}
