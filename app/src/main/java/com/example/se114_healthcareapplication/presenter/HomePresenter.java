package com.example.se114_healthcareapplication.presenter;

import com.example.se114_healthcareapplication.generalinterfaces.IPresenter;
import com.example.se114_healthcareapplication.generalinterfaces.IView;

public class HomePresenter extends PresenterBase implements IPresenter {
    public HomePresenter(IView view) {
        super(view);
    }

    @Override
    public void NotifyPresenter(int code) {

    }
}
