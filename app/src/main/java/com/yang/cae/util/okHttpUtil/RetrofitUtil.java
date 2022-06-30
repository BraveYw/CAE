package com.yang.cae.util.okHttpUtil;

import com.yang.cae.configure.requestAPI.RequestAPI;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.yang.cae.util.okHttpUtil.RequestAddress.URL;


public class RetrofitUtil {

    private static Retrofit retrofit;
    private static RequestAPI requestApi;

    public static Retrofit getRetrofit(){
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .client(OkHttpUtil.getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }
    //获取请求接口
    public static RequestAPI getRequestApi(){
        requestApi = retrofit.create(RequestAPI.class);
        return requestApi;
    }
}
