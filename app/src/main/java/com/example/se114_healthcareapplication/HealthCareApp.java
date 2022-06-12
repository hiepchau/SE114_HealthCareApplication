package com.example.se114_healthcareapplication;

import android.app.Application;
import com.google.firebase.database.FirebaseDatabase;

public class HealthCareApp extends Application {
    @Override
    public void onCreate() {
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        super.onCreate();
    }
}
