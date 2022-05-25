package com.example.se114_healthcareapplication.model.entity;

public class StatisticEntity extends BaseModelEntity{
    public double Height;
    public double Weight;
    public int Water;
    public int Steps;
    public long SleepTime;
    public long CreatedTime;

    public StatisticEntity(double height, double weight, int water, int steps, int sleepTime) {
        Height = height;
        Weight = weight;
        this.Water = water;
        this.Steps = steps;
        this.SleepTime = sleepTime;
        this.CreatedTime = System.currentTimeMillis();
    }

}
