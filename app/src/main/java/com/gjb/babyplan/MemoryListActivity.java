package com.gjb.babyplan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MemoryListActivity extends AppCompatActivity {
    private Button btnaddmem,btnback;
    private Context context;
    private ListView lvmem;
    private List<MemoryBean> memlist=new ArrayList<MemoryBean>();
    MemoryAdapter adapter=new MemoryAdapter();
    private MemoryDAO dao;

    private Handler mHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    adapter=new MemoryAdapter(context,memlist);
                    lvmem.setAdapter(adapter);
                    break;

                default:
                    break;
            }
        }

    };
    //测试数据
   // MemoryBean memoryBean=new MemoryBean();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_list);
        init();
    }

    private String getTimeNow(){
        Time t=new Time();
        t.setToNow();//取得系统时间
       // String timenow=t.year+"年"+t.month+"月"+t.monthDay+"日"+"  "+t.hour+":"+t.minute+":"+t.second;
        String timenow=t.year+"年"+t.month+"月"+t.monthDay+"日"+"  "+t.hour+":"+t.minute;
        return timenow;
    }

    private Button.OnClickListener addmemls=new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent().setClass(context,UploadActivity.class));
            finish();
/*            startActivity(new Intent().setClass(context,MemoryListActivity.class));
            finish();*/
        }
    };

    private Button.OnClickListener back=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };
    public void toast(String zz){
        Toast.makeText(context, zz, Toast.LENGTH_SHORT).show();
    }

    private void init() {
        lvmem=(ListView) findViewById(R.id.lvmemory);
        btnaddmem=(Button) findViewById(R.id.btnaddmem);
        btnback=(Button) findViewById(R.id.btnback);
        btnaddmem.setOnClickListener(addmemls);
        btnback.setOnClickListener(back);
        context=this;
        dao=new MemoryDAO(context);
//        dao.insertMem(new MemoryBean("cheetahtime","cheetahcontent","http://ac-t3s5raai.clouddn.com/mpMLci6jCiL95O9FAXzrv6oXZpI6biRYKCz8CwBx"));

//        memoryBean.setMemtime(getTimeNow());
//        memoryBean.setMemcontent("This is a Korean name; the family name is Lee.IU auditioned for various talent agencies with ambitions of becoming a singer.");
//        memoryBean.setImageUrl("http://ac-t3s5raai.clouddn.com/iWUVdv87wVbxjdjxEvqeiUbHgJsiB6NMxZa5R8c0");
        // memlist.add(memoryBean);
        memlist=dao.listAllMem();
        mHandler.obtainMessage(0).sendToTarget();
/*        MemoryAdapter adapter=new MemoryAdapter(this,memlist);
        lvmem.setAdapter(adapter);*/
    }
}
