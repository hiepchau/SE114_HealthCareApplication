package com.example.se114_healthcareapplication.presenter;

import android.content.Context;
import android.content.Intent;
import com.example.se114_healthcareapplication.view.AuthenticateActivity;
import com.example.se114_healthcareapplication.generalinterfaces.IPresenter;
import com.example.se114_healthcareapplication.generalinterfaces.IView;
import com.example.se114_healthcareapplication.model.StatisticModel;
import com.example.se114_healthcareapplication.model.UserModel;
import com.google.firebase.auth.FirebaseAuth;

public class UserPresenter extends PresenterBase implements IPresenter {
    public static final int LOGOUT = 203493;
    public static final int UPDATE_USER_INFO = 6187239;
    public static final int UPDATE_STATISTIC = 9182372;
    StatisticModel statisticModel;
    UserModel userModel;
    public UserPresenter(IView view) {

        super(view);
        statisticModel = new StatisticModel(this);
        userModel = new UserModel(this);
        userModel.getCurrentUser();
    }

    @Override
    public void NotifyPresenter(int code) {
        if(code == LOGOUT){
            FirebaseAuth.getInstance().signOut();
            _view.getAppActivity().startActivity(new Intent(_view.getAppActivity(), AuthenticateActivity.class));
            _view.getAppActivity().finish();
        }
        if(code == StatisticModel.DONE_INIT_DATA){
            _view.UpdateView(UPDATE_STATISTIC, statisticModel.currentEntity);
        }
        if(code == UserModel.RETRIEVE_USER_SUCCESS){
            _view.UpdateView(UPDATE_USER_INFO,userModel.currentUser);
        }
    }

    @Override
    public Context getCurrentContext() {
        return _view.getAppActivity().getApplicationContext();
    }
}
