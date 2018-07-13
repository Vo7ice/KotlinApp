package com.android.launcher.integration;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class NotificationActivity extends AppCompatActivity {

    private MyAsyncTask mMyAsyncTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        String[] param = new String[] {"test title", "test summary"};
        mMyAsyncTask = new MyAsyncTask(NotificationActivity.this);
        mMyAsyncTask.execute(param);
    }

    private class MyAsyncTask extends AsyncTask<String, String, Boolean> {

        private Context mContext;
        public MyAsyncTask(Context context) {
            mContext = context;
        }

        @Override
        protected Boolean doInBackground(String... params) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            NotificationUtils notificationUtils = new NotificationUtils(mContext);
            notificationUtils.sendNotification(params[0], params[1]);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
        }
    }
}
