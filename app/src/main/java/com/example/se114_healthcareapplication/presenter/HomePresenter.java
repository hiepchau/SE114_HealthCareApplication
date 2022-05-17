package com.example.se114_healthcareapplication.presenter;

import android.content.Intent;
import com.example.se114_healthcareapplication.AuthenticateActivity;
import com.example.se114_healthcareapplication.HomeActivity;
import com.example.se114_healthcareapplication.generalinterfaces.IPresenter;
import com.example.se114_healthcareapplication.generalinterfaces.IView;
import com.google.firebase.auth.FirebaseAuth;

import static androidx.core.content.ContextCompat.startActivity;

public class HomePresenter extends PresenterBase implements IPresenter {
    public HomePresenter(IView view) {
        super(view);
    }

    @Override
    public void NotifyPresenter(int code) {

    }

    public void Logout(){
        FirebaseAuth.getInstance().signOut();
        _view.getAppActivity().startActivity(new Intent(_view.getAppActivity(), AuthenticateActivity.class));
        _view.getAppActivity().finish();
    }
}
