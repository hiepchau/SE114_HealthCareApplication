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

    UserEntity currentUser;
    FirebaseAuth auth;
    DatabaseReference ref;
    public static final int UPDATE_USER_SUCCESS = 1022;
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

        ref.setValue(entity).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                _presenter.NotifyPresenter(IPresenter.REGISTER_FAILED);
            }
        });
    }
    public UserEntity getCurrentUser(){
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot snap: snapshot.getChildren()
                ) {
                    String firstname = snap.child("FirstName").getValue(String.class);
                    String lastname = snap.child("LastName").getValue(String.class);
                    int age = snap.child("Age").getValue(int.class);
                    int Gen = snap.child("Gender").getValue(int.class);
                    int rewater = snap.child("recommendWater").getValue(int.class);
                    currentUser = new UserEntity(firstname,lastname,age,Gen,rewater);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        return currentUser;
    }
}
