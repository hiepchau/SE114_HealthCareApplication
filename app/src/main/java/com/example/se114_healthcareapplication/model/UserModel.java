package com.example.se114_healthcareapplication.model;

import androidx.annotation.NonNull;
import com.example.se114_healthcareapplication.generalinterfaces.IModel;
import com.example.se114_healthcareapplication.generalinterfaces.IPresenter;
import com.example.se114_healthcareapplication.model.entity.UserEntity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class UserModel extends ModelBase implements IModel<UserEntity> {

    public UserEntity currentUser;
    FirebaseAuth auth;
    DatabaseReference ref;
    public static final int RETRIEVE_USER_SUCCESS = 1022;
    public static final int NOT_REGISTERED = 404;
    public static final int REGISTERED = 200;
    public static final int USER_NOT_FOUND = 405;
    public UserModel(IPresenter presenter){
        super(presenter);
        auth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference().child(auth.getCurrentUser().getUid())
                .child("User");

    }
    @Override
    public List<UserEntity> getEntity() {
        return null;
    }

    @Override
    public void NotifyModel(int code) {

    }

    @Override
    public void UpdateDatabase(UserEntity entity) {

        ref.setValue(entity);
    }
    public UserEntity getCurrentUser(){
        if(auth.getCurrentUser()!=null) {
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    if(!snapshot.exists()){
                        _presenter.NotifyPresenter(USER_NOT_FOUND);
                    }

                        String firstname = snapshot.child("FirstName").getValue(String.class);
                        String lastname = snapshot.child("LastName").getValue(String.class);
                        int age = snapshot.child("Age").getValue(int.class);
                        int Gen = snapshot.child("Gender").getValue(int.class);
                        currentUser = new UserEntity(firstname, lastname, age, Gen);
                        _presenter.NotifyPresenter(RETRIEVE_USER_SUCCESS);
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });
            return currentUser;
        }
        return null;
    }
    public void checkRegistered(){
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(!snapshot.exists()){
                    _presenter.NotifyPresenter(NOT_REGISTERED);
                }
                else {
                    _presenter.NotifyPresenter(REGISTERED);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
}
