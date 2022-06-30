package com.yang.cae.function.login.register.pojo;

import java.io.Serializable;

public class UserRegisterDTO implements Serializable {
    private static final long serialVersionUID =  1L;

    /**
     * 用户基本信息昵称
     */
    private String nickName;
    /**
     * 用户基本信息手机号
     */
    private String phoneNumber;
    /**
     * 用户邮箱
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
     * 密码
     */
    private String  password;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
