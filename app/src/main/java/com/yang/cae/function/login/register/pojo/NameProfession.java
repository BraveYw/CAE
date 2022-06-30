package com.yang.cae.function.login.register.pojo;


import java.io.Serializable;

/**
 * 职业信息表
 */

public class NameProfession implements Serializable {
    private static final long serialVersionUID =  1L;
    /**
     * 职业信息id
     */

    private String Id;
    /**
     * 职业名称
     */

    private String professionName;
    /**
     * 职业分类
     */

    private String classification;

    public NameProfession() {
    }

    public NameProfession(String id, String professionName, String classification) {
        Id = id;
        this.professionName = professionName;
        this.classification = classification;
    }

    @Override
    public String toString() {
        return professionName;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getProfessionName() {
        return professionName;
    }

    public void setProfessionName(String professionName) {
        this.professionName = professionName;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }
}
