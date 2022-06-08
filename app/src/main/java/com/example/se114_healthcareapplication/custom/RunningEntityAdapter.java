package com.example.se114_healthcareapplication.custom;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.se114_healthcareapplication.R;
import com.example.se114_healthcareapplication.model.entity.RunningEntity;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

public class RunningEntityAdapter extends BaseAdapter {
    ArrayList<RunningEntity> runningEntityList;
    Activity activity;

    public RunningEntityAdapter(Activity activity,ArrayList<RunningEntity> runningEntityList) {
        this.activity = activity;
        this.runningEntityList = runningEntityList;
    }

    @Override
    public int getCount() {
        return (int) runningEntityList.stream().count();
    }

    @Override
    public Object getItem(int position) {
        return runningEntityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return runningEntityList.get(position).createdTime;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.running_entity_listview_item, null);
        TextView title = view.findViewById(R.id.item_title);
        TextView distance = view.findViewById(R.id.item_distance);
        TextView time = view.findViewById(R.id.item_time);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E, dd MMM yyyy HH:mm:ss");
        LocalDateTime localtime = LocalDateTime.ofInstant(Instant.ofEpochMilli(runningEntityList.get(position).createdTime), TimeZone.getDefault().toZoneId());
        title.setText(formatter.format(localtime));
        distance.setText(String.valueOf(runningEntityList.get(position).distance)+" m");
        int seconds = (int)runningEntityList.get(position).time/1000;
        int min = seconds/60;
        int sec = seconds%60;
        time.setText(String.format("%d:%02d", min, sec));
        return view;
    }
    public void setRunningEntityList(ArrayList<RunningEntity> ls){
        this.runningEntityList = ls;
    }
}
