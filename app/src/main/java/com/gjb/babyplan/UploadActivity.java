package com.gjb.babyplan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.SaveCallback;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class UploadActivity extends Activity {
    private Button btnupload,btnback;
    private ImageView iv_image;
    private EditText etmemcontent;
    private static String url;
    private Context context;
    private MemoryDAO dao;

    URL myFileUrl = null;
    Bitmap bitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        init();
    }


    protected void onResume(){
        super.onResume();
/*        new Thread(new Runnable() {
            @Override
            public void run() {
                //bitmap=getHttpBitmap("http://ac-t3s5raai.clouddn.com/iWUVdv87wVbxjdjxEvqeiUbHgJsiB6NMxZa5R8c0");
                iv_image.setImageBitmap(bitmap);
                UploadActivity.this.runOnUiThread(new Runnable()
                {
                    public void run()
                    {
                        //Do your UI operations like dialog opening or Toast here

                    }
                });

            }
        }).start();*/
    }

    private class UpdateTask extends AsyncTask<String, String,String> {
        protected String doInBackground(String... urls) {
            try {
                myFileUrl = new URL(url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
                conn.setConnectTimeout(0);
                conn.setDoInput(true);
                conn.connect();
                InputStream is = conn.getInputStream();
                bitmap = BitmapFactory.decodeStream(is);
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
    public  Bitmap getHttpBitmap(String url) {
        this.url=url;
        new UpdateTask().execute(this.url);
        return bitmap;
    }
    // 打开系统图库，选择一张图片
    public void load(View view) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 0);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            // 得到图片的全路径
            Uri uri = data.getData();
            // toast(uri.toString());
            url=getImagePath(data);
            toast(url);
            // 通过路径加载图片
            //这里省去了图片缩放操作，如果图片过大，可能会导致内存泄漏
            //图片缩放的实现，请看：http://blog.csdn.net/reality_jie_blog/article/details/16891095

            this.iv_image.setImageURI(uri);

            // 获取图片的缩略图，可能为空！
            // Bitmap bitmap = data.getParcelableExtra("data");
            // this.iv_image.setImageBitmap(bitmap);

        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private Button.OnClickListener uploadls= new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(etmemcontent.getText().toString().equals("")){
                etmemcontent.setError("请先说点什么吧",null);
            }else{
                try {
                    //final AVFile file=AVFile.withAbsoluteLocalPath("IU.jpg", Environment.getExternalStorageDirectory()+"/IU.jpg");
                    final AVFile file=AVFile.withAbsoluteLocalPath("IU.jpg", url);
                    file.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            toast("上传成功");
                            toast(file.getUrl());
                            Time t=new Time();
                            t.setToNow();//取得系统当前时间时间
                            String time=t.year+"年"+t.month+"月"+t.monthDay+"日"+"  "+t.hour+":"+t.minute+":"+t.second;
                            String content=etmemcontent.getText().toString();
                            String imageUrl=file.getUrl();
                            dao=new MemoryDAO(context);
                            dao.insertMem(new MemoryBean(time,content,imageUrl));

                            startActivity(new Intent().setClass(context,MemoryListActivity.class));
                            finish();
                        }
                    });
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(UploadActivity.this, "上传失败！", Toast.LENGTH_SHORT).show();
                }
/*            try {
                final AVFile file1=new AVFile("IU.jpg","http://ac-t3s5raai.clouddn.com/NOQoNOxTKfTzFB8P5USf1xRyYxlb4MdiXs791KgW",new HashMap<String,Object>());
                file1.getDataInBackground(new GetDataCallback() {
                    @Override
                    public void done(byte[] bytes, AVException e) {
                        Toast.makeText(LoginActivity.this, "下载成功！", Toast.LENGTH_SHORT).show();
                    }
                }, new ProgressCallback() {
                    @Override
                    public void done(Integer integer) {

                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(LoginActivity.this, "下载失败", Toast.LENGTH_SHORT).show();
            }*/
            }

        }
    };

    //获得选择的图片的绝对路径Absolute
    public String getImagePath(Intent data){
        Uri imageUri=data.getData();//获得图片的url
        String proj[]={MediaStore.Images.Media.DATA};
        Cursor cursor=managedQuery(imageUri,proj,null,null,null);
        int column_index=cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        //将光标移至开头，不然容易引起越界
        cursor.moveToFirst();
        //根据索引值获取图片路径
        String path=cursor.getString(column_index);
        return path;
    }

/*    //获得当前时间
    public void gettime(){
        Time t=new Time();
        t.setToNow();
        String year=t.year+"";
        String zz="";
        tvtime.setText(year);

    }*/

    private TextView.OnClickListener getTime=new TextView.OnClickListener() {
        @Override
        public void onClick(View v) {
            MemoryDAO dao=new MemoryDAO(context);
            dao.insertMem(new MemoryBean("a","b","http://ac-t3s5raai.clouddn.com/mpMLci6jCiL95O9FAXzrv6oXZpI6biRYKCz8CwBx"));
            List<MemoryBean> list=dao.listAllMem();
            for(MemoryBean mb:list){
                int n=0;
                toast(n+" "+mb.getImageUrl());
                n++;
            }

/*          //取得系统时间
            Time t=new Time();
            t.setToNow();
            String timenow=t.year+"年"+t.month+"月"+t.monthDay+"日"+"  "+t.hour+":"+t.minute+":"+t.second;
            tvtime.setText(timenow);*/
            //SimpleDateFormat formatter=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
/*            SimpleDateFormat formatter=new SimpleDateFormat("MM月dd日  HH:mm");
            Date curDate=new Date(System.currentTimeMillis());//获取当前时间
            String str=formatter.format(curDate);
            tvtime.setText(str);*/

            //查看当前系统时间是24小时制还是12小时制
/*            ContentResolver cv = getContentResolver();
            String strTimeFormat =android.provider.Settings.System.getString(cv,
                    android.provider.Settings.System.TIME_12_24);
            if(strTimeFormat.equals("24"))
            {
                tvtime.setText("24");
            }else if(strTimeFormat.equals("12")){
                tvtime.setText("12");
            }*/
        }
    };

    private Button.OnClickListener back=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent().setClass(context,MemoryListActivity.class));
            finish();
        }
    };
    public void toast(String zz){
        Toast.makeText(UploadActivity.this, zz, Toast.LENGTH_SHORT).show();
    }

    private void init() {
        iv_image = (ImageView) findViewById(R.id.iv_image);
        btnupload=(Button) findViewById(R.id.btnupload);
        etmemcontent=(EditText) findViewById(R.id.etmemcontent);
        btnback=(Button) findViewById(R.id.btnback);
        btnupload.setOnClickListener(uploadls);
        btnback.setOnClickListener(back);
        context=this;
        etmemcontent.requestFocus();
    }

}
