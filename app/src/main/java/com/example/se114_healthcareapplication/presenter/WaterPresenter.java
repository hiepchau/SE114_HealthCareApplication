package com.example.se114_healthcareapplication.presenter;

import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.example.se114_healthcareapplication.generalinterfaces.IPresenter;
import com.example.se114_healthcareapplication.generalinterfaces.IView;
import com.example.se114_healthcareapplication.water_segment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WaterPresenter extends PresenterBase implements IPresenter {
    FirebaseAuth auth = FirebaseAuth.getInstance();
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    private int currentWater;
    public WaterPresenter(IView view) {

        super(view);
        auth = FirebaseAuth.getInstance();
        getCurrentWater();
    }

    @Override
    public void NotifyPresenter(int code) {

    }
    public void addWater(int amt){
        getCurrentWater();
        if(currentWater!= -1) {

                DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(auth.getCurrentUser().getUid())
                        .child(format.format(LocalDateTime.now())).child("Water");
                currentWater += amt;
                ref.setValue(currentWater);
                _view.UpdateView(water_segment.UPDATE_COMPLETE,currentWater);

        }
    }

    public void getCurrentWater() {


            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(auth.getCurrentUser().getUid())
                    .child(format.format(LocalDateTime.now())).child("Water");
            ValueEventListener listener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    if (!snapshot.exists()) {
                        ref.setValue(0);
                        currentWater = 0;
                        _view.UpdateView(water_segment.UPDATE_COMPLETE,currentWater);
                        Log.e("Water:","Create new branch");
                    }
                    else
                    {
                        currentWater = snapshot.getValue(int.class);
                        Log.e("Water:",String.valueOf(currentWater));
                        _view.UpdateView(water_segment.UPDATE_COMPLETE,currentWater);
                    }
                }
                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {
                    currentWater = -1;
                }
            };
            ref.addValueEventListener(listener);
    }
}
