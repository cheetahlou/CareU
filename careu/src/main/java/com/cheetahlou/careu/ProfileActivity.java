package com.cheetahlou.careu;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCreatedCallback;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.cheetahlou.careu.chat.ContactFragment;
import com.cheetahlou.careu.chat.CustomUserProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.leancloud.chatkit.LCChatKit;
import cn.leancloud.chatkit.LCChatKitUser;
import cn.leancloud.chatkit.activity.LCIMConversationActivity;
import cn.leancloud.chatkit.activity.LCIMConversationListFragment;
import cn.leancloud.chatkit.utils.LCIMConstants;


/**
 * 主界面Activity，可呼出侧滑菜单
 */
public class ProfileActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static ProfileActivity profileActivity=null;

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Context context;

    /**
     * 上一次点击 back 键的时间
     * 用于双击退出的判断
     */
    private static long lastBackTime = 0;

    /**
     * 当双击 back 键在此间隔内是直接触发 onBackPressed
     */
    private final int BACK_INTERVAL = 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setTitle(R.string.app_name);
        //初始化布局控件等
        init();
        //Toolbar的设置
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initTabLayout();
        //悬浮按钮控件
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //设置DrawerLayout
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //设置NavigationView
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //添加侧滑菜单的头部
        View navHeaderView = navigationView.inflateHeaderView(R.layout.nav_header_profile);

        //设置头像点击事件
        ImageView headIv = (ImageView) navHeaderView.findViewById(R.id.head_iv);
        TextView tv_nav_name=(TextView)navHeaderView.findViewById(R.id.nav_name);
        tv_nav_name.setText(getSharedPreferences("profile",MODE_PRIVATE).getString("userNickName",""));

        String url="http://www.avatarsdb.com/avatars/two_cat3.jpg";
        RequestOptions myoptions = new RequestOptions()
                .centerCrop()
                //使用圆形裁剪
                .circleCrop()
                .placeholder(R.drawable.appicon)
                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);

        Glide.with(context)
//                .load(R.drawable.blue_cheetah)
                .load(url)
                .apply(myoptions)
                .into(headIv);

        RequestBuilder<Drawable> requestBuilder = Glide.with(context)
                .load(url);


/*        //Glide V3圆形变换使用方法，在V4中placeholder和bitmapTransform不是这样用
        Glide.with(context)
                .load(R.drawable.blue_cheetah)
                .placeholder(R.mipmap.ic_launcher)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .bitmapTransform(new CropCircleTransformation(context)) //使用圆形变换，还可以使用其他的变换
                .into(headIv);*/
        /*
        Glide.with(this)
                .load(R.drawable.blue_cheetah)
                .bitmapTransform(new CropCircleTransformation(this))
                .crossFade(1000).
                into(headIv);*/
       /* Glide.with(this).load(R.drawable.blue_cheetah)
                .bitmapTransform(new BlurTransformation(context, 25), new CropCircleTransformation(context))
                .into((ImageView) findViewById(R.id.image));*/


        headIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent().setClass(context,MeActivity.class));
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                //关闭侧滑菜单
                drawer.closeDrawer(GravityCompat.START);
            }

        });

        profileActivity=this;
    }

    //按下返回键
    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastBackTime < BACK_INTERVAL) {
            super.onBackPressed();
        } else {
            Toast.makeText(this, "再按一次返回键退出", Toast.LENGTH_SHORT).show();
        }
        lastBackTime = currentTime;
    }

/*    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_square, menu);
        return true;
    }

/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuId = item.getItemId();
        if (menuId == R.id.menu_square_members) {
            gotoSquareConversation();
        }
        return super.onOptionsItemSelected(item);
    }

 /*   @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_med_reports) {
           // 打开我的病历本
            startActivity(new Intent(context,MedicalRecordsActivity.class));
        } else if (id == R.id.nav_search) {
            //打开一问医答
            startActivity(new Intent(context,SearchOnilneActivity.class));
        } else if (id == R.id.nav_health_info) {
            //打开健康资讯
            startActivity(new Intent(context,HealthInfoActivity.class));
        } else if (id == R.id.nav_collection) {
            //我的收藏
        } else if (id == R.id.nav_manage) {

        }  else if (id == R.id.nav_feedback) {

        }else if (id == R.id.nav_about) {
//            打开关于
            startActivity(new Intent(context,AboutActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //关闭侧滑菜单
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void initTabLayout() {
        String[] tabList = new String[]{"会话", "联系人"};
        final Fragment[] fragmentList = new Fragment[] {new LCIMConversationListFragment(),
                new ContactFragment()};

        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        for (int i = 0; i < tabList.length; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(tabList[i]));
        }

        TabFragmentAdapter adapter = new TabFragmentAdapter(getSupportFragmentManager(),
                Arrays.asList(fragmentList), Arrays.asList(tabList));
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (0 == position) {
//          EventBus.getDefault().post(new ConversationFragmentUpdateEvent());
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabsFromPagerAdapter(adapter);
    }



    public class TabFragmentAdapter extends FragmentStatePagerAdapter {

        private List<Fragment> mFragments;
        private List<String> mTitles;

        public TabFragmentAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
            super(fm);
            mFragments = fragments;
            mTitles = titles;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position);
        }
    }

    //进入广场
    private void gotoSquareConversation() {
        List<LCChatKitUser> userList = CustomUserProvider.getInstance().getAllUsers();
        List<String> idList = new ArrayList<>();
        for (LCChatKitUser user : userList) {
            idList.add(user.getUserId());
        }
        LCChatKit.getInstance().getClient().createConversation(
                idList, getString(R.string.square), null, false, true, new AVIMConversationCreatedCallback() {
                    @Override
                    public void done(AVIMConversation avimConversation, AVIMException e) {
                        Intent intent = new Intent(context, LCIMConversationActivity.class);
                        intent.putExtra(LCIMConstants.CONVERSATION_ID, avimConversation.getConversationId());
                        startActivity(intent);
                    }
                });
    }

    private void toast(String zz) {
        Toast.makeText(context, zz, Toast.LENGTH_SHORT).show();
    }
    private void init(){
        setTitle(R.string.app_name);
        viewPager = (ViewPager)findViewById(R.id.vp_contact);
        tabLayout = (TabLayout)findViewById(R.id.tabl_contact);
        context=this;
    }
}
