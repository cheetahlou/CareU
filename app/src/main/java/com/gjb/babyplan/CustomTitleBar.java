package com.gjb.babyplan;

import android.app.Activity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author aaron
 *
 */
public class CustomTitleBar {

    private static Activity mActivity;

    /**
     * @see [自定义标题栏]
     * @param activity
     * @param title
     */
    public static void getTitleBar(Activity activity,String title) {
        mActivity = activity;
        activity.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
       // activity.setContentView(R.layout.title);
        activity.getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
                R.layout.title);
        TextView textView = (TextView) activity.findViewById(R.id.head_center_text);
        textView.setText(title);
        Button btnback = (Button) activity.findViewById(R.id.head_TitleBackBtn);
        btnback.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                KeyEvent newEvent = new KeyEvent(KeyEvent.ACTION_DOWN,
                        KeyEvent.KEYCODE_BACK);
                mActivity.onKeyDown(KeyEvent.KEYCODE_BACK, newEvent);
            }
        });
    }
}

