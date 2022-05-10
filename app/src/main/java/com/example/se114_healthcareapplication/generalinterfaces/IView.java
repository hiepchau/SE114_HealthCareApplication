package com.example.se114_healthcareapplication.generalinterfaces;

import com.example.se114_healthcareapplication.model.entity.BaseModelEntity;

public interface IView {
    void UpdateView(int code, Object entity);
    void SwitchView(int code);
}
