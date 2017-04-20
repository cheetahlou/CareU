package com.gjb.babyplan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;

import java.util.List;

public class ParentActivity extends AppCompatActivity {

    private Button btnreward;
    private EditText etreward;
    static int summn=0;
    static String nowmn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent);
        etreward=(EditText) findViewById(R.id.etreward);
        btnreward=(Button) findViewById(R.id.btnreward);
        btnreward.setOnClickListener(rewardls);

        AVQuery<AVObject> avQuery = new AVQuery<>("Pktmn");
        avQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                AVObject avObject1 = list.get(list.size() - 1);
                nowmn = avObject1.getString("pkmoney");

            }
        });
    }

    private Button.OnClickListener rewardls=new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            //奖励零花钱，有Strign到int的转换然后计算零花钱的和再转会String类型传回到MainActivity中
            String str=etreward.getText().toString();
            boolean isdigit=str.matches("[0-9]+");//检查输入的奖励零花钱是否是数字
            //验证输入的零花钱是否合法
            if (str.equals("")) {
                Toast.makeText(ParentActivity.this, "请输入奖励金额！", Toast.LENGTH_SHORT).show();
            }else if(!isdigit){
                Toast.makeText(ParentActivity.this, "请输入数字！", Toast.LENGTH_SHORT).show();
            }
            else {
                final AVObject avObject1=new AVObject("Pktmn");
                int nowmnint = Integer.parseInt(nowmn);
                int addmn = Integer.parseInt(etreward.getText().toString());
                summn = nowmnint + addmn;//添加到孩子所得的零花钱中
               final String aftermn = summn + "";
                avObject1.put("pkmoney", aftermn);
                avObject1.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(AVException e) {
                        if (e == null) {
                            // 存储成功
                            int haha=1;
                            MainActivity.statusnum=haha;//将MainActivty中的statussum字段设置为1，作为启动MainActivity之后onResume（）将加载更改后的零花钱总和
                            avObject1.saveInBackground();//保存奖励零花钱后的结果
                            Intent intent = new Intent();
                            intent.setClass(ParentActivity.this, MainActivity.class);
                            Bundle bundle=new Bundle();
                            bundle.putString("pktmn",aftermn);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        } else {
                            // 失败的话，请检查网络环境以及 SDK 配置是否正确
                        }
                    }
                });

            }
        }
    };
}
