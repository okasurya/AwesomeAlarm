package com.donbaka.awesomealarm.model;

import android.content.Context;

import com.donbaka.awesomealarm.helper.AwesomeSharedPreferences;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by brlnt on 1/22/17.
 */

public class UniqueId {
    private static AtomicInteger id;
    private static final String LAST_ALARM_ID = "com.donbaka.awesomealarm.UniqueId.LAST_ALARM_ID";

    private UniqueId() {

    }

    public static int generate(Context context) {
        if(id == null) {
            id = new AtomicInteger(AwesomeSharedPreferences.init(context).get(LAST_ALARM_ID, 0));
        }
        int uniqueId = id.incrementAndGet();
        AwesomeSharedPreferences.init(context).store(LAST_ALARM_ID, uniqueId);

        return uniqueId;
    }
}
