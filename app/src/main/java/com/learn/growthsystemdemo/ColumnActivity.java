package com.learn.growthsystemdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.learn.growthsystemdemo.bean.DataBean;
import com.learn.growthsystemdemo.view.ColumnView;

import java.util.ArrayList;

public class ColumnActivity extends AppCompatActivity {


    public ColumnView columnView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_column);

        ArrayList<DataBean> dataBeans = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DataBean dataBean = new DataBean("L1-" + i, i * 7, i * 9);
            dataBeans.add(dataBean);
        }
        columnView = findViewById(R.id.column_view);
        columnView.addDataBeans(dataBeans);
    }
}
