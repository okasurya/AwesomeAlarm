package com.donbaka.awesomealarm.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

import com.donbaka.awesomealarm.AlarmActivity;
import com.donbaka.awesomealarm.helper.AwesomeAlarmManager;
import com.donbaka.awesomealarm.helper.Constant;
import com.donbaka.awesomealarm.model.Alarm;
import com.donbaka.awesomealarm.model.realm.RealmAlarm;
import com.hout.ark.util.storage.ModelConverter;

import io.realm.Realm;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 */
public class AwesomeAlarmService extends IntentService {
    public AwesomeAlarmService() {
        super("AwesomeAlarmService");
    }

    /**
     * Starts this service to perform action startAlarm with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startAlarm(Context context, int id) {
        Intent intent = new Intent(context, AwesomeAlarmService.class);
        intent.setAction(Constant.START_ALARM);
        intent.putExtra(Constant.ID, id);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (action.equals(Constant.START_ALARM)) {
                final int id = intent.getIntExtra(Constant.ID, 0);
                handleStartAlarm(id);
            }
        }
    }

    /**
     * Call AlarmActivity and set alarm for next day
     * @param id
     */
    private void handleStartAlarm(int id) {
        try {
            // start Activity
            Intent intent = new Intent(this, AlarmActivity.class);
            intent.putExtra(Constant.ID, id);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

            // reproduce nextAlarm
            Realm realm = Realm.getDefaultInstance();
            RealmAlarm result = realm.where(RealmAlarm.class).equalTo("alarmId", id).findFirst();

            Alarm alarm = (Alarm) ModelConverter.getInstance().convert(RealmAlarm.class, Alarm.class, result);
            AwesomeAlarmManager.getInstance().setAlarm(this, alarm);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
