package com.gjb.babyplan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVMobilePhoneVerifyCallback;
import com.avos.avoscloud.AVOSCloud;

public class LoginActivity extends AppCompatActivity {

    private EditText etph,etcaptcha;
    private Button btn1,btn2;
    static String ph="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etph=(EditText) findViewById(R.id.etph);
        etcaptcha=(EditText) findViewById(R.id.etcaptcha);
        btn1= (Button) findViewById(R.id.btn1);
        btn2=(Button) findViewById(R.id.btn2);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ph=etph.getText().toString();
                if(ph.equals("")){
                    etph.setError("请先输入手机号",null);
                    etph.requestFocus();
                }else if(ph.length()!=11) {
                etph.setError("手机号必须为11位哦",null);
                }else{
                    sendCode(ph);
                }
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击确定登录按钮时要检查验证码是否正确并且提交一些用户的初始数据到SP中，比如登录的手机号、登录状态信息、未设置昵称等
                String captcha=etcaptcha.getText().toString();
                if(captcha.equals("")){
                    etcaptcha.setError("请先输入验证码",null);
                }else if(captcha.length()!=6){
                    etcaptcha.setError("验证码必须为6位数字",null);
                }
                else{
                    try {
                        //提交验证
                        AVOSCloud.verifySMSCodeInBackground(captcha, ph, new AVMobilePhoneVerifyCallback() {
                            @Override
                            public void done(AVException e) {
                                //LaunchActivity.isLogin=true;
                                Toast.makeText(LoginActivity.this, "验证成功", Toast.LENGTH_SHORT).show();
                                SharedPreferences preferences=getSharedPreferences("Login",MODE_PRIVATE);
                                preferences.edit().putString ("isLogin","1").commit();//将SP中的是否已经登录的信息更改，表示已经登录
                                preferences.edit().putString("userPh",etph.getText().toString());
                                preferences.edit().putString("userName","还未设置昵称");//设置初始的昵称值“还未设置昵称”
                                String strlogin=getSharedPreferences("Login",MODE_PRIVATE).getString("isLogin","");
                                toast("SP中为:"+strlogin);
                                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                finish();
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(LoginActivity.this, "验证失败了", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        });
    }

    public void sendCode(String phone) {
        new AsyncTask<Void, Void, Void>() {
            boolean res;

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    AVOSCloud.requestSMSCode(ph, "BabyPlan", "用户注册", 10);
                    res = true;
                } catch (AVException e) {
                    e.printStackTrace();
                    res = false;
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if (res) {
                    toast("验证码已发送");
                } else {
                    toast("验证码发送失败");
                }
            }
        }.execute();
    }
    private void toast(String zz) {
        Toast.makeText(LoginActivity.this, zz, Toast.LENGTH_SHORT).show();
    }

}
