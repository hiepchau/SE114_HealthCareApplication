package com.example.se114_healthcareapplication.generalinterfaces;

import android.app.Activity;
import android.content.Context;

public interface IPresenter {
    void NotifyPresenter(int code);
    Context getCurrentContext();
    public final int REGISTER_FAILED = 1002;
    public final int STEPS_COUNT_UPDATED = 505;
}
