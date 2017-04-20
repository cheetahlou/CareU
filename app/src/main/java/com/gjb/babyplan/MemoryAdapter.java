package com.gjb.babyplan;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by Administrator on 2016/6/20.
 */
public class MemoryAdapter extends BaseAdapter {
    private Context context;
    private ListView listView;
    private List<MemoryBean> memlist;
    private LruCache<String,BitmapDrawable> memimgCache;
    private MemoryDAO dao;

    public MemoryAdapter(Context context,List<MemoryBean> memlist){
        this.memlist=memlist;
        this.context=context;
        //设置图片缓存大小
        int maxCache = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxCache / 8;
        memimgCache = new LruCache<String, BitmapDrawable>(cacheSize) {
            @Override
            protected int sizeOf(String key, BitmapDrawable value) {
                return value.getBitmap().getByteCount();
            }
        };
    }
    public MemoryAdapter(){

    }
    @Override
    public int getCount() {
        return memlist.size();
    }

    @Override
    public Object getItem(int position) {
        return memlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (listView == null) {
            listView = (ListView) parent;//初始化listview，后面点击事件中会用到
        }
        ViewHolder holder;
    if(convertView==null){
    convertView= LayoutInflater.from(context).inflate(R.layout.memory_item,null);
    holder=new ViewHolder();
    holder.tvmemtime=(TextView)convertView.findViewById(R.id.memtime);
    holder.tvmemcontent=(TextView)convertView.findViewById(R.id.memcontent);
    holder.ivimg=(ImageView)convertView.findViewById(R.id.memimg);
        holder.btnmemdelete=(Button)convertView.findViewById(R.id.btnmemdelete);
        convertView.setTag(holder);
} else {
        holder = (ViewHolder) convertView.getTag();
    }
        MemoryBean memory=memlist.get(position);
        holder.tvmemtime.setText(memory.getMemtime());
        holder.tvmemcontent.setText(memory.getMemcontent());
        holder.ivimg.setTag(memory.getImageUrl());//用imageUrl作tag
        holder.tvmemcontent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context,MainActivity.class));
              //  listView.setAdapter(new MemoryAdapter(context,memlist));//想进入发表的内容修改界面来着
            }
        });

        //删除该列表项的宝贝时刻
        holder.btnmemdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String urlhere=memlist.get(position).getImageUrl().toString();
                Toast.makeText(context, memlist.get(position).getImageUrl().toString(), Toast.LENGTH_SHORT).show();
                dao=new MemoryDAO(context);
                int zz=dao.deleteMem(urlhere);//从数据库中删除该条记录
                memlist=dao.listAllMem();
                listView.setAdapter(new MemoryAdapter(context,memlist));//重新获取adapter，达到刷新的效果
            }
        });
        // 如果本地已有缓存，就从本地读取，否则从网络请求数据
        if (memimgCache.get(memory.getImageUrl()) != null) {
            holder.ivimg.setImageDrawable(memimgCache.get(memory.getImageUrl()));
        } else {
            ImageTask it = new ImageTask();
            it.execute(memory.getImageUrl());
        }
        return convertView;
    }

    private static class ViewHolder{
        TextView tvmemtime;//发表时间
        TextView tvmemcontent;//发表内容
        ImageView ivimg;//发表的已上传的图片返回的url
        Button btnmemdelete;//删除记录按钮
    }

    private class ImageTask extends AsyncTask<String,Void,BitmapDrawable>{

        String imageUrl;
        @Override
        protected BitmapDrawable doInBackground(String... params) {
            imageUrl=params[0];
            Bitmap bitmap=downloadImage();
            BitmapDrawable bd=new BitmapDrawable(context.getResources(),bitmap);//Create drawable from a bitmap, setting initial target density based on
          //如果本地没有缓存，则先缓存到本地
           if(memimgCache.get(imageUrl)==null){
                memimgCache.put(imageUrl,bd);
           }
            return bd;
        }

        protected void onPostExecute(BitmapDrawable result){
            // 通过Tag找到我们需要的ImageView，如果该ImageView所在的item已被移出页面，就会直接返回null
            ImageView iv = (ImageView) listView.findViewWithTag(imageUrl);
            if (iv != null && result != null) {
                iv.setImageDrawable(result);
            }
        }
        /**
         * 根据url从网络上下载图片
         *
         * @return
         */
        private Bitmap downloadImage() {
            HttpURLConnection con = null;
            Bitmap bitmap = null;
            try {
                URL url = new URL(imageUrl);
                con = (HttpURLConnection) url.openConnection();
                con.setConnectTimeout(5 * 1000);
                con.setReadTimeout(10 * 1000);
                bitmap = BitmapFactory.decodeStream(con.getInputStream());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (con != null) {
                    con.disconnect();
                }
            }

            return bitmap;
        }
    }
}
