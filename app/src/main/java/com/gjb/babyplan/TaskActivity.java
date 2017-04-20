package com.gjb.babyplan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class TaskActivity extends AppCompatActivity {

    private ListView lvtask;
    private EditText etaddtask;
    private Button btnaddtask,btnback;
    static String task="";
    List<String> data1 = new ArrayList();  //定义ListView中要显示的List集合
    ArrayAdapter<String> adapter;
    static int n=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        init();

        adapter = new ArrayAdapter<String>(TaskActivity.this, android.R.layout.simple_expandable_list_item_1,data1);
        AVQuery<AVObject> taskquery=new AVQuery<>("Task");
        taskquery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if(list!=null){
                    if(!list.isEmpty()){
                        n=list.get(list.size()-1).getInt("tid")+1;
                        List<AVObject> tasklist=list;
                        int nu=1;
                        for(AVObject av1:tasklist){
                            task=av1.get("task").toString();
                            data1.add("小任务"+nu+": "+task);
                            nu++;
                            lvtask.setAdapter(adapter);
                        }

                    }else{
                        AVObject obj1=new AVObject("Task");


                    }
                }
            }
        });

    }

    Button.OnClickListener addtaskls=new Button.OnClickListener() {

        @Override
        public void onClick(View v) {

            task=etaddtask.getText().toString();
            if(!task.equals("")){
                data1.add("小任务"+n+": "+task);   //集合按索引添加数据
                final AVQuery<AVObject> taskquery=new AVQuery<>("Task");
                taskquery.findInBackground(new FindCallback<AVObject>() {
                    @Override
                    public void done(List<AVObject> list, AVException e) {
                        if(list!=null){
                            final AVObject obj1=new AVObject("Task");
                            obj1.put("tid",n);
                            obj1.put("task",task);
                            obj1.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(AVException e) {
                                    obj1.saveInBackground();
                                }
                            });
                            n++;
/*
                            List<AVObject> tasklist=list;
                            for(AVObject av1:tasklist){
                                data1.add(av1.get("task").toString());
                            }
*/

                        }else{
                            final AVObject obj1=new AVObject("Task");
                            obj1.put("tid",n);
                            obj1.put("task",task);
                            obj1.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(AVException e) {
                                    obj1.saveInBackground();
                                    n++;
                                }
                            });
                        }
                    }
                });

                etaddtask.setText("");      //每次输入后设置EditText控件内容为空，即清空输入框

                //添加并且显示
                lvtask.setAdapter(adapter);
            }else{
                Toast.makeText(TaskActivity.this, "请先输入点啥！", Toast.LENGTH_SHORT).show();
            }
        }
    };
    AdapterView.OnItemLongClickListener lvtaskmenu=new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            showPopupWindow(TaskActivity.this,view);
            return false;
        }

    };
    private void showPopupWindow(Context context, View parent) {
        //LayoutInflater的作用是用来动态加载Layout文件的
        LayoutInflater inflater = LayoutInflater.from(context);
        final View popupView = inflater.inflate( R.layout.popup_window, null);//动态加载Layout文件
        final PopupWindow pWindow = new PopupWindow(popupView,600,600,true);//需要填写宽高，否则显示不了

        final Button btnok=(Button)popupView.findViewById(R.id.BtnOK);//加载之后可以找到其中的控件了
        //final EditText edtUsername=(EditText)popupView.findViewById(R.id.data_edit);
        final TextView tvtopkymn=(TextView)popupView.findViewById(R.id.tvtopktmn);
        tvtopkymn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TaskActivity.this,ParentActivity.class);
                startActivity(intent);
            }
        });
        btnok.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                //设置文本框内容
                startActivity(new Intent(TaskActivity.this,ParentActivity.class));
            }
        });
        //Cancel按钮及其处理事件
        Button btnCancel=(Button)popupView.findViewById(R.id.BtnCancel);
        btnCancel.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                pWindow.dismiss();//关闭
            }
        });
        //显示popupWindow对话框
        pWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);

    }

    private Button.OnClickListener back=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };
    private void init() {
        lvtask=(ListView) findViewById(R.id.lvtask);
        etaddtask=(EditText) findViewById(R.id.etaddtask);
        btnaddtask=(Button)findViewById(R.id.btnaddtask);
        btnback=(Button) findViewById(R.id.btnback);
        btnback.setOnClickListener(back);
        btnaddtask.setOnClickListener(addtaskls);
        lvtask.setOnItemLongClickListener(lvtaskmenu);
    }
}
