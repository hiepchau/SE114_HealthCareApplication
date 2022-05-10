package com.example.se114_healthcareapplication.presenter;

import com.example.se114_healthcareapplication.generalinterfaces.IView;

public class PresenterBase {
    IView _view;
    public PresenterBase(IView view){
        this._view = view;
    }
}
