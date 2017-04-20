package com.gjb.babyplan;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/6/22.
 */
public class MemorySQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="mem.db";
    private static final int DB_VERSION=1;
    /*
 * 构造函数 第一个参数 上下文对象 第二个参数 数据库名称 第三参数 结果集 默认为空 第四个参数 版本号 从1开始 只能升级不能降级
 * 只有版本号的升级才能调用 onUpgrade方法
   */
    public MemorySQLiteHelper(Context context) {
        super(context,DATABASE_NAME , null, DB_VERSION);
    }

    // onCreate方法 只在第一次创建数据库的时候调用
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table mem (_id integer primary key autoincrement,memtime text,memcontent text,imageUrl text)");
    }

    // onUpgrade方法 只在版本号升级的时候调用该方法
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }



}
