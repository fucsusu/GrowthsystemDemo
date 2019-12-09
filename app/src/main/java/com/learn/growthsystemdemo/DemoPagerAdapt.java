package com.learn.growthsystemdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.learn.growthsystemdemo.R;

/**
 * Created by fucc
 * Date: 2019-12-05 14:43
 */
public class DemoPagerAdapt extends PagerAdapter {
    private Context context;
    public final LayoutInflater from;

    public DemoPagerAdapt(Context context) {
        this.context = context;
        from = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View item = from.inflate(R.layout.item_pager_demo, container, false);
        TextView content = item.findViewById(R.id.item_pager_txt);
        content.setText("index:" + position);
        item.setTag(position);
        item.setAlpha(0);
        container.addView(item, 0);
        return item;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
