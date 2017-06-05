package com.cheetahlou.careu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cheetahlou.careu.R;
import com.cheetahlou.careu.model.SearchQAModel;

import java.util.List;

/**
 * 一问医答Adapter
 * Created by Cheetahlou on 2017/5/28.
 */

public class SearchOnlineAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener, View.OnLongClickListener {

    private Context context;
    private List<SearchQAModel> qaModelList;

    //适配器初始化
    public SearchOnlineAdapter(Context context,List<SearchQAModel> qaModelList) {
        this.context = context;
        this.qaModelList = qaModelList;
    }

/*    public void setSearchQAModelList(List<SearchQAModel> qaModelList) {
        this.qaModelList = qaModelList;
    }*/

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //加载布局
         SearchQAViewHolder holder= new SearchQAViewHolder(LayoutInflater.from(context).inflate(R.layout.searchqa_item,parent,false));
        return holder;
    }

    //将数据与item视图绑定
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder,int position) {
        SearchQAViewHolder viewHolder=(SearchQAViewHolder)holder;
        //更新数据
        viewHolder.bindData(qaModelList.get(position));

    }

    @Override
    public int getItemCount() {
        return qaModelList.size();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }

    class SearchQAViewHolder extends RecyclerView.ViewHolder{

        private TextView tvSearchUrl;
        private TextView tvSearchQTitle;
        private TextView tvSearchQDate;
        private TextView tvSearchQDetail;
        private TextView tvDoctorName;
        private TextView tvDoctorHospital;
        private TextView tvSearchA;

        public SearchQAViewHolder(View itemView) {
            super(itemView);
            tvSearchUrl= (TextView) itemView.findViewById(R.id.tv_search_url);
            tvSearchQTitle= (TextView) itemView.findViewById(R.id.tv_search_qtitle);
            tvSearchQDate= (TextView) itemView.findViewById(R.id.tv_search_qdate);
            tvSearchQDetail= (TextView) itemView.findViewById(R.id.tv_search_qdetail);
            tvDoctorName= (TextView) itemView.findViewById(R.id.doctor_name);
            tvDoctorHospital= (TextView) itemView.findViewById(R.id.doctor_hospital);
            tvSearchA= (TextView) itemView.findViewById(R.id.tv_search_a);
        }

        //更新数据，刷新UI
        public void bindData(SearchQAModel searchQAModel){
            tvSearchUrl.setText(searchQAModel.getUrl());
            tvSearchQTitle.setText(searchQAModel.getUserQTitle());
            tvSearchQDate.setText(searchQAModel.getqDate());
            tvSearchQDetail.setText(searchQAModel.getUserQDetail());
            tvDoctorName.setText(searchQAModel.getDoctorName());
            tvDoctorHospital.setText(searchQAModel.getDoctorHospital());
            tvSearchA.setText(searchQAModel.getDoctorA());
        }

    }
}
