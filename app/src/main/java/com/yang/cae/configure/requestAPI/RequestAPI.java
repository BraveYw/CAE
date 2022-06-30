package com.yang.cae.configure.requestAPI;

import com.yang.cae.function.setting.pojo.UserBasicInformation;
import com.yang.cae.function.login.reset.UserResetDTO;
import com.yang.cae.DataDTO;
import com.yang.cae.function.login.register.pojo.UserRegisterDTO;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.GET;

public interface RequestAPI {

    /**登录**/
    @GET("userLogin/login")  //登录
    Call<ResponseBody> login(@Query("userName")String userName, @Query("password") String password);
    @GET("userLogin/autoLogin") //自动登录
    Call<ResponseBody> autoLogin();
    @POST("userLogin/reset") // 密码重置
    Call<ResponseBody> reset(@Body UserResetDTO userDTO);
    @POST("userLogin/register")//注册
    Call<ResponseBody> register(@Body UserRegisterDTO registerDTO);
    @GET("userLogin/getAuthCode")//获取邮箱验证码
    Call<ResponseBody> getAuthCode(@Query("email")String email);
    @GET("userLogin/getMajor")//获取邮箱验证码
    Call<ResponseBody> getMajor();
    @GET("userLogin/getProfession")//获取邮箱验证码
    Call<ResponseBody> getProfession();

    /**首页**/
    @GET("home/getHomeData")  //获取首页数据
    Call<ResponseBody>getHomeData();

    /**搜索**/
    @GET("search/getSearch")  //搜索结果
    Call<ResponseBody> getSearch(@Query("queryData")String queryData);
    @GET("search/getSearchRecord") //搜索记录
    Call<ResponseBody> getSearchRecord();
    @DELETE("search/deleteSearchRecord")//删除记录
    Call<ResponseBody> deleteSearchRecord(@Query("recordId")String recordId);

    /**推荐**/
    @POST("recommend/getRecommend") //获取推荐
    Call<ResponseBody> getRecommend();
    @GET("recommend/getAdvertise") //获取广告
    Call<ResponseBody> getAdvertise();

    /**收藏**/
    @GET("collect/getCollect") //获取收藏
    Call<ResponseBody> getCollect();
    @POST("collect/addCollect") //添加收藏
    Call<ResponseBody> addCollect(@Body DataDTO dataDTO);
    @DELETE("collect/deleteCollect") //取消收藏
    Call<ResponseBody> deleteCollect(@Query("messageId")String messageId);

    /**获取详细信息**/
    @GET("message/getMessageCertificate") //证书信息
    Call<ResponseBody> getMessageCertificate(@Query("messageId")String messageId);
    @GET("message/getMessageExam") //考试信息
    Call<ResponseBody> getMessageExam(@Query("messageId")String messageId);
    @GET("message/getMessageWork") //办事信息
    Call<ResponseBody> getMessageWork(@Query("messageId")String messageId);

    @GET("message/getUserMessage") //用户基本信息
    Call<ResponseBody> getUserMessage();
    @POST("message/setUserMessage") //办事信息
    Call<ResponseBody> setUserMessage(@Body UserBasicInformation userBasicInformation);
    @GET("message/getNickName") //用户昵称
    Call<ResponseBody> getNickName();

}
