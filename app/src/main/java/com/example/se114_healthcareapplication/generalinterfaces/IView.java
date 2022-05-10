package com.example.se114_healthcareapplication.generalinterfaces;

import com.example.se114_healthcareapplication.model.entity.BaseModelEntity;

public interface IView {
    final int UPDATE_FRAGMENT_LOGIN = 1;
    final int UPDATE_FRAGMENT_SIGNUP =2;
    void UpdateView(int code, BaseModelEntity entity);
    void SwitchView(int code);
}
