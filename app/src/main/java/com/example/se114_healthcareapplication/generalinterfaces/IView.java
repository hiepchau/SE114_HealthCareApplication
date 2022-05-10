package com.example.se114_healthcareapplication.generalinterfaces;

import android.app.Activity;
import android.content.Intent;
import com.example.se114_healthcareapplication.model.entity.BaseModelEntity;

public interface IView<T extends IPresenter> {
    int EMPTY_CODE = -10001;
    void UpdateView(int code, Object entity);
    void SwitchView(int code);
    void setMainPresenter(T presenter);
    T getMainpresnter();
    void StartNewActivity(Intent intent);
    Activity getAppActivity();
}
