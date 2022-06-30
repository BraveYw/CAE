package com.yang.cae.function.login.register.pojo;


import java.io.Serializable;

/**
 * 专业信息表
 */

public class NameMajor implements Serializable {
    private static final long serialVersionUID =  1L;
    /**
     * 专业信息表id
     */

    private String Id;

    /**
     * 专业名称
     */

    private String majorName;
    /**
     * 专业分类
     */

    private String classification;


    public NameMajor() {
    }

    public NameMajor(String id, String majorName, String classification) {
        Id = id;
        this.majorName = majorName;
        this.classification = classification;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }


    @Override
    public String toString() {
        return majorName;
    }
}
