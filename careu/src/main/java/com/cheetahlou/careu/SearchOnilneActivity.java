package com.cheetahlou.careu;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cheetahlou.careu.adapter.SearchOnlineAdapter;
import com.cheetahlou.careu.model.SearchQAModel;
import com.cheetahlou.careu.util.NetworkUtil;
import com.cheetahlou.careu.widget.Divider;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 一问医答Activity
 */
public class SearchOnilneActivity extends AppCompatActivity {

    private Button btn_search_online;
    private EditText et_search_keyword;
    private RecyclerView recyclerViewQA;
    private SearchOnlineAdapter searchQAAdapter;
    private List<SearchQAModel> qaList;
    private Context context;
    ProgressDialog dialog;
    private Toolbar toolbar;

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

        recyclerViewQA.setLayoutManager(new LinearLayoutManager(
                this, LinearLayoutManager.VERTICAL, false));
/*        //设置adapter
        recyclerViewQA.setAdapter(searchQAAdapter);*/


        Divider divider = new Divider(new ColorDrawable(0xffff0000), OrientationHelper.VERTICAL);
        //单位:px
        divider.setMargin(5, 50, 5, 50);
        divider.setHeight(20);
        recyclerViewQA.addItemDecoration(divider);
        //设置Item增加、移除动画
        recyclerViewQA.setItemAnimator(new DefaultItemAnimator());
/*        //添加分割线
        recyclerViewQA.addItemDecoration(new DividerItemDecoration(
                context, LinearLayoutManager.HORIZONTAL));*/

        toolbar = (Toolbar) findViewById(R.id.tb_1);
        toolbar.inflateMenu(R.menu.toolbar_menu);
        //设置toolbar的Title
        toolbar.setTitle(R.string.tb_title_search_online);

        //导航按钮(此处为Back)的点击事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //action menu操作菜单按钮的点击事件
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.tb_chat:
                        finish();
                        break;
                /*    case R.id.setting:
                        Toast.makeText(MeActivity.this,"设置",Toast.LENGTH_SHORT).show();
                        break;*/
                }
                return false;
            }
        });

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
            //获取问答列表,最后的select相当于以class名为hot-qa-item的div标签来作每个问答的区分
            Elements hot_qa_list=doc
                    .getElementsByClass("main-wrap")
                    .first()
                    .select("div.hot-qa.main-block")
                    .select("div.hot-qa-item");
//                Elements select=doc.select("div.ui-grid.ui-main.clearfix") ;
            if(!hot_qa_list.isEmpty()){
                Log.i("mycareu",hot_qa_list.toString());
                //遍历问答列表取出
                for(Element qa:hot_qa_list){
                    //获取详细url,attr()方法表示取该属性的属性值
                    String url=preUrl+qa.select("a[href]").first().attr("href");
                    Log.i("mycareu",url);
                    //获取问题标题
                    String userQTitle=qa.select("a[href]").first().text();
                    Log.i("mycareu",userQTitle);
                    //获取问答时间
                    String qDate=qa.select("span.date").first().text();
                    Log.i("mycareu",qDate);
                    //获取问题详情
                    String qDetail=qa.select("p.hot-qa-user").first().text();
                    Log.i("mycareu",qDetail);
                    //获取医生姓名
                    String doctorName=qa.getElementsByTag("em").get(0).text();
                    Log.i("mycareu",doctorName);
                    //获取医生所在医院名
                    String doctorHospital=qa.getElementsByTag("h6").text().replace(doctorName,"").trim();
                    Log.i("mycareu",doctorHospital);
                    //获取回答具体内容
                    String doctorA=qa.select("p").last().text().trim();
                    Log.i("mycareu",doctorA);

                    SearchQAModel qaModel=new SearchQAModel();
                    qaModel.setUrl(url);
                    qaModel.setUserQTitle(userQTitle);
                    qaModel.setqDate(qDate);
                    qaModel.setUserQDetail(qDetail);
                    qaModel.setDoctorName(doctorName);
                    qaModel.setDoctorHospital(doctorHospital);
                    qaModel.setDoctorA(doctorA);
                    qaModelList.add(qaModel);
                    //设置adapter等
                    showData(qaModelList);
                }
            }else{
                runOnUiThread(new Runnable(){//更新UI
                    @Override
                    public void run() {
                        toast("暂无搜索结果");
                    }
                });

            }
            dialog.dismiss();

        }
    };

    //设置adapter等
    private void showData(List<SearchQAModel> qaModelList){
        qaList=qaModelList;
        runOnUiThread(new Runnable(){//更新UI
            @Override
            public void run() {
                    searchQAAdapter=new SearchOnlineAdapter(context,qaList);
                    recyclerViewQA.setAdapter(searchQAAdapter);
            }
        });
    }
    private void toast(String zz){
        Toast.makeText(context, zz, Toast.LENGTH_SHORT).show();
    }

    private void init(){
        btn_search_online=(Button)findViewById(R.id.btn_search_online);
        et_search_keyword=(EditText)findViewById(R.id.et_search_keyword);
        recyclerViewQA=(RecyclerView)findViewById(R.id.rv_qa);
        context=this;
        btn_search_online.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyword=et_search_keyword.getText().toString();
                if(keyword.trim().equals("")){
                    toast("请输入要查找的关键词");
                }else if(!NetworkUtil.isNetworkAvailable((Activity)context)){
                    toast("请检查网络连接");
                } else{
                    parseSearchResult();
                }
            }
        });
    }
}
