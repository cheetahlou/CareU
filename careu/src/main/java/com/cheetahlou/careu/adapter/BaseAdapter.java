package com.cheetahlou.careu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 封装后Adapter，ViewHolder
 * Created by Cheetahlou on 2017/5/31.
 */

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseHolder> {
    Context context;
    List<T> data;
    int []layoutIds;//布局资源Id数组layoutIds中
    public BaseAdapter(Context context, List<T> data, int layoutId) {
        this.context = context;
        this.data = data;
        //不使用多布局时的构造函数，将layoutId放到布局资源Id数组layoutIds中
        this.layoutIds = new int[]{layoutId};
    }
    //使用多布局时的构造函数，可以接收传入的布局资源Id数组
    public BaseAdapter(Context context, List<T> data, int[] layoutIds) {
        this.context = context;
        this.data = data;
        this.layoutIds = layoutIds;
    }
    public void setData(List<T> data) {
        this.data = data;
    }
    public List<T> getData() {
        return this.data;
    }
    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }
    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //根据viewType从布局资源数组中加载指定的布局
        //1.当不需要加载多布局时，子类继承BaseAdapter，不用覆写getItemViewType()
        //此时，viewType值默认为0；
        //2.当需要加载多布局时，子类继承MultiLayoutsBaseAdapter，需要覆写getItemViewType()
        //此时，viewType值为子类adapter指定的值；
        return BaseHolder.getHolder(context, parent, layoutIds[viewType]);
    }
    @Override
    public void onBindViewHolder(BaseHolder holder, int position) {
        onBind(holder, data.get(position), position);
    }
    public abstract void onBind(BaseHolder holder, T t, int position);
}

class BaseHolder extends RecyclerView.ViewHolder {
    View itemView;
    SparseArray<View> views;//存放Item中的控件
    public BaseHolder(View itemView){
        super(itemView);
        this.itemView = itemView;
        views = new SparseArray<View>();
    }
    //供adapter调用，返回holder
    public static <T extends BaseHolder> T getHolder(Context cxt, ViewGroup parent, int layoutId){
        return (T) new BaseHolder(LayoutInflater.from(cxt).inflate(layoutId, parent, false));
    }
    //根据Item中的控件Id获取控件
    public <T extends View> T getView(int viewId){
        View childreView = views.get(viewId);
        if (childreView == null){
            childreView = itemView.findViewById(viewId);
            views.put(viewId, childreView);
        }
        return (T) childreView;
    }
    //根据Item中的控件Id向控件添加事件监听
    public BaseHolder setOnClickListener(int viewId, View.OnClickListener listener){
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }
}
