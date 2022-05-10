package com.example.se114_healthcareapplication.generalinterfaces;

import com.example.se114_healthcareapplication.model.entity.BaseModelEntity;

import java.util.List;

public interface IModel<T extends BaseModelEntity> {
    List<T> getEntity();
    void NotifyModel(int code);
    void UpdateDatabase(T entity);
}
