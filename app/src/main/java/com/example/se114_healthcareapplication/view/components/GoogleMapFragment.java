package com.example.se114_healthcareapplication.view.components;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentManager;
import com.example.se114_healthcareapplication.R;
import com.example.se114_healthcareapplication.generalinterfaces.IView;
import com.example.se114_healthcareapplication.presenter.GoogleMapPresenter;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import org.jetbrains.annotations.NotNull;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GoogleMapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GoogleMapFragment extends Fragment implements IView<GoogleMapPresenter> {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView textclock;
    private Button clockbtn,clearbtn;
    private List<LatLng> latLngList;
    private MapView mMap;
    private GoogleMap gMap;
    List<Polyline> runlinels;
    private float distanceTravelled;
    private float lastdistance;
    private FusedLocationProviderClient fusedLocationClient;

    public static final int UPDATE_TIMER = 1726346;
    GoogleMapPresenter mainPresenter;

    public GoogleMapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GoogleMapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GoogleMapFragment newInstance(String param1, String param2) {
        GoogleMapFragment fragment = new GoogleMapFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(com.example.se114_healthcareapplication.R.layout.fragment_google_map, container, false);
        textclock = v.findViewById(com.example.se114_healthcareapplication.R.id.text_clock);
        textclock.setText("0:00");
        clockbtn = v.findViewById(com.example.se114_healthcareapplication.R.id.btn_start);
        clearbtn = v.findViewById(com.example.se114_healthcareapplication.R.id.btn_clear);
        mMap = v.findViewById(com.example.se114_healthcareapplication.R.id.map_view);
        mMap.onCreate(savedInstanceState);
        runlinels = new ArrayList<>();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

        mMap.onResume(); // needed to get the map to display immediately
        latLngList = new ArrayList<>();
        distanceTravelled = 0;
        lastdistance = 0;

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMap.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull @NotNull GoogleMap googleMap) {
                gMap = googleMap;
                gMap.getUiSettings().setZoomControlsEnabled(true);
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getAppActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                    }, 1);
                }
                gMap.setMyLocationEnabled(true);
                fusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        LatLng currentposition = new LatLng(location.getLatitude(),location.getLongitude());
                        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentposition, 19));
                    }
                });
            }
        });
        clockbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mainPresenter.isTimerRunning) {
                    mainPresenter.startClock();
                    Button b = (Button) v;
                    b.setText("Stop");
                } else {
                    mainPresenter.stopClock();
                    Button b = (Button) v;
                    textclock.setText("0:00");
                    b.setText("Start");
                    latLngList.clear();
                    mainPresenter.UpdateRunning(lastdistance);
                    DecimalFormat df = new DecimalFormat("0.00");
                    df.setRoundingMode(RoundingMode.UP);
                    float speed = lastdistance/((System.currentTimeMillis()-mainPresenter.starttime)/1000);
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Great job")
                            .setMessage("You have just travelled " + df.format(lastdistance)
                            +" meters at the average speed of "+df.format(speed) + " m/s")

                            // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            })
                            .setIcon(android.R.drawable.star_big_on)
                            .show();
                    distanceTravelled =0;
                    lastdistance =0;
                }
            }
        });

        clearbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Polyline pline : runlinels){
                    pline.remove();
                }
            }
        });

        setMainPresenter(new GoogleMapPresenter(this));
        return v;
    }

    @Override
    public void UpdateView(int code, Object entity) {
        if (code == UPDATE_TIMER) {
            ArrayList<Integer> lstime = (ArrayList<Integer>) entity;
            int min = lstime.get(0);
            int sec = lstime.get(1);
            textclock.setText(String.format("%d:%02d", min, sec));
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getAppActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                }, 1);
            }
            fusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
                    latLngList.add(latLng);
                    runlinels.add(gMap.addPolyline(new PolylineOptions()
                            .clickable(true)
                            .width(25)
                            .color(R.color.teal_700)
                            .addAll(latLngList)));
                    distanceTravelled = 0;
                    for(int i =0;i<latLngList.stream().count()-1;i++){
                        Location location1 = new Location("tmploaction1");
                        location1.setLatitude(latLngList.get(i).latitude);
                        location1.setLongitude(latLngList.get(i).longitude);

                        Location location2 = new Location("tmplocation2");
                        location2.setLatitude(latLngList.get(i+1).latitude);
                        location2.setLongitude(latLngList.get(i+1).longitude);

                        distanceTravelled+= location1.distanceTo(location2);
                        Log.d("Distance",String.valueOf(distanceTravelled));
                        lastdistance = distanceTravelled;
                    }
                }
            });
        }
    }

    @Override
    public void SwitchView(int code) {

    }

    @Override
    public void setMainPresenter(GoogleMapPresenter presenter) {
        this.mainPresenter = presenter;
    }

    @Override
    public GoogleMapPresenter getMainpresnter() {
        return mainPresenter;
    }

    @Override
    public void StartNewActivity(Intent intent) {
        startActivity(intent);
    }

    @Override
    public Activity getAppActivity() {
        return getActivity();
    }

    @Override
    public Fragment getCurrentFragment() {
        return this;
    }

    @Override
    public FragmentManager GetFragmentManager() {
        return getActivity().getSupportFragmentManager();
    }
}