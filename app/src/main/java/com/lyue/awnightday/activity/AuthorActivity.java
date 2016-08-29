package com.lyue.awnightday.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lyue.awnightday.R;
import com.lyue.awnightday.helper.DayNightHelper;


public class AuthorActivity extends AppCompatActivity {

    private DayNightHelper mDayNightHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initTheme();
        setContentView(R.layout.activity_author);
    }

    private void initData() {
        mDayNightHelper = new DayNightHelper(this);
    }

    private void initTheme() {
        if (mDayNightHelper.isDay()) {
            setTheme(R.style.DayTheme);
        } else {
            setTheme(R.style.NightTheme);
        }
    }
}
