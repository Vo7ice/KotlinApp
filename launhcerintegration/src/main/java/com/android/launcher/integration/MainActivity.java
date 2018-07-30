package com.android.launcher.integration;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private NetworkAync mNetworkAync;
    private SmsAync mSmsAync;
    private CallAync mCallAync;
    private H mH;

    private TextView textView;

    private static final String SAMPLE_URL = "www.baidu.com";
    private static final String TAG = "MainActivity";

    private static final int MSG_VALIDATE_NETWORK_DONE = 1;
    private static final int MSG_VALIDATE_SMS_DONE = 2;
    private static final int MSG_VALIDATE_CALL_DONE = 3;
    private static final int MSG_NETWORK_AVAILABLE_NOW = 4;
    private static final int MSG_TERMINATE_ALL = 5;

    private static final int MSG_RESULT_TRUE = 1;
    private static final int MSG_RESULT_NOT_PERFORM = 0;
    private static final int MSG_RESUTL_FALSE = -1;

    private static final long NETWORK_GAP = 1000;

    private static final String AUTHORITY = "com.android.launcher3.settings";
    private static final String TABLE_NAME = "favorites";

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);

    Timer mTimer;

    private ScheduledExecutorService mExecutorService;
    private RepeatTask mRpeatTask;
    private TerminateTask mTerminateTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        textView = findViewById(R.id.test_call);
        SpannableString span = new SpannableString(getResources().getString(R.string.app_name));
        span.setSpan(new URLSpan("tel:18885460314"), 0, span.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(span);
        textView.setMovementMethod(LinkMovementMethod.getInstance());

        mExecutorService = Executors.newScheduledThreadPool(2);


    }

    @Override
    protected void onResume() {
        super.onResume();
        mNetworkAync = new NetworkAync(MainActivity.this);
        mSmsAync = new SmsAync(MainActivity.this);
        mCallAync = new CallAync(MainActivity.this);
        mH = new H(MainActivity.this);
        mRpeatTask = new RepeatTask(mH, getBaseContext());
        mTerminateTask = new TerminateTask(mH);
        if (!mExecutorService.isShutdown()) {
            mExecutorService.scheduleAtFixedRate(mRpeatTask, 1, 150, TimeUnit.MICROSECONDS);
            mExecutorService.scheduleAtFixedRate(mTerminateTask, 2000, 1, TimeUnit.MICROSECONDS);
        }


        TelephonyManager tm = (TelephonyManager) MainActivity.this.getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        String mdnNum = tm != null ? tm.getLine1Number() : null;

        if (isCellularConnect(MainActivity.this)) {
            mNetworkAync.execute(SAMPLE_URL);
        } else {
            Message msg = mH.obtainMessage(MSG_VALIDATE_NETWORK_DONE);
            msg.arg1 = MSG_RESULT_NOT_PERFORM;
            mH.sendMessageDelayed(msg, NETWORK_GAP);
            mSmsAync.execute(mdnNum);
        }
        doSomthing();

        String selection = "itemType" + " = ? " + " AND " + " title = ?";
        String[] selectionArgs = new String[]{"0", "Voicemail"};
        ContentResolver resolver = getBaseContext().getContentResolver();
        Cursor cursor = resolver.query(CONTENT_URI, null, selection, selectionArgs, "_id DESC");
        if (null == cursor) {
            Log.d("Vo7ice", "cursor is null");
        } else {
            Log.d("Vo7ice", "cursor size is " + cursor.getCount());
        }
        long id = 0L;
        if (null != cursor && cursor.moveToFirst()) {
            id = cursor.getLong(cursor.getColumnIndex("_id"));
        }
        if (id != 0L) {
            String where = "_id = ?";
            String[] ids = new String[]{String.valueOf(id)};
            ContentValues values = new ContentValues();
            values.put("cellX", 3);
            values.put("cellY", 3);
            int update = resolver.update(CONTENT_URI, values, where, ids);
            int delete = resolver.delete(CONTENT_URI, where, ids);
        }

        Resources resources = getBaseContext().getResources();


    }

    private static void doSomthing() {

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!mNetworkAync.isCancelled()) {
            mNetworkAync.cancel(true);
        }
        if (!mExecutorService.isTerminated() || !mExecutorService.isShutdown()) {
            mExecutorService.shutdownNow();
        }
    }

    protected void handleReceiveMessage(Message msg) {
        int what = msg.what;
        Log.d("Vo7ice", "receive message" + what);
        switch (what) {
            case MSG_VALIDATE_NETWORK_DONE:
                msg.what = MSG_VALIDATE_SMS_DONE;
                msg.arg1 = MSG_RESUTL_FALSE;
                mH.sendMessageDelayed(msg, 1000);
                break;
            case MSG_VALIDATE_SMS_DONE:
                break;
            case MSG_VALIDATE_CALL_DONE:
                break;
            case MSG_NETWORK_AVAILABLE_NOW:
            case MSG_TERMINATE_ALL:
                Log.d("Vo7ice", " !mExecutorService.isShutdown() = " + !mExecutorService.isShutdown());
                Log.d("Vo7ice", "isTerminated = " + mExecutorService.isTerminated());
                if (mExecutorService != null && !mExecutorService.isShutdown()) {
                    mExecutorService.shutdownNow();
                }
                break;
            default:
                break;
        }
    }

    protected void sendMessage(int what, int arg1) {
        Message msg = mH.obtainMessage(what);
        msg.arg1 = arg1;
        mH.sendMessage(msg);
    }

    private class SmsDeliverReceiver extends BroadcastReceiver {

        private H mHandler;

        public SmsDeliverReceiver(H handler) {
            mHandler = handler;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "deliver success!");
            if (null != mNetworkAync && mNetworkAync.getStatus() != AsyncTask.Status.RUNNING) {
                mNetworkAync.execute(SAMPLE_URL);
            }
            Message msg = mHandler.obtainMessage(MSG_VALIDATE_SMS_DONE);
            switch (getResultCode()) {
                case Activity.RESULT_OK:
                    Log.d(TAG, "RESULT_OK");
                    msg.arg1 = MSG_RESULT_TRUE;
                    mHandler.sendMessageDelayed(msg, NETWORK_GAP);
                    break;
                case Activity.RESULT_CANCELED:
                    Log.d(TAG, "RESULT_CANCELED");
                    msg.arg1 = MSG_RESUTL_FALSE;
                    mHandler.sendMessageDelayed(msg, NETWORK_GAP);
                    break;
                default:
                    break;
            }
            // unRegisterReceiver();
        }
    }

    public static class RepeatTask implements Runnable {

        private H mH;
        private Context mContext;
        private int count;

        public RepeatTask(H h, Context context) {
            mH = h;
            mContext = context;
        }

        @Override
        public void run() {
            count++;
            Log.d("Vo7ice", "count = " + count + ", isDataAvailable(mContext) = " + isDataAvailable(mContext));
            if (isDataAvailable(mContext)) {
                mH.sendEmptyMessage(MSG_NETWORK_AVAILABLE_NOW);
            }
        }
    }

    public static class TerminateTask implements Runnable {

        private H mH;

        public TerminateTask(H h) {
            mH = h;
        }

        @Override
        public void run() {
            Log.d("Vo7ice", "Terminate");
            mH.sendEmptyMessage(MSG_TERMINATE_ALL);
        }
    }

    public static class H extends Handler {
        private WeakReference<MainActivity> mActivity;

        public H(MainActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (mActivity.get() != null) {
                mActivity.get().handleReceiveMessage(msg);
            }
        }
    }

    public static class NetworkAync extends AsyncTask<String, Integer, Boolean> {

        private WeakReference<Activity> weakActivity;

        public NetworkAync(Activity activity) {
            this.weakActivity = new WeakReference<>(activity);
        }

        @Override
        protected Boolean doInBackground(String... parms) {
            String url = parms[0];
            boolean result = touchNetwork(url);
            try {
                Thread.sleep(NETWORK_GAP);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            MainActivity activity;

            if ((activity = (MainActivity) weakActivity.get()) != null) {
                activity.sendMessage(MSG_VALIDATE_NETWORK_DONE, result ? MSG_RESULT_TRUE : MSG_RESUTL_FALSE);
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        private static boolean touchNetwork(String original) {
            try {
                URL url = new URL(original);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(6000);
                connection.connect();
                boolean result = connection.getResponseCode() == HttpURLConnection.HTTP_OK;
                connection.disconnect();
                return result;
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, "exception occurs:" + e.getMessage(), e);
                return false;
            }
        }
    }


    public static class SmsAync extends AsyncTask<String, Integer, Boolean> {

        private WeakReference<Activity> weakActivity;

        public SmsAync(Activity activity) {
            this.weakActivity = new WeakReference<>(activity);
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
        }
    }

    public static class CallAync extends AsyncTask<String, Integer, Boolean> {

        private WeakReference<Activity> weakActivity;

        public CallAync(Activity activity) {
            this.weakActivity = new WeakReference<>(activity);
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
        }
    }

    protected static boolean isCellularConnect(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm != null ? cm.getActiveNetworkInfo() : null;
        return null != ni && ni.getType() == ConnectivityManager.TYPE_MOBILE;
    }

    protected static boolean isWifiConnect(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm != null ? cm.getActiveNetworkInfo() : null;
        return null != ni && ni.getType() == ConnectivityManager.TYPE_WIFI;
    }

    protected static boolean isDataAvailable(Context context) {
        return isWifiConnect(context) || isCellularConnect(context);
    }

}
