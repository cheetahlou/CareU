package com.cheetahlou.careu;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.cheetahlou.careu.adapter.HealthInfoAdapter;
import com.cheetahlou.careu.model.HealthInfoModel;
import com.cheetahlou.careu.widget.Divider;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 健康资讯Activity
 */
public class HealthInfoActivity extends AppCompatActivity {

    private RecyclerView recyclerViewHInfo;
    private HealthInfoAdapter hInfoAdapter;
    private List<HealthInfoModel> hInfoList;

    ProgressDialog dialog;
    private Context context;
    private Toolbar toolbar;
    SwipeRefreshLayout swipeRefreshLayout;

    //解析后的网页文档
    Document doc;
    //拼接地址前缀
    private  static final String preUrl="http://www.jianshu.com";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_info);
        context=this;
        recyclerViewHInfo=(RecyclerView)findViewById(R.id.rv_health_info);

        //设置recyclerview
        recyclerViewHInfo.setLayoutManager(new LinearLayoutManager(
                this, LinearLayoutManager.VERTICAL, false));
        Divider divider = new Divider(new ColorDrawable(0xffff0000), OrientationHelper.VERTICAL);
        //单位:px
        divider.setMargin(5, 50, 5, 50);
        divider.setHeight(20);
        recyclerViewHInfo.addItemDecoration(divider);
        //设置Item增加、移除动画
        recyclerViewHInfo.setItemAnimator(new DefaultItemAnimator());


        //设置toolbar
        toolbar = (Toolbar) findViewById(R.id.tb_1);
        toolbar.inflateMenu(R.menu.toolbar_menu);
        //设置toolbar的Title
        toolbar.setTitle(R.string.tb_title_health_info);

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

        //获取数据，加载解析结果
        parseSearchResult();

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override public void onRefresh() {
                //重新获取数据
                parseSearchResult();
                //获取完成
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }


    /**
     * 解析搜索结果网页
     */
    private void parseSearchResult(){


        dialog = new ProgressDialog(this);
        dialog.setMessage("正在加载...");
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
            String searchUrl="http://www.jianshu.com/c/YZRvCb?order_by=added_at";
            try {
                doc= Jsoup.connect(searchUrl).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            List<HealthInfoModel> hInfoModelList=new ArrayList<>();
            //获取列表,最后的select相当于以class含有data-note-id属性的li标签来作每个问答的区分
            Elements hinfo_list=doc.select("ul.note-list").select("li[data-note-id]");
            if(!hinfo_list.isEmpty()){
                Log.i("mycareu",hinfo_list.toString());
                //遍历问答列表取出
                for(Element hinfo:hinfo_list){
                    //获取详细url,attr()方法表示取该属性的属性值
                    String url=preUrl+hinfo.select("a.title").first().attr("href");
                    Log.i("mycareu",url);
                    //获取时间
                    String hTime=hinfo.select("span.time").first().attr("data-shared-at").substring(0,16).replace("T"," ");
                    Log.i("mycareu",hTime);
                    //获取问题标题
                    String hTitle=hinfo.select("a.title").first().text();
                    Log.i("mycareu",hTitle);
                    //获取问题详情
                    String hAbstract=hinfo.select("p.abstract").first().text();
                    Log.i("mycareu",hAbstract);
                    //获取图片url
                    String hImageUrl="http:"+hinfo.select("img").attr("src");
                    Log.i("mycareu",hImageUrl);

                    HealthInfoModel model= new HealthInfoModel();
                    model.setUrl(url);
                    model.setTime(hTime);
                    model.setTitle(hTitle);
                    model.sethAbstract(hAbstract);
                    model.setImageUrl(hImageUrl);
                    hInfoModelList.add(model);
                    //设置adapter等
                    showData(hInfoModelList);
                }
            }else{
                toast("暂无结果");
            }
            dialog.dismiss();

        }
    };

    //设置adapter等
    private void showData(List<HealthInfoModel> healthInfoModelList){
        hInfoList=healthInfoModelList;
        runOnUiThread(new Runnable(){//更新UI
            @Override
            public void run() {
                hInfoAdapter=new HealthInfoAdapter(context,hInfoList);
                //设置item点击事件
                hInfoAdapter.setmOnItemClickListener(new HealthInfoAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        //携带item中的url跳转到另一个Activity
                        Intent intent=new Intent();
                        intent.setClass(context,HealthInfoItemDetailActivity.class);
                        //获取url
                        String url=hInfoList.get(position).getUrl();
                        if(!url.isEmpty()){
                            intent.putExtra("url",url);
                            startActivity(intent);
                        }

                    }
                });
                recyclerViewHInfo.setAdapter(hInfoAdapter);
            }

        });
    }

    private void toast(String zz){
        Toast.makeText(context, zz, Toast.LENGTH_SHORT).show();
    }
}
