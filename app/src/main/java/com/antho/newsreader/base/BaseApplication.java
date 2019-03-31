package com.antho.newsreader.base;

import android.app.Application;

import com.jakewharton.threetenabp.AndroidThreeTen;

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AndroidThreeTen.init(this);
    }
}
