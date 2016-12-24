package com.example.ahmaadyunus.task4;

import android.os.Bundle;
import android.view.View;

public class DashboardActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View contentView = getLayoutInflater().inflate(R.layout.activity_dashboard,null);
        drawerLayout.addView(contentView,1);
        //toolbar.setTitle(leftSliderData[position]);
    }
}
