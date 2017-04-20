package com.gjb.babyplan;


import android.test.AndroidTestCase;

import java.util.List;

/**
 * Created by Administrator on 2016/6/23.
 */

public class TestMemorySQLite extends AndroidTestCase {

    private MemoryDAO dao;

    protected void setUp() throws Exception {
        dao=new MemoryDAO(getContext());

    }

    public void create() throws Exception{
//        dao=new MemoryDAO(getContext());
        MemoryBean bean=new MemoryBean("aaa","bbb","ccc");
        dao.insertMem(bean);
    }
    public List<MemoryBean> listmem() throws Exception{
//        dao=new MemoryDAO(getContext());
        List<MemoryBean> list=dao.listAllMem();
        System.out.println(list.get(0).getMemcontent());

        return list;
    }
}
