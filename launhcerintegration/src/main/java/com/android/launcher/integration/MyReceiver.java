package com.android.launcher.integration;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;

/**
 * @author huguojin
 */
public class MyReceiver extends BroadcastReceiver {

    private static final String TAG = "MyReceiver";
    private static final String PTT_INTENT = "com.kodiak.intent.action.PTT_Button";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: action = " + intent.getAction());
        /*intent.setComponent(new ComponentName("com.android.launcher.integration",
                "com.android.launcher.integration.MyReceiver"));*/
        KeyEvent event = intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT);
        Intent pttIntent = new Intent(PTT_INTENT);
        pttIntent.putExtra(Intent.EXTRA_KEY_EVENT, event);
        pttIntent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        context.sendBroadcast(pttIntent);
    }
}
