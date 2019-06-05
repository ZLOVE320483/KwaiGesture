package com.zlove.gesture.app;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

public class KwaiApplication extends Application {

    @Override public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
