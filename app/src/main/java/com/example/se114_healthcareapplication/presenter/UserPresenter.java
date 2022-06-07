package com.example.se114_healthcareapplication.presenter;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import com.example.se114_healthcareapplication.model.entity.StatisticEntity;
import com.example.se114_healthcareapplication.model.entity.UserEntity;
import com.example.se114_healthcareapplication.view.AuthenticateActivity;
import com.example.se114_healthcareapplication.view.authentication.resetpassword;
import com.example.se114_healthcareapplication.view.bottom_nav.NotificationsFragment;
import com.example.se114_healthcareapplication.R;
import com.example.se114_healthcareapplication.generalinterfaces.IPresenter;
import com.example.se114_healthcareapplication.generalinterfaces.IView;
import com.example.se114_healthcareapplication.view.bottom_nav.UserFragment;
import com.example.se114_healthcareapplication.view.components.info_user;
import com.example.se114_healthcareapplication.view.manage_data;
import com.example.se114_healthcareapplication.model.StatisticModel;
import com.example.se114_healthcareapplication.model.UserModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import org.jetbrains.annotations.NotNull;

public class UserPresenter extends PresenterBase implements IPresenter {
    public static final int LOGOUT = 203493;
    public static final int UPDATE_USER_INFO = 6187239;
    public static final int UPDATE_STATISTIC = 9182372;
    public static final int SWITCH_TO_MANAGEDATA = 127381;
    public static final int SWITCH_TO_RESET_PASS = 182349;
    public static final int BACK_TO_DATA = 4617349;
    public static final int RESET_PASSWORD_MAIL_SENT = 200;
    public static final int RESET_PASSWORD_MAIL_FAILED = 1823;
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
        if(code == SWITCH_TO_MANAGEDATA){
            FragmentManager manager = _view.GetFragmentManager();
            manager.beginTransaction().replace(R.id.fragmentContainer_homeactivity, new manage_data()).commit();
        }
        if(code == SWITCH_TO_RESET_PASS){
            FragmentManager manager = _view.GetFragmentManager();
            manager.beginTransaction().replace(R.id.fragmentContainer_homeactivity, new resetpassword()).commit();
        }
        if(code == BACK_TO_DATA){
            FragmentManager manager = _view.GetFragmentManager();
            manager.beginTransaction().replace(R.id.fragmentContainer_homeactivity, new UserFragment()).commit();
        }
    }

    @Override
    public Context getCurrentContext() {
        return _view.getAppActivity().getApplicationContext();
    }
    public void UpdateData(UserEntity user, StatisticEntity stat){
        statisticModel.UpdateDatabase(stat);
        userModel.UpdateDatabase(user);
    }
    public void refresh(){
        userModel.getCurrentUser();
        statisticModel.getCurrentStatistic();
    }

    public void ResetPassword(){
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            FirebaseAuth.getInstance().sendPasswordResetEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            _view.SwitchView(RESET_PASSWORD_MAIL_SENT);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull @NotNull Exception e) {
                            _view.UpdateView(RESET_PASSWORD_MAIL_FAILED,e.getMessage());
                        }
                    });
        }
    }
}
