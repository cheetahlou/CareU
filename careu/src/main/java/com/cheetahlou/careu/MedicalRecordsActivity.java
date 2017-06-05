package com.cheetahlou.careu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

/**
 * 我的病历本
 */
public class MedicalRecordsActivity extends AppCompatActivity {
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_records);



        //toolbar相关设置
        toolbar = (Toolbar) findViewById(R.id.tb_1);
        //设置toolbar的Title
        toolbar.setTitle("我的病历本");

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
}
