package com.example.se114_healthcareapplication.model.entity;

public class RunningEntity extends BaseModelEntity{
    public float distance;
    public long time;
    public long createdTime;
    public RunningEntity(float dis, long ti, long created){
        distance = dis;
        time = ti;
        this.createdTime = created;
    }
}
