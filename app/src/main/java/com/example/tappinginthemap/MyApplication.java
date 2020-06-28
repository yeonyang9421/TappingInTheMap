package com.example.tappinginthemap;

import android.app.Application;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
//        Realm.init(this);
//        RealmConfiguration config2 = new RealmConfiguration.Builder()
//                .deleteRealmIfMigrationNeeded()
//                .name("savedLocation.realm")
//                .build();
//        Realm.setDefaultConfiguration(config2);
//        Realm = Realm.getInstance(config2);

    }
}
