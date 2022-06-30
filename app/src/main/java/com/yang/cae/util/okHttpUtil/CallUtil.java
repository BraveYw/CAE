package com.yang.cae.util.okHttpUtil;

import android.util.Log;

import com.yang.cae.bean.result.Result;
import com.yang.cae.util.jsonUtil.JsonUtil;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallUtil {
    private static Result result;
    //异步请求
    public static Result getEnqueue(Call<ResponseBody> call){
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                int code = response.code();
                Log.w("code---->", String.valueOf(code));
                if ( response.isSuccessful()) {
                    Log.w("code---->", String.valueOf(code));
                    String resp = null;
                    try {
                        resp = new String(response.body().bytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e("---->","响应异常" );
                    }
                    Log.e("响应body---->", resp);
                     result = JsonUtil.respBodyParse(resp);
                }else {
                    Log.e("---->","请求失败" );
                    result = null;
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("onFailure请求失败！");
            }
        });
        return result;
    }
    //同步请求
    public static Result getExecute(Call<ResponseBody> call){
        Runnable retrofit = new Runnable() {
            @Override
            public void run() {
                try {
                    Response<ResponseBody> response = call.execute();
                    int code = response.code();
                    Log.w("code---->", String.valueOf(code));
                    if (call.isExecuted()) {
                        String body = new String(response.body().bytes());
                        Log.e("响应body---->", body);
                        result = JsonUtil.respBodyParse(body);
                    } else {
                        Log.e("---->", "请求失败");
                        result = null;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        //同步请求数据，不能放在主线程，所以要放在比别的线程中
        Thread myThread = new Thread(retrofit);
        myThread.start();
        return result;
    }

}
