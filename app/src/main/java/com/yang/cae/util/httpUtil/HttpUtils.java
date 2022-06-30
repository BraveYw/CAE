package com.yang.cae.util.httpUtil;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;


public class HttpUtils {

    final static private String URL = "http://47o654i322.qicp.vip/cae/";

    final static private String URL1 = "http://localhost:8081/";
    public final static String GET = "GET";

    public final static String POST = "POST";

    public final static String DELETE = "DELETE";

    public HttpUtils() {
    }

    public static String getJsonContent(String address, Map params, String requestMethod) {
        String param = ParamsUtil.getTypeUtil(params);
        try{
            java.net.URL url = new URL(URL+address+param);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(3000);
            connection.setRequestMethod(requestMethod);
            connection.setDoInput(true);
            int code = connection.getResponseCode();
            if(code == 200){
                InputStream inputStream = connection.getInputStream();
                System.out.println(inputStream);
                String s = changeInputStream(connection.getInputStream());
                return s;

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String changeInputStream(InputStream inputStream) {
        String jsonString = "";
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int len = 0;
        byte[] data = new byte[1024];
        try{
            while ((len = inputStream.read(data)) != -1) {
                outputStream.write(data, 0, len);
            }
            jsonString = new String(outputStream.toByteArray());
            return jsonString;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}


