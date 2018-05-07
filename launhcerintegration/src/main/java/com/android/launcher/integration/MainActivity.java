package com.android.launcher.integration;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.NetworkOnMainThreadException;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private NetworkAync mNetworkAync;
    private SmsAync mSmsAync;
    private CallAync mCallAync;
    private H mH;

    private static final String SAMPLE_URL = "www.baidu.com";
    private static final String TAG = "MainActivity";

    private static final int MSG_VALIDATE_NETWORK_DONE = 1;
    private static final int MSG_VALIDATE_SMS_DONE = 2;
    private static final int MSG_VALIDATE_CALL_DONE = 3;

    private static final int MSG_RESULT_TRUE = 1;
    private static final int MSG_RESUTL_FALSE = -1;


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
    }

    @Override
    protected void onResume() {
        super.onResume();
        mNetworkAync = new NetworkAync(MainActivity.this);
        mSmsAync = new SmsAync(MainActivity.this);
        mCallAync = new CallAync(MainActivity.this);
        mH = new H(MainActivity.this);

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
            mSmsAync.execute(mdnNum);
        }
    }

    protected void handleReceiveMessage(Message msg) {
        int what = msg.what;
        boolean result = msg.arg1 > 0;
        switch (what) {
            case MSG_VALIDATE_NETWORK_DONE:
                break;
            case MSG_VALIDATE_SMS_DONE:
                break;
            case MSG_VALIDATE_CALL_DONE:
                break;
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
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }

    private static boolean touchNetwork(String original) {
        try {
            URL url = new URL(original);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            return connection.getResponseCode() == HttpURLConnection.HTTP_OK;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "exception occurs:" + e.getMessage(), e);
            return false;
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

    protected boolean isCellularConnect(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm != null ? cm.getActiveNetworkInfo() : null;
        return null != ni && ni.getType() == ConnectivityManager.TYPE_MOBILE;
    }

    protected boolean isWifiConnect(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm != null ? cm.getActiveNetworkInfo() : null;
        return null != ni && ni.getType() == ConnectivityManager.TYPE_WIFI;
    }

    protected boolean isDataAvailable(Context context) {
        return isWifiConnect(context) || isCellularConnect(context);
    }

}
