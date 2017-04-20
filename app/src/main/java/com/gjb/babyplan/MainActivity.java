package com.gjb.babyplan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button btntowish,btntotask,btntoparent,btntomem,btntome;
    private TextView pktmn;
    private EditText etreward;
    Intent intent=new Intent();
     static String objid;
     static String pkmn;
    static int statusnum=0;
    static int acstatus=0;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_main);
        init();

/*        if(acstatus==0){
            finish();
            Intent intent=new Intent();
            intent.setClass(MainActivity.this,RegisterActivity.class);
            startActivity(intent);
        }*/

/*        final AVObject todo = new AVObject("Todo");
        todo.put("pktmn", "222");
        todo.put("content", "每周工程师会议，周一下午2点");
        todo.put("location", "会议室");
        todo.saveInBackground(new SaveCallback() {
            @Override

            public void done(AVException e) {
                if (e == null) {
                    // 存储成功

                    todo.put("location", "二楼大会议室");
                    todo.saveInBackground();

                } else {
                    // 失败的话，请检查网络环境以及 SDK 配置是否正确
                }
            }
        });*/

        AVQuery<AVObject> avQuery1 = new AVQuery<>("Pktmn");
        avQuery1.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if(list!=null&&!list.isEmpty()){
                        //  objid=av2.getObjectId();
                        AVObject av2=list.get(list.size()-1);
                        pkmn=av2.get("pkmoney").toString();
                        pktmn.setText(pkmn);
                }
                else{
                    pktmn.setText("0");
                    final AVObject avpktmn=new AVObject("Pktmn");
                    avpktmn.put("pkmoney","0");
                    avpktmn.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            avpktmn.saveInBackground();
                        }
                    });
                }
            }
        });

    }

    protected void onResume(){
        super.onResume();
        if(statusnum==1) {
            Intent intent = this.getIntent();
            Bundle bundle = intent.getExtras();
            pkmn = bundle.getString("pktmn");
            pktmn.setText(pkmn);
            statusnum=0;
        }
    }
   private  Button.OnClickListener towishls= new Button.OnClickListener() {
       @Override
       public void onClick(View v) {
           intent.setClass(MainActivity.this,WishActivity.class);
           startActivity(intent);
       }
   };
    private  Button.OnClickListener totaskls= new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            intent.setClass(MainActivity.this,TaskActivity.class);
            startActivity(intent);
        }
    };

    private Button.OnClickListener tomemls= new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent().setClass(context,MemoryListActivity.class));
        }
    };
    private Button.OnClickListener tomels= new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent().setClass(context,MeActivity.class));
        }
    };
    private void init() {
        btntowish=(Button) findViewById(R.id.btntowish);
        btntotask=(Button)findViewById(R.id.totask);
        btntomem=(Button) findViewById(R.id.btntomem);
        btntome=(Button)findViewById(R.id.btntome);
        pktmn=(TextView) findViewById(R.id.pktmoney);
        btntomem.setOnClickListener(tomemls);
        btntome.setOnClickListener(tomels);
        LayoutInflater layout=this.getLayoutInflater();
        View view=layout.inflate(R.layout.activity_parent, null);
        etreward=(EditText)view.findViewById(R.id.etreward);
        btntowish.setOnClickListener(towishls);
        btntotask.setOnClickListener(totaskls);
        context=this;
    }

}
