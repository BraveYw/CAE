package com.yang.cae.util.jsonUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yang.cae.bean.result.Result;

public class JsonUtil {
    //响应json解析
    public static Result respBodyParse(String json){
        //json直接解析为result，但是data可能不全
        //Result result = jsonObject.toJavaObject(Result.class);
       //手动解析
        Result result = new Result();
        JSONObject jsonObject = JSON.parseObject(json);
        Boolean success = jsonObject.getBoolean("success");
        result.setSuccess(success);
        Integer code = jsonObject.getInteger("code");
        result.setCode(code);
        String message = jsonObject.getString("message");
        result.setMessage(message);
        if (code == 200){
            JSONArray data = jsonObject.getJSONArray("data");
            result.setData(data);
        }
        Long timestamp = jsonObject.getLong("timestamp");
        result.setTimestamp(timestamp);
        return result;
    }
}
