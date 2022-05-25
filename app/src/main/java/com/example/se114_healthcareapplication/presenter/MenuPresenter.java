package com.example.se114_healthcareapplication.presenter;

import android.content.Intent;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.se114_healthcareapplication.*;
import com.example.se114_healthcareapplication.generalinterfaces.IPresenter;
import com.example.se114_healthcareapplication.generalinterfaces.IView;
import com.google.firebase.auth.FirebaseAuth;

public class MenuPresenter extends PresenterBase implements IPresenter {
    public static final int SWITCH_TO_WATER = 1;
    public static final int SWITCH_TO_STEP =2;
    public static final int SWITCH_TO_ALARM =3;
    public static final int SWITCH_TO_BMI = 5;
    public static final int LOGOUT = -1;
    public MenuPresenter(IView view) {
        super(view);
    }

    @Override
    public void NotifyPresenter(int code) {
        if(code == SWITCH_TO_WATER){
            FragmentManager fragmentManager = _view.GetFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer_function,water_segment.class,null).addToBackStack("").commit();
        }

        if(code == SWITCH_TO_ALARM){
            FragmentManager fragmentManager = _view.GetFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer_function, alarm_segment.class,null).addToBackStack("").commit();
        }


        if(code == SWITCH_TO_STEP){
            FragmentManager fragmentManager = _view.GetFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer_function, step_segment.class,null).addToBackStack("").commit();
        }

        if(code == LOGOUT){
            FirebaseAuth.getInstance().signOut();
            _view.getAppActivity().startActivity(new Intent(_view.getAppActivity(), AuthenticateActivity.class));
            _view.getAppActivity().finish();
        }

    }
}
