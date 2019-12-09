package com.learn.growthsystemdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.learn.growthsystemdemo.view.DirectionTransformer;
import com.learn.growthsystemdemo.view.ScaleTransformer;

public class PageActivity extends AppCompatActivity {

    public ViewPager mViewPager1;
    public ViewPager mViewPager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);

        mViewPager1 = findViewById(R.id.page_viewpager1);
        mViewPager1.setAdapter(new DemoPagerAdapt(this));
        mViewPager1.setOffscreenPageLimit(2);
        ScaleTransformer transformer = new ScaleTransformer();
        mViewPager1.setPageTransformer(false, transformer);

        mViewPager2 = findViewById(R.id.page_viewpager2);
        mViewPager2.setAdapter(new DemoPagerAdapt(this));

        DirectionTransformer transformer1 = new DirectionTransformer();
        mViewPager2.addOnPageChangeListener(transformer1.getOnPageChangeListener());
        mViewPager2.setPageTransformer(true, transformer1);
    }
}
