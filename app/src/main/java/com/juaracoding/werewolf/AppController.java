package com.juaracoding.werewolf;

import android.app.Application;

import com.raizlabs.android.dbflow.annotation.Database;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

@Database(name = AppController.NAME, version = AppController.VERSION)
public class AppController extends Application {

    public static final String NAME = "Werewolf";
    public static final int VERSION = 2 ;

    @Override
    public void onCreate() {
        super.onCreate();
        FlowManager.init(new FlowConfig.Builder(this).build());
    }
}
