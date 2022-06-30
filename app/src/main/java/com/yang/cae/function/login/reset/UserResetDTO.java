package com.yang.cae.function.login.reset;

import java.io.Serializable;

public class UserResetDTO implements Serializable {

    /**
     * 用户验证码  5分钟
     */
    private String authCode;
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 用户密码
     */
    private String password;

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public String toString() {
        return "UserResetDTO{" +
                "authCode='" + authCode + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
