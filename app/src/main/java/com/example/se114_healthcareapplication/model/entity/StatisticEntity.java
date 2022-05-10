package com.example.se114_healthcareapplication.model.entity;

public class StatisticEntity extends BaseModelEntity{
    private int Height;
    private int Weight;

    public StatisticEntity(int height, int weight) {
        Height = height;
        Weight = weight;
    }

    public int getHeight() {
        return Height;
    }

    public int getWeight() {
        return Weight;
    }
}
