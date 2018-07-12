package com.android.launcher.integration;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * @author huguojin
 */
public class OmaMobileIDReceiver extends BroadcastReceiver {

    private static final String TAG = "MobileID";

    private static final String ACTION_MOBILE_ID_START = "com.sprint.intent.action.SW_START";
    private static final String ACTION_CHAMELEON_UPDATED = "chameleon.intent.action.PARAM_UPDATED";
    private static final String MOBILE_ID_PKGNAME = "com.sprint.psdg.sw";
    private static final String MOBILE_ID_CLASSNAME = "com.sprint.psdg.sw.receiver.SWStartReceiver";
    private static final String MOBILE_ID_PERMISSION = "com.sprint.permission.RECEIVE_SW_START";
    private static final String TELECOM_PKGNAME = "com.android.server.telecom";
    private static final String TELECOM_CLEAN_CACHE_CLASSNAME = "com.android.server.telecom.AddCleanCacheReceiver";
    private static final String NETWORK_PKGNAME = "com.qualcomm.qti.networksetting";
    private static final String NETWORK_ROAMING_CLASSNAME = "com.qualcomm.qti.networksetting.RoamingSettingReceiver";
    private static final String NEED_START = "need_start";

    public OmaMobileIDReceiver() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: action = " + intent.getAction());
        boolean needStart = intent.getBooleanExtra(NEED_START, false);
        if (needStart) {
            startMobileId(context);
            sendCustomerForceChangeBroadcast(context);
        }
    }

    protected void startMobileId(Context context) {
        Intent i = new Intent(ACTION_MOBILE_ID_START);
        i.setClassName(MOBILE_ID_PKGNAME, MOBILE_ID_CLASSNAME);
        context.sendBroadcast(i, MOBILE_ID_PERMISSION);
    }

    protected void sendCustomerForceChangeBroadcast(Context context) {
        Intent cleanCache = new Intent(ACTION_CHAMELEON_UPDATED);
        cleanCache.setFlags(Intent.FLAG_RECEIVER_FOREGROUND);
        ComponentName cache = new ComponentName(TELECOM_PKGNAME, TELECOM_CLEAN_CACHE_CLASSNAME);
        cleanCache.setComponent(cache);
        Log.i("ChameleonBootReceiver", "ChameleonBootReceiver send PARAM_UPDATED");
        context.sendBroadcast(cleanCache, null);

        Intent roamingSetting = new Intent(ACTION_CHAMELEON_UPDATED);
        roamingSetting.setFlags(Intent.FLAG_RECEIVER_FOREGROUND);
        ComponentName roaming = new ComponentName(NETWORK_PKGNAME, NETWORK_ROAMING_CLASSNAME);
        roamingSetting.setComponent(roaming);
        context.sendBroadcast(roamingSetting, null);

        Intent mobileId = new Intent(ACTION_CHAMELEON_UPDATED);
        mobileId.setFlags(Intent.FLAG_RECEIVER_FOREGROUND);
        ComponentName mobile = new ComponentName(MOBILE_ID_PKGNAME, MOBILE_ID_CLASSNAME);
        mobileId.setComponent(mobile);
        context.sendBroadcast(mobileId, null);
    }
}