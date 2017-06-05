package com.cheetahlou.careu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cheetahlou.careu.R;
import com.cheetahlou.careu.model.HealthInfoModel;

import java.util.List;

/**
 * 健康资讯Adapter
 * Created by Cheetahlou on 2017/5/28.
 */

public class HealthInfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener, View.OnLongClickListener {

    private Context context;
    private List<HealthInfoModel> hInfoList;

    //适配器初始化
    public HealthInfoAdapter(Context context, List<HealthInfoModel> hInfoList) {
        this.context = context;
        this.hInfoList = hInfoList;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView=LayoutInflater.from(context).inflate(R.layout.health_info_item,parent,false);
        //加载布局
        HealthInfoViewHolder holder= new HealthInfoViewHolder(itemView);
        //item点击事件
        itemView.setOnClickListener(this);
        return holder;
    }

    //将数据与item视图绑定
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder,int position) {
        //将position保存在itemView的tag中，一边点击时获取
        holder.itemView.setTag(position);
        HealthInfoViewHolder viewHolder=(HealthInfoViewHolder)holder;
        //更新数据
        viewHolder.bindData(hInfoList.get(position));


    }

    @Override
    public int getItemCount() {
        return hInfoList.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, (int) v.getTag());//注意这里使用getTag方法获取position
        }
    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }

    class HealthInfoViewHolder extends RecyclerView.ViewHolder{

        private TextView tvHInfoUrl;
        private TextView tvHInfoTime;
        private TextView tvHInfoTitle;
        private TextView tvHInfoAbstract;
        private ImageView ivHInfoImage;

        public HealthInfoViewHolder(View itemView) {
            super(itemView);
            tvHInfoUrl= (TextView) itemView.findViewById(R.id.tv_hinfo_url);
            tvHInfoTime= (TextView) itemView.findViewById(R.id.tv_hinfo_time);
            tvHInfoTitle= (TextView) itemView.findViewById(R.id.tv_hinfo_title);
            tvHInfoAbstract= (TextView) itemView.findViewById(R.id.tv_hinfo_abstract);
            ivHInfoImage=(ImageView)itemView.findViewById(R.id.iv_hinfo_image);
        }

        //更新数据，刷新UI
        public void bindData(HealthInfoModel healthInfoModel){
            tvHInfoUrl.setText(healthInfoModel.getUrl());
            tvHInfoTime.setText(healthInfoModel.getTime());
            tvHInfoTitle.setText(healthInfoModel.getTitle());
            tvHInfoAbstract.setText(healthInfoModel.gethAbstract());
            if(!healthInfoModel.getImageUrl().isEmpty()){
                //加载网络上的图片
                Glide.with(context).load(healthInfoModel.getImageUrl()).into(ivHInfoImage);
//                Picasso.with(context).load(healthInfoModel.getImageUrl()).resize(125,100).into(ivHInfoImage);
            }else{
                ivHInfoImage.setVisibility(View.GONE);
            }
        }

    }
    /** * ItemClick的回调接口 */
    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }

}
