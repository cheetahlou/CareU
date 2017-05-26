package com.cheetahlou.careu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

/**
 * Created by Cheetahlou on 2017/5/21.
 */

public class ToolbarActivity extends AppCompatActivity {
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.toolbar);
        toolbar = (Toolbar) findViewById(R.id.tb_1);
        toolbar.inflateMenu(R.menu.toolbar_menu);
        //action menu操作菜单按钮的点击事件
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.tb_chat:
                        finish();
                        break;
                /*    case R.id.setting:
                        Toast.makeText(ToolbarActivity.this,"设置",Toast.LENGTH_SHORT).show();
                        break;*/
                }
                return false;
            }
        });
        //导航按钮的点击事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                finish();
            }
        });
    }
}
