package com.example.se114_healthcareapplication;

import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.webianks.library.scroll_choice.ScrollChoice;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link water_segment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class water_segment extends Fragment  {
List<String> datas = new ArrayList<>();
TextView waterchoiceview;
ScrollChoice waterchoice ;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public water_segment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment water_segment.
     */
    // TODO: Rename and change types and number of parameters
    public static water_segment newInstance(String param1, String param2) {
        water_segment fragment = new water_segment();
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
        initViews();
        loadDatas();
        waterchoice.addItems(datas,3);//default choice
        waterchoice.setOnItemSelectedListener(new ScrollChoice.OnItemSelectedListener() {
            @Override
            public void onItemSelected(ScrollChoice scrollChoice, int position, String name) {


            }
        });
        View v= inflater.inflate(R.layout.fragment_water_segment, container, false);

        return v;
    }
    private void loadDatas(){
        datas.add("0");
        datas.add("200");
        datas.add("400");
        datas.add("600");
        datas.add("800");
        datas.add("1000");
    }
    private void initViews(){
        waterchoice=(ScrollChoice) getView().findViewById(R.id.Scrollchoice123);
    }

}