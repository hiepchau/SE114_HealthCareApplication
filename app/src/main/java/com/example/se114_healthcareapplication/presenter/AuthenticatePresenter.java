package com.example.se114_healthcareapplication.presenter;

import android.view.Display;
import com.example.se114_healthcareapplication.R;
import com.example.se114_healthcareapplication.generalinterfaces.IModel;
import com.example.se114_healthcareapplication.generalinterfaces.IPresenter;
import com.example.se114_healthcareapplication.generalinterfaces.IView;
import com.example.se114_healthcareapplication.model.UserModel;
import com.example.se114_healthcareapplication.model.entity.BaseModelEntity;
import com.example.se114_healthcareapplication.model.entity.UserEntity;

import static android.os.Build.VERSION_CODES.R;

import com.example.se114_healthcareapplication.R.id;

public class AuthenticatePresenter extends PresenterBase implements IPresenter {
    public AuthenticatePresenter(IView view) {
        super(view);
    }

    @Override
    public void NotifyPresenter(int code) {
        switch (code){
            case id.btn_goto_signin:
                _view.SwitchView(id.btn_goto_signin);
                break;
        }
    }
}
