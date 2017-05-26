package com.cheetahlou.careu.model;

/**
 * 问答结果model,用于组装QAList
 * Created by Cheetahlou on 2017/5/25.
 */

public class SearchQAModel {

    public SearchQAModel() {
    }

    /**
     * 具体网址
     */
    private String url;
    /**
     * 患者问题
     */
    private String userQ;
    /**
     * 医生姓名
     */
    private String doctorName;
    /**
     * 医生认证的医院
     */
    private String doctorHospital;

    /**
     * 医生回答
     */
    private String doctorA;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserQ() {
        return userQ;
    }

    public void setUserQ(String userQ) {
        this.userQ = userQ;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorHospital() {
        return doctorHospital;
    }

    public void setDoctorHospital(String doctorHospital) {
        this.doctorHospital = doctorHospital;
    }

    public String getDoctorA() {
        return doctorA;
    }

    public void setDoctorA(String doctorA) {
        this.doctorA = doctorA;
    }
}
