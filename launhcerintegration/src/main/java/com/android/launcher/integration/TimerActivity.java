package com.android.launcher.integration;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.Timer;
import java.util.concurrent.ScheduledExecutorService;

public class TimerActivity extends AppCompatActivity implements TextToSpeech.OnInitListener,
        GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener, View.OnTouchListener {

    private static final String TAG = "TimerActivity";

    private ScheduledExecutorService mExecutorService;

    Timer mTimer;

    private TextToSpeech mTextToSpeech;
    private Button mStart;
    private TextView mTextView;

    private GestureDetector mGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mStart = findViewById(R.id.start_speech);
        mTextView = findViewById(R.id.textView_description);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view,
                "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());

        mTextToSpeech = new TextToSpeech(getBaseContext(), this);

        mStart.setOnClickListener(view -> mTextToSpeech.speak(mTextView.getText().toString(),
                TextToSpeech.QUEUE_ADD, null, "UniqueID"));

        mGestureDetector = new GestureDetector(getApplicationContext(), this);
        mGestureDetector.setOnDoubleTapListener(this);
        mGestureDetector.setIsLongpressEnabled(true);
        mTextView.setOnTouchListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onInit(int status) {
        // 如果装载TTS成功
        if (status == TextToSpeech.SUCCESS) {
            // 设置使用英式英语朗读
            int result = mTextToSpeech.setLanguage(Locale.US);
            mTextToSpeech.setOnUtteranceProgressListener(new TtsUtteranceProgressListener());
            // 如果不支持所设置的语言
            if (result != TextToSpeech.LANG_COUNTRY_AVAILABLE
                    && result != TextToSpeech.LANG_AVAILABLE) {
                Toast.makeText(getBaseContext()
                        , "TTS暂时不支持这种语言的朗读。", Toast.LENGTH_LONG)
                        .show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mTextToSpeech) {
            mTextToSpeech.shutdown();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public boolean onDown(MotionEvent e) {
        Log.d(TAG, "onDown: ");
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        Log.d(TAG, "onShowPress: ");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Log.d(TAG, "onSingleTapUp: ");
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        Log.d(TAG, "onScroll: ");
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        Log.d(TAG, "onLongPress: ");
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.d(TAG, "onFling: ");
        return false;
    }


    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        Log.d(TAG, "onSingleTapConfirmed: ");
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Log.d(TAG, "onDoubleTap: ");
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        Log.d(TAG, "onDoubleTapEvent: ");
        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        return true;
    }

    private class TtsUtteranceProgressListener extends UtteranceProgressListener {

        @Override
        public void onStart(String utteranceId) {
            Log.d(TAG, "onStart: utteranceId = " + utteranceId);
        }

        @Override
        public void onDone(String utteranceId) {
            Log.d(TAG, "onDone: utteranceId = " + utteranceId);
            mTextToSpeech.speak(mTextView.getText().toString(),
                    TextToSpeech.QUEUE_ADD, null, "UniqueID");
        }

        @Override
        public void onError(String utteranceId) {
            Log.d(TAG, "onError: utteranceId = " + utteranceId);
        }
    }
}
