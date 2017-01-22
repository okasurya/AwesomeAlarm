package com.donbaka.awesomealarm.model.realm;

import io.realm.RealmObject;

/**
 * Created by brlnt on 1/21/17.
 */

public class RealmAlarm extends RealmObject {
    public int alarmId;
    public String alarmTitle;
    public int hour;
    public int minute;

    public int getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(int alarmId) {
        this.alarmId = alarmId;
    }

    public String getAlarmTitle() {
        return alarmTitle;
    }

    public void setAlarmTitle(String alarmTitle) {
        this.alarmTitle = alarmTitle;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }
}
