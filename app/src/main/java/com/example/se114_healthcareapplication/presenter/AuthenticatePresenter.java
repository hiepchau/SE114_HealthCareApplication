package com.example.se114_healthcareapplication.presenter;

import android.content.Intent;
import android.view.Display;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.example.se114_healthcareapplication.AuthenticateActivity;
import com.example.se114_healthcareapplication.HomeActivity;
import com.example.se114_healthcareapplication.R;
import com.example.se114_healthcareapplication.generalinterfaces.IModel;
import com.example.se114_healthcareapplication.generalinterfaces.IPresenter;
import com.example.se114_healthcareapplication.generalinterfaces.IView;
import com.example.se114_healthcareapplication.model.UserModel;
import com.example.se114_healthcareapplication.model.entity.BaseModelEntity;
import com.example.se114_healthcareapplication.model.entity.UserEntity;

import static android.os.Build.VERSION_CODES.R;

import com.example.se114_healthcareapplication.R.id;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class AuthenticatePresenter extends PresenterBase implements IPresenter {
    FirebaseAuth auth;
    public AuthenticatePresenter(IView view) {
        super(view);
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public void NotifyPresenter(int code) {
        switch (code){
            case id.btn_goto_signin:
                _view.SwitchView(id.btn_goto_signin);
                break;
        }
    }

    public void Authenticate(String email, String pass){
        auth.signInWithEmailAndPassword(email,pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Intent intent = new Intent(_view.getAppActivity(),HomeActivity.class);
                intent.putExtra("session",auth.getCurrentUser().getUid());
                Toast.makeText(_view.getAppActivity(), "Login successfully", Toast.LENGTH_SHORT).show();
                _view.StartNewActivity(intent);
                _view.getAppActivity().finish();
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(_view.getAppActivity(), "Login failed",Toast.LENGTH_SHORT);
                        _view.UpdateView(IView.EMPTY_CODE,null);
                    }
                });
    }

    public void SignUp(String email,String pass, String repass){
        String u = email;
        String p = pass;
        String re = repass;
        if(u.isEmpty() || p.isEmpty() || re.isEmpty()){
            Toast.makeText(_view.getAppActivity(), "Information required!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(p.length()<=6){
            Toast.makeText(_view.getAppActivity(),"Password too short",Toast.LENGTH_SHORT);
        }
        if (!p.equals(re)){
            Toast.makeText(_view.getAppActivity(),"Password dont match", Toast.LENGTH_SHORT).show();
        }
        else if (p.equals(re)){
            auth.createUserWithEmailAndPassword(u,p).addOnCompleteListener(_view.getAppActivity(), task -> {
                if(task.isSuccessful())
                {
                    Toast.makeText(_view.getAppActivity(),"Register successfully", Toast.LENGTH_SHORT).show();
                    _view.UpdateView(IView.EMPTY_CODE,null);
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(_view.getAppActivity(),"Register failed:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}
