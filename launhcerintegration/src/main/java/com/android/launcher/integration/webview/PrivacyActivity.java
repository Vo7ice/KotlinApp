package com.android.launcher.integration.webview;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.android.launcher.integration.R;

/**
 * @author huguojin
 */
public class PrivacyActivity extends AppCompatActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTextView = findViewById(R.id.textview);

        /*String str = "xxxx 查看详情";
        ClickableSpan span = new ClickableSpan() {

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                // 设置超链接的颜色
                ds.setColor(Color.parseColor("#ff33b5e5"));
                ds.setUnderlineText(true);
            }

            @Override
            public void onClick(View widget) {
                // 单击事件处理
            }
        };
        SpannableString ss = new SpannableString(str);
        ss.setSpan(span, str.length() - 4, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTextView.setText(ss);
        mTextView.setMovementMethod(LinkMovementMethod.getInstance());*/
        String str = "单击打开 <a href='http://www.baidu.com/'>百度首页</a>";
        StringBuilder sb = new StringBuilder();
        String string = getBaseContext().getResources().getString(R.string.app_name);
        sb.append(string).append(str);
        if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            mTextView.setText(Html.fromHtml(str, Html.FROM_HTML_MODE_LEGACY));
        } else {
            mTextView.setText(Html.fromHtml(str));
        }
        mTextView.setMovementMethod(LinkMovementMethod.getInstance());



        ContextCompat.getDrawable(getBaseContext(), R.color.colorAccent);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
