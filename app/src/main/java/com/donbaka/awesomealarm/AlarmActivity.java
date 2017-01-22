package com.donbaka.awesomealarm;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.donbaka.awesomealarm.helper.Constant;
import com.donbaka.awesomealarm.model.realm.RealmAlarm;
import com.hout.ark.activity.ArkAppCompatActivity;

import java.io.IOException;

import butterknife.BindView;
import io.realm.Realm;

/**
 * Activity for showing ongoing alarm
 */
public class AlarmActivity extends ArkAppCompatActivity {
    @BindView(R.id.tvTitle) TextView tvTitle;
    @BindView(R.id.fabClose) FloatingActionButton fabClose;

    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_alarm);

        loadUI();
        loadData();
    }

    /**
     * load ui elements
     */
    private void loadUI() {
        fabClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                finish();
            }
        });
    }

    /**
     * load data from database
     */
    private void loadData() {
        try {
            int id = getIntent().getIntExtra(Constant.ID, 0);
            Realm realm = Realm.getDefaultInstance();
            RealmAlarm result = realm.where(RealmAlarm.class).equalTo("alarmId", id).findFirst();
            tvTitle.setText(result.getAlarmTitle());

            startAlarm();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * start alarm and play it sound
     */
    private void startAlarm() {
        // set max volume
        final AudioManager mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        mAudioManager.setStreamVolume(AudioManager.STREAM_ALARM, mAudioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM), 0);

        // start the sound
        mediaPlayer = MediaPlayer.create(this, R.raw.tornado_siren);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }
}
