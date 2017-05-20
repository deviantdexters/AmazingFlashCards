package com.sahilguptalive.amazingflashcard;

import android.app.Application;

import io.realm.Realm;

/**
 * Created on 20-05-2017.
 */
public class ApplicationClass extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
