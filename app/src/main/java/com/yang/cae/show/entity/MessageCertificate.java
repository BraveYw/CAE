package com.yang.cae.show.entity;


import java.io.Serializable;
import java.util.Date;

/**
 * 证书信息表
 */

public class MessageCertificate implements Serializable {
    private static final long serialVersionUID =  1L;

    /**
     * 考试信息id
     */

    private String id;

    /**
     * 证书名称
     */

    private String certificateName;

    /**
     * 考试时长
     */

    private String examTime;

    /**
     * 考试开始报名时间
     */

    private String startRegistrationTime;

    /**
     * 考试截止时间
     */

    private String endRegistrationTime;

    /**
     * 报名方式
     */

    private String registrationWay;

    /**
     * 报名地址
     */

    private String registrationAddress;

    /**
     * 报名需要材料
     */

    private String registrationPrepareMaterial;

    /**
     * 考试开始时间
     */

    private String startExamTime;

    /**
     * 考试结束时间
     */

    private String endExamTime;

    /**
     * 考试地点
     */

    private String examAddress;

    /**
     * 考试材料
     */

    private String prepareMaterial;

    /**
     * 备考信息
     */

    private String examPreparationMessage;

    /**
     * 性价比
     */

    private String costEffective;

    /**
     * 备注
     */

    private String remark;


    private String recommendMajor;


    private String recommendProfession;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCertificateName() {
        return certificateName;
    }

    public void setCertificateName(String certificateName) {
        this.certificateName = certificateName;
    }

    public String getExamTime() {
        return examTime;
    }

    public void setExamTime(String examTime) {
        this.examTime = examTime;
    }

    public String getStartRegistrationTime() {
        return startRegistrationTime;
    }

    public void setStartRegistrationTime(String startRegistrationTime) {
        this.startRegistrationTime = startRegistrationTime;
    }

    public String getEndRegistrationTime() {
        return endRegistrationTime;
    }

    public void setEndRegistrationTime(String endRegistrationTime) {
        this.endRegistrationTime = endRegistrationTime;
    }

    public String getRegistrationWay() {
        return registrationWay;
    }

    public void setRegistrationWay(String registrationWay) {
        this.registrationWay = registrationWay;
    }

    public String getRegistrationAddress() {
        return registrationAddress;
    }

    public void setRegistrationAddress(String registrationAddress) {
        this.registrationAddress = registrationAddress;
    }

    public String getRegistrationPrepareMaterial() {
        return registrationPrepareMaterial;
    }

    public void setRegistrationPrepareMaterial(String registrationPrepareMaterial) {
        this.registrationPrepareMaterial = registrationPrepareMaterial;
    }

    public String getStartExamTime() {
        return startExamTime;
    }

    public void setStartExamTime(String startExamTime) {
        this.startExamTime = startExamTime;
    }

    public String getEndExamTime() {
        return endExamTime;
    }

    public void setEndExamTime(String endExamTime) {
        this.endExamTime = endExamTime;
    }

    public String getExamAddress() {
        return examAddress;
    }

    public void setExamAddress(String examAddress) {
        this.examAddress = examAddress;
    }

    public String getPrepareMaterial() {
        return prepareMaterial;
    }

    public void setPrepareMaterial(String prepareMaterial) {
        this.prepareMaterial = prepareMaterial;
    }

    public String getExamPreparationMessage() {
        return examPreparationMessage;
    }

    public void setExamPreparationMessage(String examPreparationMessage) {
        this.examPreparationMessage = examPreparationMessage;
    }

    public String getCostEffective() {
        return costEffective;
    }

    public void setCostEffective(String costEffective) {
        this.costEffective = costEffective;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRecommendMajor() {
        return recommendMajor;
    }

    public void setRecommendMajor(String recommendMajor) {
        this.recommendMajor = recommendMajor;
    }

    public String getRecommendProfession() {
        return recommendProfession;
    }

    public void setRecommendProfession(String recommendProfession) {
        this.recommendProfession = recommendProfession;
    }

    @Override
    public String toString() {
        return "MessageCertificate{" +
                "id='" + id + '\'' +
                ", certificateName='" + certificateName + '\'' +
                ", examTime='" + examTime + '\'' +
                ", startRegistrationTime='" + startRegistrationTime + '\'' +
                ", endRegistrationTime='" + endRegistrationTime + '\'' +
                ", registrationWay='" + registrationWay + '\'' +
                ", registrationAddress='" + registrationAddress + '\'' +
                ", registrationPrepareMaterial='" + registrationPrepareMaterial + '\'' +
                ", startExamTime='" + startExamTime + '\'' +
                ", endExamTime='" + endExamTime + '\'' +
                ", examAddress='" + examAddress + '\'' +
                ", prepareMaterial='" + prepareMaterial + '\'' +
                ", examPreparationMessage='" + examPreparationMessage + '\'' +
                ", costEffective='" + costEffective + '\'' +
                ", remark='" + remark + '\'' +
                ", recommendMajor='" + recommendMajor + '\'' +
                ", recommendProfession='" + recommendProfession + '\'' +
                '}';
    }
}
