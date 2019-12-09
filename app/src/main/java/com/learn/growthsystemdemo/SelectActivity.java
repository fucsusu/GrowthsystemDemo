package com.learn.growthsystemdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
    }

    public void selectBtn(View view) {
        switch (view.getId()) {
            case R.id.select_brokenline:
                startActivity(new Intent(this, BrokenLineActivity.class));
                break;
            case R.id.select_column:
                startActivity(new Intent(this, ColumnActivity.class));
                break;
            case R.id.select_page:
                startActivity(new Intent(this, PageActivity.class));
                break;
            case R.id.select_recycle:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }
}
