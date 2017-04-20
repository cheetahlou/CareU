package com.gjb.babyplan;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class LaunchActivity extends AppCompatActivity {
    //启动时加载页，可以判断网络状态和是否第一次登录
    private ImageView ivstart1,ivstart2;
    static int image_alpha1=255;
    static int image_alpha2=0;
    Handler launch;
    boolean isrun1=true;
    boolean isrun2=false;
    private int runtime=0;
    static boolean isLogin=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        init();

        ivstart2.setAlpha(image_alpha2);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(isrun1){
                    try {
                        Thread.sleep(70);//线程睡眠70毫秒，通过连续短时间的睡眠改变图片透明度来达到动画的渐变效果
                        updateAlpha();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                while(isrun2){
                    try {
                        Thread.sleep(100);
                        updateAlpha();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        //接收消息之后更新imageview视图

        launch=new Handler(){
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                ivstart1.setAlpha(image_alpha1);
                ivstart2.setAlpha(image_alpha2);
                ivstart1.invalidate();//刷新视图
                ivstart2.invalidate();
            }
        };
        launch.postDelayed(new Runlaunch(),2500);//设置5秒钟延迟执行Runlaunch线程，
    }
//两张图片Alpha值渐变函数
    private void updateAlpha() {
        //第一张图片的Alpha变化，渐渐变透明
        while(runtime<100){

            if (image_alpha1 > 120) {

                image_alpha1-=14;
            }else{
                image_alpha1=0;
                isrun1=false;
                image_alpha2=100;
            }
            runtime+=10;
            break;
        }
        //第二张图片的Alpha变化，渐渐从透明变成完全不透明
        while(runtime>=100){
            isrun2=true;
            if(image_alpha2<200){
                image_alpha2+=10;
            }else{
                image_alpha2=255;
                isrun2=false;
            }
            runtime+=10;
            break;
        }
        launch.sendMessage(launch.obtainMessage()); // 发送需要更新imageview视图的消息-->这里是发给主线程
    }

    class Runlaunch implements Runnable{

        @Override
        public void run() {
            //如果isLogin为真则表示已经登录过了不需要再登录直接进入主界面，为isLogin为假则表示第一次登录进入登录界面
            if(isLogin){
                startActivity(new Intent(LaunchActivity.this,MainActivity.class));
            }else{
                startActivity(new Intent(LaunchActivity.this,MainActivity.class));
            }
            LaunchActivity.this.finish();
        }
    }


    private void init() {
        ivstart1=(ImageView) findViewById(R.id.ivstart1);
        ivstart2=(ImageView) findViewById(R.id.ivstart2);
        //在onCreate()初始化中根据SP中的信息选择启动登录界面还是主界面
        if(!isLogin){
            String strlogin=getSharedPreferences("Login",MODE_PRIVATE).getString("isLogin","unknown");
            if(strlogin.equals("1")){
            isLogin=true;
            }
        }
    }
}
