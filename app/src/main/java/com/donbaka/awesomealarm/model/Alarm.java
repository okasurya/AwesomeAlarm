package com.donbaka.awesomealarm.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by brlnt on 1/21/17.
 */

public class Alarm implements Parcelable {
    public int alarmId;
    public String alarmTitle;
    public int hour;
    public int minute;
    public int tone;

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

    public int getTone() {
        return tone;
    }

    public void setTone(int tone) {
        this.tone = tone;
    }

    public Alarm() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.alarmId);
        dest.writeString(this.alarmTitle);
        dest.writeInt(this.hour);
        dest.writeInt(this.minute);
        dest.writeInt(this.tone);
    }

    protected Alarm(Parcel in) {
        this.alarmId = in.readInt();
        this.alarmTitle = in.readString();
        this.hour = in.readInt();
        this.minute = in.readInt();
        this.tone = in.readInt();
    }

    public static final Creator<Alarm> CREATOR = new Creator<Alarm>() {
        @Override
        public Alarm createFromParcel(Parcel source) {
            return new Alarm(source);
        }

        @Override
        public Alarm[] newArray(int size) {
            return new Alarm[size];
        }
    };
}
