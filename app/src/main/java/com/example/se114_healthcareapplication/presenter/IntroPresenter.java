package com.example.se114_healthcareapplication.presenter;

import android.Manifest;
import android.content.pm.PackageManager;
import android.widget.Toast;
import androidx.core.content.ContextCompat;
import com.example.se114_healthcareapplication.HomeActivity;
import com.example.se114_healthcareapplication.generalinterfaces.IPresenter;
import com.example.se114_healthcareapplication.generalinterfaces.IView;

import static androidx.core.app.ActivityCompat.requestPermissions;

public class IntroPresenter extends PresenterBase implements IPresenter {
    public IntroPresenter(IView view) {
        super(view);
    }

    @Override
    public void NotifyPresenter(int code) {
        _view.SwitchView(0);
    }

    public void getRequiredPermission(){
        if (
                (ContextCompat.checkSelfPermission(_view.getAppActivity(), Manifest.permission.ACTIVITY_RECOGNITION) ==
                PackageManager.PERMISSION_GRANTED)
            &&
        (ContextCompat.checkSelfPermission(_view.getAppActivity(), Manifest.permission.SCHEDULE_EXACT_ALARM) ==
                PackageManager.PERMISSION_GRANTED)
                &&
                        (ContextCompat.checkSelfPermission(_view.getAppActivity(), Manifest.permission.SET_ALARM) ==
                                PackageManager.PERMISSION_GRANTED)
                &&
                        (ContextCompat.checkSelfPermission(_view.getAppActivity(), Manifest.permission.INTERNET) ==
                                PackageManager.PERMISSION_GRANTED)
                &&
                        (ContextCompat.checkSelfPermission(_view.getAppActivity(), Manifest.permission.ACCESS_NETWORK_STATE) ==
                                PackageManager.PERMISSION_GRANTED)
                &&
                        (ContextCompat.checkSelfPermission(_view.getAppActivity(), Manifest.permission.WAKE_LOCK) ==
                                PackageManager.PERMISSION_GRANTED)
                &&
                        (ContextCompat.checkSelfPermission(_view.getAppActivity(), Manifest.permission.DISABLE_KEYGUARD) ==
                                PackageManager.PERMISSION_GRANTED)
        ) {
            Toast.makeText(_view.getAppActivity(), "Permission grandted", Toast.LENGTH_SHORT).show();
        }
        else {
            requestPermissions(_view.getAppActivity(),
                    new String[]{ Manifest.permission.ACTIVITY_RECOGNITION,
                    Manifest.permission.SCHEDULE_EXACT_ALARM,
                            Manifest.permission.SET_ALARM,
                            Manifest.permission.INTERNET,
                            Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.WAKE_LOCK,
                    Manifest.permission.DISABLE_KEYGUARD},
                    1);
        }
    }
}
