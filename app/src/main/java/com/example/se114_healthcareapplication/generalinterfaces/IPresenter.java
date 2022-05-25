package com.example.se114_healthcareapplication.generalinterfaces;

public interface IPresenter {
    void NotifyPresenter(int code);
    public final int REGISTER_FAILED = 1002;
    public final int STEPS_COUNT_UPDATED = 505;
}
