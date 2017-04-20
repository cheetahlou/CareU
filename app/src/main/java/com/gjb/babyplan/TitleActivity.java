package com.gjb.babyplan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class TitleActivity extends AppCompatActivity {

    private Button btnback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.title);
        btnback=(Button) findViewById(R.id.btnback);
        btnback.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                finish();
 /*               KeyEvent newEvent = new KeyEvent(KeyEvent.ACTION_DOWN,
                        KeyEvent.KEYCODE_BACK);
                onKeyDown(KeyEvent.KEYCODE_BACK, newEvent);*/
            }
        });
    }
}
