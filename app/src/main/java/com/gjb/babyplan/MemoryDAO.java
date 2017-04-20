package com.gjb.babyplan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/23.
 */
public class MemoryDAO {
    private MemorySQLiteHelper sqliteHelper;


    public MemoryDAO(Context context){
        sqliteHelper=new MemorySQLiteHelper(context);
    }
    //添加数据
    public void insertMem(MemoryBean memoryBean){
        SQLiteDatabase db=sqliteHelper.getReadableDatabase();
        db.execSQL("insert into mem(memtime,memcontent,imageUrl) values (?,?,?)",
                new Object[]{memoryBean.getMemtime(),memoryBean.getMemcontent(),memoryBean.getImageUrl()});
    }
    //列出全部
    public List<MemoryBean> listAllMem(){
        SQLiteDatabase db=sqliteHelper.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM mem",new String[]{});
        List<MemoryBean> list=new ArrayList<MemoryBean>();
        //使用moveToNext逐条读取
        while(cursor.moveToNext()){
            String time=cursor.getString(cursor.getColumnIndex("memtime"));
            String content=cursor.getString(cursor.getColumnIndex("memcontent"));
            String imageUrl=cursor.getString(cursor.getColumnIndex("imageUrl"));
            MemoryBean memoryBean=new MemoryBean(time,content,imageUrl);
            list.add(memoryBean);
        }
        cursor.close();
        return list;
    }

    public  int deleteMem(String url){
        SQLiteDatabase db=sqliteHelper.getReadableDatabase();
        int delResult = db.delete("mem", "imageUrl = ?", new String[] { url });
        db.close();
        return delResult;
    }

    public int updateMem(String oldcontent,String newcontent){
        SQLiteDatabase db=sqliteHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("memcontent", newcontent);
        int updResult = db.update("mem",values, "memcontent = ?", new String[] { oldcontent });
        db.close();
        return updResult;
    }
}
