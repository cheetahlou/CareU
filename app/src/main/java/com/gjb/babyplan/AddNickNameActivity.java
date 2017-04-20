package com.gjb.babyplan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddNickNameActivity extends AppCompatActivity {
    private Button btnback,btnsavename;
    private EditText etaddname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_nick_name);
        init();
    }

    private Button.OnClickListener back=new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent().setClass(AddNickNameActivity.this,MeActivity.class));
            finish();
        }
    };

    private Button.OnClickListener savename=new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            getSharedPreferences("Login",MODE_PRIVATE).edit().putString("userName",etaddname.getText().toString()).commit();
                    finish();
            startActivity(new Intent().setClass(AddNickNameActivity.this,MeActivity.class));
        }
    };
    private void init() {
        btnback=(Button) findViewById(R.id.btnback);
        btnsavename=(Button) findViewById(R.id.btnsavename);
        btnback.setOnClickListener(back);
        btnsavename.setOnClickListener(savename);
        etaddname=(EditText)findViewById(R.id.etaddname);
    }
}
