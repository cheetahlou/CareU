package com.cheetahlou.careu;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cheetahlou.careu.model.SearchQAModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchOnilneActivity extends AppCompatActivity {

    private Button btn_search_online;
    private EditText et_search_keyword;
    private Context context;
    ProgressDialog dialog;

    //解析后的网页文档
    Document doc;
    //QA的拼接地址前缀
    private  static final String preUrl="http://www.chunyuyisheng.com";
    private String keyword="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_onilne);
        init();
    }

    /**
     * 解析搜索结果网页
     */
    private void parseSearchResult(){


        dialog = new ProgressDialog(this);
        dialog.setMessage("正在搜索...");
        dialog.setCancelable(false);
        dialog.show();
       new Thread(runnable).start();
    }

    Runnable runnable=new Runnable() {
        /**
         *
         */
        @Override
        public void run() {
            String searchUrl="http://www.chunyuyisheng.com/pc/qalist/?disease_name="+keyword+"#hotqa";
            try {
                doc= Jsoup.connect(searchUrl).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            List<SearchQAModel> qaModelList=new ArrayList<>();
            //获取问答列表,最后的select相当于以带href属性的a标签来作每个问答的区分
            Elements hot_qa_list=doc.getElementsByClass("main-wrap").first().select("div.hot-qa.main-block").select("a[href]");
//                Elements select=doc.select("div.ui-grid.ui-main.clearfix") ;
            if(!hot_qa_list.isEmpty()){
                Log.i("mycareu",hot_qa_list.toString());
                //遍历问答列表取出
                for(Element qa:hot_qa_list){
                    //获取详细url,attr()方法表示取该属性的属性值
                    String url=preUrl+qa.attr("href");
                    Log.i("mycareu",url);
                    //获取问题详情
                    String userQ=qa.select("p.hot-qa-user").get(0).text();
                    Log.i("mycareu",userQ);
                    //获取医生姓名
                    String doctorName=qa.getElementsByTag("em").get(0).text();
                    Log.i("mycareu",doctorName);
                    //获取医生所在医院名
                    String doctorHospital=qa.getElementsByTag("h6").text().replace(doctorName,"").trim();
                    Log.i("mycareu",doctorHospital);
                    //获取回答具体内容
                    String doctorA=qa.select("p").last().text();
                    Log.i("mycareu",doctorA);

                    SearchQAModel qaModel=new SearchQAModel();
                    qaModel.setUrl(url);
                    qaModel.setUserQ(userQ);
                    qaModel.setDoctorName(doctorName);
                    qaModel.setDoctorHospital(doctorHospital);
                    qaModel.setDoctorA(doctorA);
                    qaModelList.add(qaModel);
                }
            }

            dialog.dismiss();

        }
    };

    private void toast(String zz){
        Toast.makeText(context, zz, Toast.LENGTH_SHORT).show();
    }

    private void init(){
        btn_search_online=(Button)findViewById(R.id.btn_search_online);
        et_search_keyword=(EditText)findViewById(R.id.et_search_keyword);
        context=this;
        btn_search_online.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyword=et_search_keyword.getText().toString();
                if(keyword.trim().equals("")){
                    toast("请输入要查找的关键词");
                }else {
                    parseSearchResult();
                }
            }
        });
    }
}
