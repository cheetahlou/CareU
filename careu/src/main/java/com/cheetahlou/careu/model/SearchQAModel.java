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
     * 患者问题标题
     */
    private String userQTitle;

    /**
     * 患者问题详情
     */
    private String userQDetail;

    /**
     * 问答时间
     */
    private String qDate;
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

    public String getUserQTitle() {
        return userQTitle;
    }

    public void setUserQTitle(String userQTitle) {
        this.userQTitle = userQTitle;
    }

    public String getUserQDetail() {
        return userQDetail;
    }

    public void setUserQDetail(String userQDetail) {
        this.userQDetail = userQDetail;
    }

    public String getqDate() {
        return qDate;
    }

    public void setqDate(String qDate) {
        this.qDate = qDate;
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
