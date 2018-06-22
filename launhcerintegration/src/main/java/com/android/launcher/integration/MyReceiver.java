package com.android.launcher.integration;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * @author huguojin
 */
public class MyReceiver extends BroadcastReceiver {

    private static final String TAG = "MyReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: action = " + intent.getAction());
        /*intent.setComponent(new ComponentName("com.android.launcher.integration",
                "com.android.launcher.integration.MyReceiver"));*/
    }
}
