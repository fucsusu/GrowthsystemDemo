package com.learn.growthsystemdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.learn.growthsystemdemo.view.BrokenLineVerticalView;
import com.learn.growthsystemdemo.view.BrokenLineView;

public class BrokenLineActivity extends AppCompatActivity {

    public BrokenLineView brokenLineView;
    public BrokenLineVerticalView brokenLineVerticalView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broken_line);
        int[] poinst = {6, 10, 15, 25, 27, 32, 34, 37, 39, 40, 43, 46, 48, 54, 58, 59, 66, 71, 82, 99, 100};

        brokenLineView = findViewById(R.id.brokenline_view);
        brokenLineVerticalView = findViewById(R.id.brokenline_vertical_view);
        brokenLineView.setBrokenLineVerticalView(brokenLineVerticalView);
        brokenLineView.setPoints(poinst);
    }
}
