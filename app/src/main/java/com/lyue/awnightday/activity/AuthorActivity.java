package com.lyue.awnightday.activity;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lyue.awnightday.R;
import com.lyue.awnightday.helper.CapturePhotoHelper;
import com.lyue.awnightday.helper.DayNightHelper;

import java.io.File;


public class AuthorActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST_CODE = 1;
    private DayNightHelper mDayNightHelper;
    private TextView tvLuue;
    private CapturePhotoHelper capturePhotoHelper;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initTheme();
        setContentView(R.layout.activity_author);
        tvLuue = (TextView) findViewById(R.id.tvLuue);
        imageView = (ImageView) findViewById(R.id.imageView);
        _initData();
    }

    private void _initData() {
        tvLuue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capturePhotoHelper.capture();
            }
        });
    }

    private void initData() {
        mDayNightHelper = new DayNightHelper(this);
        // 判断SD卡是否存在。返回true代表存在，false代表不存在；
        // 特别说明：针对不同的Android手机有的厂商没有为手机配置SD卡，像三星有几款手机不具有拓展内存的。
        // 废话再多一点补充：
        // 这里要区别一下SD卡，外部存储卡，内部存储卡，运行内存这些都是不同的概念。不是特别理解的同学请查一下google或者关注我的博客里面有一篇文章是介绍这些概念的。
        // 比如说我要写一个相册的程序，图片肯定是存在外部的存储卡中，而如果我需要的是存储一些配置信息则是放在内部存储卡中。
        //操作一个文件（读写，创建文件或者目录）是通过File类来完成的，这个操作和java中完全一致。
//        外部存储external storage和内部存储internal storage
        if (Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            File skRoot = Environment.getExternalStorageDirectory();
            capturePhotoHelper = new CapturePhotoHelper(this, skRoot);
        } else {
            System.out.println("没有sd卡");
        }

    }

    private void initTheme() {
        if (mDayNightHelper.isDay()) {
            setTheme(R.style.DayTheme);
        } else {
            setTheme(R.style.NightTheme);
        }
    }
}
