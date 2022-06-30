package com.yang.cae.bean.result;



import com.alibaba.fastjson.JSONArray;

import java.io.Serializable;


public class Result implements Serializable {
   //成功标志
    private boolean success;
    //返回代码
    private Integer code;
    //返回处理消息
    private String message;
    //返回数据对象 data
    private JSONArray data;

     //时间戳
    private long timestamp;

 public boolean isSuccess() {
  return success;
 }

 public void setSuccess(boolean success) {
  this.success = success;
 }

 public Integer getCode() {
  return code;
 }

 public void setCode(Integer code) {
  this.code = code;
 }

 public String getMessage() {
  return message;
 }

 public void setMessage(String message) {
  this.message = message;
 }

 public JSONArray getData() {
  return data;
 }

 public void setData(JSONArray data) {
  this.data = data;
 }

 public long getTimestamp() {
  return timestamp;
 }

 public void setTimestamp(long timestamp) {
  this.timestamp = timestamp;
 }
}
