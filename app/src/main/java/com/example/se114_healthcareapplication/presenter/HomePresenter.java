package com.example.se114_healthcareapplication.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.Toast;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.se114_healthcareapplication.*;
import com.example.se114_healthcareapplication.generalinterfaces.IPresenter;
import com.example.se114_healthcareapplication.generalinterfaces.IView;
import com.example.se114_healthcareapplication.model.AvatarModel;
import com.example.se114_healthcareapplication.model.UserModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.lang.annotation.Target;

import static androidx.core.content.ContextCompat.startActivity;

public class HomePresenter extends PresenterBase implements IPresenter {
    public static final int SWITCH_TO_HOME = 1;
    public static final int SWITCH_TO_TARGET = 2;
    public static final int SWITCH_TO_NOTIFICATIONS = 3;
    public static final int SWITCH_TO_USER = 4;
    public static final int UPDATE_USER_INFO = 38742;
    private AvatarModel avatarModel;
    private UserModel userModel;
    private Uri avatar;
    ActivityResultLauncher<Intent> activityResultLauncher;
    public HomePresenter(IView view) {

        super(view);
        avatarModel = new AvatarModel(this);
        activityResultLauncher = _view.getCurrentFragment().registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode()==Activity.RESULT_OK){
                            avatar = result.getData().getData();
                            avatarModel.UploadAvatar(avatar);
                        }
                    }
                });
        userModel = new UserModel(this);
        userModel.getCurrentUser();
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
        if(code == AvatarModel.UPLOAD_SUCCESS){
            avatarModel.retrieveAvatar();
        }
        if(code == AvatarModel.RETRIEVE_SUCCESS){
            if(getAvatar()!=null)
            _view.UpdateView(HomeFragment.UPDATE_AVATAR,getAvatar());
        }
        if(code == UserModel.RETRIEVE_USER_SUCCESS){
            _view.UpdateView(UPDATE_USER_INFO,userModel.currentUser);
        }
        if(code == UserModel.USER_NOT_FOUND){
            logout();
        }
    }

    @Override
    public Context getCurrentContext() {
        return _view.getAppActivity();
    }
    public void changeAvatar(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        activityResultLauncher.launch(intent);
    }
    public Bitmap getAvatar(){
        return avatarModel.getBitmap();
    }
    private void logout(){
        FirebaseAuth.getInstance().signOut();
        _view.getAppActivity().startActivity(new Intent(_view.getAppActivity(), AuthenticateActivity.class));
        _view.getAppActivity().finish();
    }
}
