package com.donbaka.awesomealarm;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.donbaka.awesomealarm.adapter.AlarmListAdapter;
import com.donbaka.awesomealarm.helper.AwesomeAlarmManager;
import com.donbaka.awesomealarm.model.Alarm;
import com.donbaka.awesomealarm.model.realm.RealmAlarm;
import com.hout.ark.activity.ArkAppCompatActivity;
import com.hout.ark.util.storage.ModelConverter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends ArkAppCompatActivity {
    @BindView(value = R.id.rvAlarm) RecyclerView rvAlarm;
    @BindView(value = R.id.fabAddAlarm) FloatingActionButton fabAddAlarm;

    private AlarmListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    Realm realm;

    private List<Alarm> mAlarms;
    private AlertDialog.Builder alertDialog;
    private final int DIALOG_OVERLAY_PERMISSION = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_main);

        loadUI();
        realm = Realm.getDefaultInstance();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == DIALOG_OVERLAY_PERMISSION) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if(Settings.canDrawOverlays(this)) {

                } else {
                    finish();
                }
            }
        }
    }

    public void checkDrawOverlayPermission() {
        /** check if we already  have permission to draw over other apps */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                /** if not construct intent to request permission */
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
                /** request permission via start activity for result */
                startActivityForResult(intent, DIALOG_OVERLAY_PERMISSION);
            }
        }
    }

    /**
     * Load UI elements of this activity
     */
    private void loadUI() {
        checkDrawOverlayPermission();
        rvAlarm.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        rvAlarm.setLayoutManager(mLayoutManager);

        // specify an adapter
        mAlarms = new ArrayList<>();
        mAdapter = new AlarmListAdapter(mAlarms);

        // set long click listener for list item
        mAdapter.setOnItemLongClickLister(new AlarmListAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClicked(final int position) {
                // delete confirmation will show up if user perform long press on item
                alertDialog = new AlertDialog.Builder(MainActivity.this);
                alertDialog.setMessage(R.string.dialog_delete_alarm)
                        .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // delete data
                                realm.executeTransaction(new Realm.Transaction() {
                                    @Override
                                    public void execute(Realm realm) {
                                        AwesomeAlarmManager.getInstance(MainActivity.this).cancelAlarm(mAlarms.get(position));
                                        RealmAlarm toBeDeleted = realm.where(RealmAlarm.class).equalTo("alarmId", mAlarms.get(position).getAlarmId()).findFirst();
                                        toBeDeleted.deleteFromRealm();
                                        loadData();
                                    }
                                });
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });
                alertDialog.create();

                alertDialog.show();
                return true;
            }
        });
        rvAlarm.setAdapter(mAdapter);

        fabAddAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddAlarmActivity.class));
            }
        });
    }

    /**
     * load alarm data from database
     */
    private void loadData() {
        try {
            RealmResults<RealmAlarm> result = realm.where(RealmAlarm.class).findAll();
            mAlarms = ModelConverter.getInstance().convert(RealmAlarm.class, Alarm.class, realm.copyFromRealm(result));
            mAdapter.update(mAlarms);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
