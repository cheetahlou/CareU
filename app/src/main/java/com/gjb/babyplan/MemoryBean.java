package com.gjb.babyplan;

/**
 * Created by Administrator on 2016/6/21.
 */
public class MemoryBean {
    private String memtime;
    private String memcontent;
    private String imageUrl;

    public MemoryBean(String memtime, String memcontent, String imageUrl) {
        this.memtime = memtime;
        this.memcontent = memcontent;
        this.imageUrl = imageUrl;
    }

    public MemoryBean(){

    }

    public String getMemtime() {
        return memtime;
    }

    public void setMemtime(String memtime) {
        this.memtime = memtime;
    }

    public String getMemcontent() {
        return memcontent;
    }

    public void setMemcontent(String memcontent) {
        this.memcontent = memcontent;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
