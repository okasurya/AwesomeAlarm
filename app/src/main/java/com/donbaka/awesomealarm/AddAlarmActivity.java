package com.donbaka.awesomealarm;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.donbaka.awesomealarm.helper.AwesomeAlarmManager;
import com.donbaka.awesomealarm.helper.Util;
import com.donbaka.awesomealarm.model.Alarm;
import com.donbaka.awesomealarm.model.UniqueId;
import com.donbaka.awesomealarm.model.realm.RealmAlarm;
import com.hout.ark.activity.ArkAppCompatActivity;
import com.hout.ark.util.storage.ModelConverter;

import java.util.Calendar;

import butterknife.BindView;
import io.realm.Realm;

public class AddAlarmActivity extends ArkAppCompatActivity {
    @BindView(value = R.id.etTitle) TextView etTitle;
    @BindView(value = R.id.etTime) TextView etTime;
    @BindView(value = R.id.spAlarmTone) Spinner spAlarmTone;

    TimePickerDialog timePickerDialog;
    Calendar cal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_add_alarm);

        loadUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_alarm_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuSaveAlarm :
                save();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * load ui elements
     */
    private void loadUI() {
        cal = Calendar.getInstance();
        timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                cal.set(Calendar.HOUR_OF_DAY, i);
                cal.set(Calendar.MINUTE, i1);
                etTime.setText(Util.TIME_FORMAT1.format(cal.getTime()));
            }
        }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true);

        etTime.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                timePickerDialog.show();
                return true;
            }
        });
    }

    /**
     * save alarm data to database
     */
    private void save() {
        if(isValid()) {
            Realm realm = Realm.getDefaultInstance();
            try {
                RealmAlarm alarm = new RealmAlarm();
                alarm.setAlarmId(UniqueId.generate(this));
                alarm.setAlarmTitle(etTitle.getText().toString());
                alarm.setHour(cal.get(Calendar.HOUR_OF_DAY));
                alarm.setMinute(cal.get(Calendar.MINUTE));

                realm.beginTransaction();
                realm.copyToRealm(alarm);
                realm.commitTransaction();

                setAlarm((Alarm) ModelConverter.getInstance().convert(RealmAlarm.class, Alarm.class, alarm));
                finish();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * set alarm in alarm manager
     * @param alarm the data the will be set in alarm manager
     */
    private void setAlarm(Alarm alarm) {
        AwesomeAlarmManager.getInstance(this).setAlarm(alarm);
    }

    /**
     * perform validation process through all form fields
     *
     * @return status of validation
     */
    // TODO: 1/23/17 need to complete this feature
    private boolean isValid() {
        return true;
    }
}
