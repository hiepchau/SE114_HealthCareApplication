package com.example.se114_healthcareapplication.presenter;

import android.content.Context;
import android.content.Intent;
import com.example.se114_healthcareapplication.AuthenticateActivity;
import com.example.se114_healthcareapplication.generalinterfaces.IPresenter;
import com.example.se114_healthcareapplication.generalinterfaces.IView;
import com.google.firebase.auth.FirebaseAuth;

public class UserPresenter extends PresenterBase implements IPresenter {
    public static final int LOGOUT = 203493;
    public UserPresenter(IView view) {
        super(view);
    }

    @Override
    public void NotifyPresenter(int code) {
        if(code == LOGOUT){
            FirebaseAuth.getInstance().signOut();
            _view.getAppActivity().startActivity(new Intent(_view.getAppActivity(), AuthenticateActivity.class));
            _view.getAppActivity().finish();
        }
    }

    @Override
    public Context getCurrentContext() {
        return _view.getAppActivity().getApplicationContext();
    }
}
