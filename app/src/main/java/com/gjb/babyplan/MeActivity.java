package com.gjb.babyplan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MeActivity extends AppCompatActivity {
    private Button btnback,btnlogout;
    private TextView tvmename;
    private LinearLayout llnick;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);
        init();
    }

    private Button.OnClickListener back=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };
    private Button.OnClickListener logout=new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            getSharedPreferences("Login",MODE_PRIVATE).edit().putString("isLogin","0").commit();
            startActivity(new Intent().setClass(context,LoginActivity.class));
            finish();
        }
    };
    private LinearLayout.OnClickListener addnickname=new LinearLayout.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent().setClass(context,AddNickNameActivity.class));
            finish();
        }
    };

    private void init() {
        context=this;
        btnback=(Button) findViewById(R.id.btnback);
        btnlogout=(Button) findViewById(R.id.btnlogout);
        tvmename=(TextView) findViewById(R.id.tvmename);
        tvmename.setText(getSharedPreferences("Login",MODE_PRIVATE).getString("userName",""));
        llnick=(LinearLayout) findViewById(R.id.llnick);
        llnick.setOnClickListener(addnickname);//去设置昵称
        btnback.setOnClickListener(back);
        btnlogout.setOnClickListener(logout);
    }
}
