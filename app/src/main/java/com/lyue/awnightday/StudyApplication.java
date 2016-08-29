package com.lyue.awnightday;

import android.app.Application;

import com.clock.utils.crash.CrashExceptionHandler;
import com.lyue.awnightday.manager.FolderManager;

/**
 * Created by Clock on 2016/5/13.
 */
public class StudyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        configCollectCrashInfo();
    }

    /**
     * 配置奔溃信息的搜集
     */
    private void configCollectCrashInfo() {
        CrashExceptionHandler crashExceptionHandler = new CrashExceptionHandler(this, FolderManager.getCrashLogFolder());
        Thread.setDefaultUncaughtExceptionHandler(crashExceptionHandler);
    }
}
