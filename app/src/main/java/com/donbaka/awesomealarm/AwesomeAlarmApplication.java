package com.donbaka.awesomealarm;

import android.app.Application;

import com.upyhca.stethorealm.RealmInspectorModulesProvider;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by brlnt on 1/21/17.
 */

public class AwesomeAlarmApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);

        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("Alarm.realm")
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();

        Realm.setDefaultConfiguration(config);
    }
}
