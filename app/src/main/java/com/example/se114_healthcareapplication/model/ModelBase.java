package com.example.se114_healthcareapplication.model;

import com.example.se114_healthcareapplication.generalinterfaces.IPresenter;

public class ModelBase {
    IPresenter _presenter;
    public ModelBase(IPresenter _presenter){
        this._presenter = _presenter;
    }
}
