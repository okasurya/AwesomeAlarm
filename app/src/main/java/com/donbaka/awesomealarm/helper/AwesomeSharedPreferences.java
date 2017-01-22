package com.donbaka.awesomealarm.helper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharedPreference Helper
 * Created by brlnt on 1/22/17.
 */

public class AwesomeSharedPreferences {

    private static AwesomeSharedPreferences instance;
    private static SharedPreferences pref;
    private static SharedPreferences.Editor editor;

    private AwesomeSharedPreferences() {

    }

    /**
     * initiate the Shared Preference
     * @param context
     * @return
     */
    public static AwesomeSharedPreferences init(Context context) {
        if (instance == null) {
            instance = new AwesomeSharedPreferences();
        }

        pref = context.getSharedPreferences("AwesomeAlarm", 0); // 0 - for private mode
        editor = pref.edit();

        return instance;
    }

    /**
     * store String data to shared preferences
     *
     * @param key identifier of the data
     * @param data data that will be stored
     */
    public void store(String key, String data) {
        editor.putString(key, data);
        editor.commit();
    }

    /**
     * store int data to shared preferences
     *
     * @param key identifier of the data
     * @param data data that will be stored
     */
    public void store(String key, int data) {
        editor.putInt(key, data);
        editor.commit();
    }

    /**
     * store long data to shared preferences
     *
     * @param key identifier of the data
     * @param data data that will be stored
     */
    public void store(String key, long data) {
        editor.putLong(key, data);
        editor.commit();
    }

    /**
     * store float data to shared preferences
     *
     * @param key identifier of the data
     * @param data data that will be stored
     */
    public void store(String key, float data) {
        editor.putFloat(key, data);
        editor.commit();
    }

    /**
     * store boolean data to shared preferences
     *
     * @param key identifier of the data
     * @param data data that will be stored
     */
    public void store(String key, boolean data) {
        editor.putBoolean(key, data);
        editor.commit();
    }

    /**
     * Get string data from spesific identifier (key)
     * @param key identifier of the data
     * @param defaultValue the default value if data couldnt be found
     * @return expected data
     */
    public String get(String key, String defaultValue) {
        return pref.getString(key, defaultValue);
    }

    /**
     * Get int data from spesific identifier (key)
     * @param key identifier of the data
     * @param defaultValue the default value if data couldnt be found
     * @return expected data
     */
    public int get(String key, int defaultValue) {
        return pref.getInt(key, defaultValue);
    }

    /**
     * Get long data from spesific identifier (key)
     * @param key identifier of the data
     * @param defaultValue the default value if data couldnt be found
     * @return expected data
     */
    public long get(String key, long defaultValue) {
        return pref.getLong(key, defaultValue);
    }

    /**
     * Get float data from spesific identifier (key)
     * @param key identifier of the data
     * @param defaultValue the default value if data couldnt be found
     * @return expected data
     */
    public float get(String key, float defaultValue) {
        return pref.getFloat(key, defaultValue);
    }

    /**
     * Get boolean data from spesific identifier (key)
     * @param key identifier of the data
     * @param defaultValue the default value if data couldnt be found
     * @return expected data
     */
    public boolean get(String key, boolean defaultValue) {
        return pref.getBoolean(key, defaultValue);
    }


    /**
     * remove data from shared preferences
     * @param key identifier of the data
     */
    public void remove(String key) {
        editor.remove(key);
        editor.commit();
    }
}
