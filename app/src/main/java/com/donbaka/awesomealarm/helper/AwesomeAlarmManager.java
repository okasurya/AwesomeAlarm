package com.donbaka.awesomealarm.helper;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.donbaka.awesomealarm.model.Alarm;
import com.donbaka.awesomealarm.service.AwesomeAlarmService;

import java.util.Calendar;

/**
 * Manage alarm manager for awesome alarm
 * Created by brlnt on 1/22/17.
 */

public class AwesomeAlarmManager {

    private static AwesomeAlarmManager instance;

    private AwesomeAlarmManager() {

    }

    /**
     * Initiate the alarm manager
     * @param context
     * @param alarm
     * @return
     */
    public static AwesomeAlarmManager getInstance() {
        if(instance == null) {
            instance = new AwesomeAlarmManager();
        }

        return instance;
    }

    /**
     * get alarm service from system
     * @return AlarmManager from system
     */
    private AlarmManager getAlarmManager(Context context) {
        return (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    /**
     * get Pending Intent for alarm
     * @return PendingIntent
     */
    private PendingIntent getAlarmIntent(Context context, Alarm alarm) {
        Intent intent = new Intent(context, AwesomeAlarmService.class);
        intent.setAction(Constant.START_ALARM);
        intent.putExtra(Constant.ID, alarm.getAlarmId());

        return PendingIntent.getService(context, alarm.getAlarmId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    /**
     * register alarm data to alarm manager
     */
    public void setAlarm(Context context, Alarm alarm) {
        Calendar alarmTime = Calendar.getInstance();
        alarmTime.set(Calendar.HOUR_OF_DAY, alarm.getHour());
        alarmTime.set(Calendar.MINUTE, alarm.getMinute());
        alarmTime.set(Calendar.SECOND, 0);
        alarmTime.set(Calendar.MILLISECOND, 0);

        Calendar now = Calendar.getInstance();
        if(alarmTime.before(now)) {
            alarmTime.add(Calendar.DAY_OF_MONTH, 1);
        }

        getAlarmManager(context).set(AlarmManager.RTC_WAKEUP, alarmTime.getTimeInMillis(), getAlarmIntent(context, alarm));
    }

    /**
     * delete alarm from alarm manager
     * @param context
     * @param alarm
     */
    public void cancelAlarm(Context context, Alarm alarm) {
        getAlarmManager(context).cancel(getAlarmIntent(context, alarm));
    }


}
