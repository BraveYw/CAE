package com.yang.cae.configure.interceptor;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;


import com.yang.cae.configure.ContextApplication;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * token 拦截器
 */
public class TokenInterceptor implements Interceptor{
    //用于添加的请求头
    private String token = "token is null!";
    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        //从SharePreferences中获取token

        SharedPreferences sharedPreferences = ContextApplication.getContext().getSharedPreferences("loginData", 0);
        token = sharedPreferences.getString("token", "");

        Request request = chain.request()
                .newBuilder()
                .addHeader("token", token)
                .build();
        Response response = chain.proceed(request);
        //Log.e("返回数据：",response.body().string());
        return response;

    }
}
