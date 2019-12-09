package com.learn.growthsystemdemo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by fucc
 * Date: 2019-12-02 13:14
 */
public class ScaleAdapter extends RecyclerView.Adapter<ScaleAdapter.ScaleViewHolder> {

    private Context context;

    private int binds = 0;
    public final LayoutInflater mFrom;

    public ScaleAdapter(Context context) {
        this.context = context;
        mFrom = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ScaleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = mFrom.inflate(R.layout.item_scale, parent, false);
        return new ScaleViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ScaleViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    static class ScaleViewHolder extends RecyclerView.ViewHolder {

        public ImageView scaleImg;

        public ScaleViewHolder(@NonNull View itemView) {
            super(itemView);
            scaleImg = itemView.findViewById(R.id.item_scale_img);
        }
    }
}
