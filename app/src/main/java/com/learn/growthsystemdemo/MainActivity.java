package com.learn.growthsystemdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.learn.growthsystemdemo.view.MyQuadView;
import com.learn.growthsystemdemo.view.ScaleLayoutManger;
import com.learn.growthsystemdemo.view.BrokenLineView;

public class MainActivity extends AppCompatActivity {

    public RecyclerView mRecycle;
    public MyQuadView myQuadView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecycle = findViewById(R.id.test_recycleview);
        mRecycle.setAdapter(new ScaleAdapter(this));
        final ScaleLayoutManger scaleLayoutManger = new ScaleLayoutManger();
        mRecycle.setLayoutManager(scaleLayoutManger);
        mRecycle.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        scaleLayoutManger.startScroll();
                        break;
                }
                return false;
            }
        });
        new PagerSnapHelper().attachToRecyclerView(mRecycle);

        myQuadView = findViewById(R.id.test_quadView);
        myQuadView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myQuadView.startAnim();
            }
        });

    }
}
