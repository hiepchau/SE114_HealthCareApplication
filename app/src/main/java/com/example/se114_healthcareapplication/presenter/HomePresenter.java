package com.example.se114_healthcareapplication.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.se114_healthcareapplication.*;
import com.example.se114_healthcareapplication.generalinterfaces.IPresenter;
import com.example.se114_healthcareapplication.generalinterfaces.IView;
import com.google.firebase.auth.FirebaseAuth;

import java.lang.annotation.Target;

import static androidx.core.content.ContextCompat.startActivity;

public class HomePresenter extends PresenterBase implements IPresenter {
    public static final int SWITCH_TO_HOME = 1;
    public static final int SWITCH_TO_TARGET = 2;
    public static final int SWITCH_TO_NOTIFICATIONS = 3;
    public static final int SWITCH_TO_USER = 4;
    public HomePresenter(IView view) {
        super(view);
    }

    @Override
    public void NotifyPresenter(int code) {
        if(code == SWITCH_TO_HOME){
            FragmentManager manager = _view.GetFragmentManager();
            manager.beginTransaction().replace(R.id.fragmentContainer_homeactivity, new HomeFragment()).commit();
        }

        if(code == SWITCH_TO_TARGET){
            FragmentManager manager = _view.GetFragmentManager();
            manager.beginTransaction().replace(R.id.fragmentContainer_homeactivity, new TargetFragment()).commit();
        }


        if(code == SWITCH_TO_NOTIFICATIONS){
            FragmentManager manager = _view.GetFragmentManager();
            manager.beginTransaction().replace(R.id.fragmentContainer_homeactivity, new NotificationsFragment()).commit();
        }

        if(code == SWITCH_TO_USER){
            FragmentManager manager = _view.GetFragmentManager();
            manager.beginTransaction().replace(R.id.fragmentContainer_homeactivity, new UserFragment()).commit();
        }
    }

    @Override
    public Context getCurrentContext() {
        return _view.getAppActivity();
    }
}
