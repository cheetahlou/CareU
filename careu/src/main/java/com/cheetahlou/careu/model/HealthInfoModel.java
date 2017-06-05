package com.cheetahlou.careu.model;

/**
 * 健康资讯model,用于组装List
 * Created by Cheetahlou on 2017/5/25.
 */

public class HealthInfoModel {

    public HealthInfoModel() {
    }

    /**
     * 具体网址
     */
    private String url;
    /**
     * 时间
     */
    private String time;
    /**
     * 标题
     */
    private String title;

    /**
     * 摘要
     */
    private String hAbstract;
    /**
     * 图片网址
     */
    private String imageUrl;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String gethAbstract() {
        return hAbstract;
    }

    public void sethAbstract(String hAbstract) {
        this.hAbstract = hAbstract;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
