package com.yang.cae.function.setting.pojo;


import java.io.Serializable;
import java.util.Date;

public class UserBasicInformation implements Serializable {
    private static final long serialVersionUID =  1L;
    /**
     * 用户基本信息id
     */
    private String Id;
    /**
     * 用户基本信息昵称
     */

    private String nickName;
    /**
     * 创建见时间
     */

    private String createTime;
    /**
     * 用户基本信息手机号
     */
    private String phoneNumber;
    /**
     * 用户基本信息邮箱
     */
    private String email;
    /**
     * 用户基本信息职业id
     */
    private String professionId;
    /**
     * 用户基本信息职业
     */
    private String profession;
    /**
     * 用户基本信息专业id
     */
    private String majorId;
    /**
     * 用户基本信息专业
     */
    private String major;
    /**
     * 用户基本信息地址
     */
    private String address;
    /**
     * 用户基本信息是否删除
     */
    private String  isDeleted;
    /**
     * 用户基本信息更改时间
     */
    private String updateTime;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfessionId() {
        return professionId;
    }

    public void setProfessionId(String professionId) {
        this.professionId = professionId;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getMajorId() {
        return majorId;
    }

    public void setMajorId(String majorId) {
        this.majorId = majorId;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
