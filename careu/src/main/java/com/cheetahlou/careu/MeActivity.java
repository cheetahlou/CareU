package com.cheetahlou.careu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 个人中心
 */
public class MeActivity extends AppCompatActivity {
    private Button btnlogout;
    private TextView tvmename,tvmeph;
    private LinearLayout llnick;
    private Context context;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);
        init();
        toolbar = (Toolbar) findViewById(R.id.tb_1);
        //设置toolbar的Title
        toolbar.setTitle(R.string.tb_title_me);

        toolbar.inflateMenu(R.menu.toolbar_menu);

        //去掉setSupportActionBar，才会让Toolbar本身的inflateMenu生效
        //        setSupportActionBar(toolbar);

        //导航按钮(此处为Back)的点击事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //action menu操作菜单按钮的点击事件
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.tb_chat:
                        finish();
                        break;
                /*    case R.id.setting:
                        Toast.makeText(MeActivity.this,"设置",Toast.LENGTH_SHORT).show();
                        break;*/
                }
                return false;
            }
        });
    }

    //退出登录
    private Button.OnClickListener logout=new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            getSharedPreferences("Login",MODE_PRIVATE).edit().putString("isLogin","0").commit();
            startActivity(new Intent().setClass(context,LoginActivity.class));
            ProfileActivity.profileActivity.finish();
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
        btnlogout=(Button) findViewById(R.id.btnlogout);
        tvmename=(TextView) findViewById(R.id.tvmename);
        tvmeph=(TextView) findViewById(R.id.tvmeph);
        tvmename.setText(getSharedPreferences("profile",MODE_PRIVATE).getString("userNickName",""));
        String pri_ph=getSharedPreferences("profile",MODE_PRIVATE).getString("userPhone","");
        String encrypt_ph=pri_ph.substring(0,3)+"****"+pri_ph.substring(7);
        tvmeph.setText(encrypt_ph);
        llnick=(LinearLayout) findViewById(R.id.llnick);
        llnick.setOnClickListener(addnickname);//去设置昵称
        btnlogout.setOnClickListener(logout);
    }
}
