package com.android.launcher.integration;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;

/**
 * @author huguojin
 */
public class FunctionActivity extends AppCompatActivity {

    private static final String TAG = "FunctionActivity";

    private MyReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        int keycode_ptt = KeyEvent.keyCodeFromString("KEYCODE_PTT");
        int keycode_ptt_key = KeyEvent.keyCodeFromString("KEYCODE_PTT_KEY");
        Log.d("Vo7ice","keycode_ptt = " + keycode_ptt + "keycode_ptt_key = " + keycode_ptt_key);
        String app_name = getResources().getString(R.string.app_name);
        ContextCompat.getDrawable(getBaseContext() ,R.drawable.answer_hangup);
        IntentFilter filter = new IntentFilter("com.kodiak.intent.action.PTT_Button");
        mReceiver = new MyReceiver();
        getBaseContext().registerReceiver(mReceiver,filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        getBaseContext().unregisterReceiver(mReceiver);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "onReceive: action = " + intent.getAction());
        }
    }
}
