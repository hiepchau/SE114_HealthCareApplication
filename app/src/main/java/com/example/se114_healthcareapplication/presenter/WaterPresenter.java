package com.example.se114_healthcareapplication.presenter;

import com.example.se114_healthcareapplication.generalinterfaces.IPresenter;
import com.example.se114_healthcareapplication.generalinterfaces.IView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.format.DateTimeFormatter;

public class WaterPresenter extends PresenterBase implements IPresenter {
    FirebaseAuth auth = FirebaseAuth.getInstance();
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    public WaterPresenter(IView view) {
        super(view);
    }

    @Override
    public void NotifyPresenter(int code) {

    }
}
