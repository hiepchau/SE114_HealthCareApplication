package com.example.se114_healthcareapplication.presenter;

import android.content.Context;
import com.example.se114_healthcareapplication.generalinterfaces.IPresenter;
import com.example.se114_healthcareapplication.generalinterfaces.IView;

public class TargetPresenter extends PresenterBase implements IPresenter {
    public TargetPresenter(IView view) {
        super(view);
    }

    @Override
    public void NotifyPresenter(int code) {

    }

    @Override
    public Context getCurrentContext() {
        return null;
    }
}
