package com.example.se114_healthcareapplication.model.entity;

public class RunningEntity extends BaseModelEntity{
    public float distance;
    public long time;
    public RunningEntity(float dis, long ti){
        distance = dis;
        time = ti;
    }
}
