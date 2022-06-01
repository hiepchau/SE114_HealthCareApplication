package com.example.se114_healthcareapplication.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.hotspot2.pps.Credential;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Display;
import android.widget.Toast;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.se114_healthcareapplication.*;
import com.example.se114_healthcareapplication.R;
import com.example.se114_healthcareapplication.generalinterfaces.IModel;
import com.example.se114_healthcareapplication.generalinterfaces.IPresenter;
import com.example.se114_healthcareapplication.generalinterfaces.IView;
import com.example.se114_healthcareapplication.model.StatisticModel;
import com.example.se114_healthcareapplication.model.UserModel;
import com.example.se114_healthcareapplication.model.entity.BaseModelEntity;
import com.example.se114_healthcareapplication.model.entity.StatisticEntity;
import com.example.se114_healthcareapplication.model.entity.UserEntity;

import static android.os.Build.VERSION_CODES.R;
import static android.provider.Settings.System.getString;
import static androidx.core.app.ActivityCompat.startActivityForResult;

import com.example.se114_healthcareapplication.R.id;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.*;
import com.google.firebase.database.FirebaseDatabase;

public class AuthenticatePresenter extends PresenterBase implements IPresenter {
    FirebaseAuth auth;
    ActivityResultLauncher<Intent> activityResultLauncher;
    public final int GOOGLE_REQUEST = 1001;
    private boolean canContinue;
    private GoogleSignInClient client;
    public AuthenticatePresenter(IView view) {
        super(view);
        auth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("593884992492-5qj5ecn99fs0oc2ue2n51jel09a611hq.apps.googleusercontent.com")
                .requestEmail()
                .build();
        client = GoogleSignIn.getClient(_view.getAppActivity(), gso);
    }

    public void registerGoogleAuthen(){
        activityResultLauncher = _view.getCurrentFragment().registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {

                            // There are no request codes
                            Intent data = result.getData();
                            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                            try {
                                // Google Sign In was successful, authenticate with Firebase
                                GoogleSignInAccount account = task.getResult(ApiException.class);
                                firebaseAuthWithGoogle(account);
                            } catch (ApiException e) {
                                // Google Sign In failed, update UI appropriately
                                // ...
                                Toast.makeText(_view.getAppActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                });
    }

    public void AuthenticateWithGoogle(){
        Intent googleIntent = client.getSignInIntent();
        activityResultLauncher.launch(googleIntent);
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        AuthenticatePresenter authCLone = this;

        auth.signInWithCredential(credential)
                .addOnCompleteListener(_view.getAppActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            UserModel userModel = new UserModel(authCLone);
                            userModel.checkRegistered();
                        } else {
                            Toast.makeText(_view.getAppActivity(), "Cannot authenticate via Google at the moment", Toast.LENGTH_SHORT).show();
                        }


                        // ...
                    }
                });
    }


    @Override
    public void NotifyPresenter(int code) {
        switch (code){
            case id.btn_goto_signin:
                _view.SwitchView(id.btn_goto_signin);
                break;
            case REGISTER_FAILED:
                _view.UpdateView(IView.EMPTY_CODE,null);
                Toast.makeText(_view.getAppActivity(), "Register failed",Toast.LENGTH_SHORT).show();
                break;
            case UserModel.NOT_REGISTERED:
                FragmentManager fragmentManager = _view.GetFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(id.authenticateContainer, register_google.class,null).addToBackStack("").commit();
                break;
                case UserModel.REGISTERED:
                    Intent intent = new Intent(_view.getAppActivity(),HomeActivity.class);
                    intent.putExtra("session",auth.getCurrentUser().getUid());
                    _view.StartNewActivity(intent);
                    _view.getAppActivity().finish();
                    break;

        }
    }

    @Override
    public Context getCurrentContext() {
        return _view.getAppActivity();
    }

    public void Authenticate(String email, String pass){
        auth.signInWithEmailAndPassword(email,pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                if(auth.getCurrentUser().isEmailVerified()) {
                    Intent intent = new Intent(_view.getAppActivity(), HomeActivity.class);
                    intent.putExtra("session", auth.getCurrentUser().getUid());
                    _view.StartNewActivity(intent);
                    _view.getAppActivity().finish();
                }
                else{
                    Toast.makeText(_view.getAppActivity(), "Email not verified!",Toast.LENGTH_SHORT).show();
                    auth.signOut();
                }
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

    public void SignUp(String email,String pass, String repass, String firstname,String lastname, int age, double height, double weight, int gender){
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
                    auth.signInWithEmailAndPassword(u,p);
                    UserModel userModel = new UserModel(this);
                    StatisticModel statisticModel = new StatisticModel(this);
                    UserEntity userEntity = new UserEntity(firstname,lastname,age,gender);
                    userModel.UpdateDatabase(userEntity);
                    StatisticEntity statistic = new StatisticEntity(height,weight,0,0,0,1,"");
                    statisticModel.UpdateDatabase(statistic);
                    FirebaseUser user = auth.getCurrentUser();
                    user.sendEmailVerification()
                            .addOnCompleteListener(_view.getAppActivity(), new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task) {
                                    // Re-enable button

                                    if (task.isSuccessful()) {
                                        Toast.makeText(_view.getAppActivity(),
                                                "Verification email sent to " + user.getEmail(),
                                                Toast.LENGTH_SHORT).show();
                                    } else {
                                        Log.e("Register:", "sendEmailVerification", task.getException());
                                        Toast.makeText(_view.getAppActivity(),
                                                "Failed to send verification email.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                    auth.signOut();
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

    public void RegisterGoogle(String firstname, int age, double height, double weight){
        UserModel userModel = new UserModel(this);
        StatisticModel statisticModel = new StatisticModel(this);
        UserEntity userEntity = new UserEntity(firstname,"",age,0);
        userModel.UpdateDatabase(userEntity);
        StatisticEntity statistic = new StatisticEntity(height,weight,0,0,0,1,"");
        statisticModel.UpdateDatabase(statistic);
        Intent intent = new Intent(_view.getAppActivity(),HomeActivity.class);
        intent.putExtra("session",auth.getCurrentUser().getUid());
        _view.StartNewActivity(intent);
        _view.getAppActivity().finish();
    }
    public void checkSignedin(){
        if(auth.getCurrentUser() != null){
            if(auth.getCurrentUser().isEmailVerified()) {
                Intent intent = new Intent(_view.getAppActivity(), HomeActivity.class);
                intent.putExtra("session", auth.getCurrentUser().getUid());

                _view.StartNewActivity(intent);
                _view.getAppActivity().finish();
            }
        }
        else auth.signOut();
    }

    public void firstInit(){

    }
}
