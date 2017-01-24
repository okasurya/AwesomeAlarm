package com.donbaka.awesomealarm.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.donbaka.awesomealarm.AlarmActivity;
import com.donbaka.awesomealarm.helper.AwesomeAlarmManager;
import com.donbaka.awesomealarm.helper.Constant;
import com.donbaka.awesomealarm.model.Alarm;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 */
public class AwesomeAlarmService extends IntentService {
    private Alarm alarm;

    public AwesomeAlarmService() {
        super("AwesomeAlarmService");
    }

    /**
     * Starts this service to perform action startAlarm with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startAlarmService(Context context, Alarm alarm) {
        Intent intent = new Intent(context, AwesomeAlarmService.class);
        intent.setAction(Constant.START_ALARM);
        intent.putExtra(Constant.ALARM, alarm);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (action.equals(Constant.START_ALARM)) {
                alarm = intent.getParcelableExtra(Constant.ALARM);
                handleStartAlarm();
            }
        }
    }

    /**
     * Call AlarmActivity and set alarm for next day
     * @param alarm
     */
    private void handleStartAlarm() {
        try {
            Intent intent = new Intent(this, AlarmActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(Constant.ALARM, alarm);
            startActivity(intent);

            AwesomeAlarmManager.getInstance(this).setAlarm(alarm);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
