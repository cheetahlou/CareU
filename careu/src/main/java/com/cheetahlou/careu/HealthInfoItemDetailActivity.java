package com.cheetahlou.careu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * 健康资讯点击某项后的详情页，webview视图
 */
public class HealthInfoItemDetailActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private WebView mWebView;
    private String url;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_info_item_detail);

        toolbar = (Toolbar) findViewById(R.id.tb_1);
        //设置toolbar的Title
        toolbar.setTitle("资讯详情");

        toolbar.inflateMenu(R.menu.toolbar_menu);

        //去掉setSupportActionBar，才会让Toolbar本身的inflateMenu生效
        //        setSupportActionBar(toolbar);

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

                /*WebView相关设置开始*/
        //得到WebView
        mWebView = (WebView)findViewById(R.id.webview_health_info);

        //WebView调用getSettings接口，将获得WebSettings对象
        //WebSettings对象可用于修改WebView的一些配置
        //这里启用JavaScript，并不是所有网页均需要启动JavaScript
        mWebView.getSettings().setJavaScriptEnabled(true);

        //设置WebChromeClient
        //WebChromeClient是一个事件接口，用于响应那些改变浏览器中装饰元素的事件
        //包括JavaScript警告信息、网页图标、状态条加载、网页标题刷新等
        mWebView.setWebChromeClient(new WebChromeClient() {

/*            //在这里我们重写onProgressChanged，刷新ProgressBar
            public void onProgressChanged(WebView webView, int newProgress) {
                if (newProgress == 100) {
                    mProgressBar.setVisibility(View.GONE);
                } else {
                    mProgressBar.setVisibility(View.VISIBLE);
                    mProgressBar.setProgress(newProgress);
                }
            }*/

/*            //重写onReceivedTitle，刷新标题栏
            public void onReceivedTitle(WebView webView, String title) {
                AppCompatActivity activity = (AppCompatActivity) getActivity();
                if (activity != null && activity.getSupportActionBar() != null) {
                    activity.getSupportActionBar().setSubtitle(title);
                }
            }*/
        });

        //设置WebViewClient
        //WebViewClient也是一个事件接口，可以实现WebViewClient的各种回调接口来响应各种渲染事件
        mWebView.setWebViewClient(new WebViewClient() {

            //这里覆盖了shouldOverrideUrlLoading接口
            //当有新的URL加载到WebView时，该方法会决定对URL的处理行为
            //例如，WebView中加载了一个新的链接时，
            //如果该方法返回false，那么用户点击链接后，将由WebView来加载该链接对应的信息
            //如果该方法返回true，那么用户点击链接后，WebView就不会加载该链接，程序员必须自己定义处理行为
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return  false;
            }
        });
        Intent intent=this.getIntent();
        url=intent.getStringExtra("url");
        //设置完WebView后，才能用WebView加载网络信息
        mWebView.loadUrl(url);
        /*WebView相关设置完毕*/
    }

}
