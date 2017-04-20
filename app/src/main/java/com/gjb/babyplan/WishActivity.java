package com.gjb.babyplan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class WishActivity extends AppCompatActivity {
    //查看、添加心愿单
    private ListView lvwish;
    private Button btnaddwish,btnback;
    private EditText etaddwish;
    static String wish="";
    List<String> data1=new ArrayList<>();
    ArrayAdapter<String> adapter;
    static int n=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish);
        init();

        adapter = new ArrayAdapter<String>(WishActivity.this, android.R.layout.simple_expandable_list_item_1,data1);
        AVQuery<AVObject> wishquery=new AVQuery<>("Wish");
        wishquery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if(list!=null){
                    if(!list.isEmpty()){
                        n=list.get(list.size()-1).getInt("wid")+1;
                        List<AVObject> wishlist=list;
                        int nu=1;
                        for(AVObject av1:wishlist){
                            wish=av1.get("wish").toString();
                            data1.add("心愿"+nu+": "+wish);
                            nu++;
                            lvwish.setAdapter(adapter);
                        }

                    }
                }
                else{
                    AVObject obj1=new AVObject("Wish");


                }
            }
        });
    }


    private Button.OnClickListener addwishls= new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            wish=etaddwish.getText().toString();
            if(!wish.equals("")){
                data1.add("心愿"+n+": "+wish);   //集合按索引添加数据
                final AVQuery<AVObject> wishquery=new AVQuery<>("Wish");
                wishquery.findInBackground(new FindCallback<AVObject>() {
                    @Override
                    public void done(List<AVObject> list, AVException e) {
                        if(list!=null){
                            final AVObject obj1=new AVObject("Wish");
                            obj1.put("wid",n);
                            obj1.put("wish",wish);
                            obj1.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(AVException e) {
                                    obj1.saveInBackground();
                                }
                            });
                            n++;
/*
                            List<AVObject> wishlist=list;
                            for(AVObject av1:wishlist){
                                data1.add(av1.get("wish").toString());
                            }
*/

                        }else{
                            final AVObject obj1=new AVObject("Wish");
                            obj1.put("wid",n);
                            obj1.put("wish",wish);
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

                etaddwish.setText("");      //每次输入后设置EditText控件内容为空，即清空输入框

                //添加并且显示
                lvwish.setAdapter(adapter);
            }else{
                Toast.makeText(WishActivity.this, "请先输入点啥！", Toast.LENGTH_SHORT).show();
            }
        }
    };
    private Button.OnClickListener back=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };
    private void init() {
        lvwish=(ListView)findViewById(R.id.lvwish);
        etaddwish=(EditText) findViewById(R.id.etaddwish);
        btnaddwish=(Button) findViewById(R.id.btnaddwish);
        btnback=(Button)findViewById(R.id.btnback);
        btnback.setOnClickListener(back);
        btnaddwish.setOnClickListener(addwishls);
    }
}
