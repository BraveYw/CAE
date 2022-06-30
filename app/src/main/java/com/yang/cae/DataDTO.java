package com.yang.cae;

import java.io.Serializable;

public class DataDTO implements Serializable {
    private String flag;
    private String messageId;
    private String messageName;
    private String date;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMessageName() {
        return messageName;
    }

    public void setMessageName(String messageName) {
        this.messageName = messageName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "DataDTO{" +
                "flag='" + flag + '\'' +
                ", messageId='" + messageId + '\'' +
                ", messageName='" + messageName + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
