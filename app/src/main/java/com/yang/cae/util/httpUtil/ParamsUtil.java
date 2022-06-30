package com.yang.cae.util.httpUtil;


import java.util.Iterator;
import java.util.Map;

public class ParamsUtil {
    public static String getTypeUtil(Map params){
        StringBuilder stringBuilder = new StringBuilder();
        if (params != null && params.size() > 0) {
            //将URL的参数拼接完成
            stringBuilder.append("?");
            Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> next = iterator.next();
                stringBuilder.append(next.getKey());
                stringBuilder.append("=");
                stringBuilder.append(next.getValue());
                if (iterator.hasNext()) {
                    stringBuilder.append("&");
                }
            }
        }
        return stringBuilder.toString();
    }
}
