package com.example.se114_healthcareapplication.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.example.se114_healthcareapplication.generalinterfaces.IPresenter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;

public class AvatarModel extends ModelBase {
    FirebaseAuth auth;
    StorageReference sto;
    Bitmap bitmap;

    public Bitmap getBitmap() {
        return bitmap;
    }
    public static final int UPLOAD_SUCCESS = 238451;
    public static final int RETRIEVE_SUCCESS = 982345;

    public AvatarModel(IPresenter presenter){
        super(presenter);
        auth = FirebaseAuth.getInstance();
        sto = FirebaseStorage.getInstance().getReference().child(auth.getCurrentUser().getUid());
        retrieveAvatar();
    }

    public void UploadAvatar(Uri imageUri){
        sto.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                _presenter.NotifyPresenter(UPLOAD_SUCCESS);
            }
        });
    }

    public void retrieveAvatar(){
        try {
            File localFile = File.createTempFile("image","jpg");
            sto.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    Toast.makeText(_presenter.getCurrentContext(),"Pictures retrieved!", Toast.LENGTH_SHORT).show();
                    _presenter.NotifyPresenter(RETRIEVE_SUCCESS);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                }
            });
        } catch (IOException e) {
            Log.e("AvatarModel",e.toString());
        }
    }
}
