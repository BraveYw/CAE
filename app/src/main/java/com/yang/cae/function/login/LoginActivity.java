package com.yang.cae.function.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mengpeng.mphelper.ToastUtils;
import com.yang.cae.bean.result.Result;
import com.yang.cae.MainActivity;
import com.yang.cae.R;
import com.yang.cae.configure.requestAPI.RequestAPI;
import com.yang.cae.function.login.register.RegisterActivity;
import com.yang.cae.function.login.reset.ResetActivity;
import com.yang.cae.util.authCodeUtils.authCodeUtils;
import com.yang.cae.util.jsonUtil.JsonUtil;
import com.yang.cae.util.okHttpUtil.RetrofitUtil;


import java.io.IOException;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private EditText et_login_user_name;
    private EditText et_login_password;
    private Button login_btn;
    private TextView tv_login_reset;
    private TextView tv_login_register;
    private CheckBox login_auto;
    private CheckBox login_remember;
    private EditText login_auth_code;
    private ImageView login_img_code;
    private String getCode = null;
    private Intent intent;
    private ImageView back;

    RequestAPI requestApi;

    boolean autoLogin;
    boolean remember;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //ToastUtils 初始化
        ToastUtils.getInstance().initToast(this);
        //新建文档 获取记忆
        sharedPreferences=getSharedPreferences("loginData", MODE_PRIVATE);
        editor=sharedPreferences.edit();
        //向新建的文档总读取数据
        autoLogin=sharedPreferences.getBoolean("login_auto", false);
        remember=sharedPreferences.getBoolean("login_remember", false);
        initView(); //初始化
        setListener();//设置监听
        requestApi = RetrofitUtil.getRetrofit()
                .create(RequestAPI.class);
        if(autoLogin){
            autoLogin();//自动登录
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //返回
            case R.id.back:
                finish();
                break;
            //刷新验证码
            case R.id.login_img_code:
                login_img_code.setImageBitmap(authCodeUtils.getInstance().getBitmap());
                getCode = authCodeUtils.getInstance().getCode();
                break;
            //登录
            case R.id.login_btn:
                //获取EditText的值
                final String userName = et_login_user_name.getText().toString();
                final String password = et_login_password.getText().toString();
                String v_code = login_auth_code.getText().toString().trim();
                if (userName.isEmpty()){
                    ToastUtils.onInfoShowToast("邮箱为空！");
                }
                else if (password.isEmpty()){
                    ToastUtils.onInfoShowToast("密码为空！");
                }
                else if (v_code == null || v_code.isEmpty()){
                    ToastUtils.onInfoShowToast("验证为空！");
                }
                else if (!v_code.equalsIgnoreCase(getCode)) {
                    login_img_code.setImageBitmap(authCodeUtils.getInstance().getBitmap());
                    getCode = authCodeUtils.getInstance().getCode();
                    ToastUtils.onErrorShowToast("验证码错误");
                } else {
                    editor.putString("userName", userName).commit();
                    Call<ResponseBody> call = requestApi.login(userName,password);
               call.enqueue(new Callback<ResponseBody>() {
                   @Override
                   public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                       int code = response.code();
                       Log.w("code---->", String.valueOf(code));
                       if(code >= 400){
                           ToastUtils.onErrorShowToast("系统维护中");
                           return;
                       }
                       if (response.isSuccessful()) {
                           Result result = null;
                           try {
                               String body = new String(response.body().bytes());
                               Log.e("响应body---->", body);
                               result = JsonUtil.respBodyParse(body);
                           } catch (IOException e) {
                               e.printStackTrace();
                           }
                           if (result == null) {
                               ToastUtils.onErrorShowToast("系统繁忙，稍后重试！");
                           }
                           else if (result.isSuccess()) {
                               if (login_remember.isChecked()) {
                                   editor.putString("password", password).commit();  //保存密码
                               } else {
                                   editor.putString("password", "").commit();  //保存密码
                               }
                               editor.putString("token", result.getMessage()).commit(); // 保存token
                               Log.e("token:", result.getMessage());
                               ToastUtils.onSuccessShowToast("登录成功！");
                               login();
                           } else {
                               editor.putString("password", "");
                               editor.putBoolean("login_auto", false);
                               editor.putBoolean("login_remember", false);
                               editor.commit();
                               ToastUtils.onErrorShowToast(result.getMessage());
                               login_auto.setChecked(false);
                               login_remember.setChecked(false);
                               et_login_password.setText("");
                           }
                       } else {
                           ToastUtils.onErrorShowToast("请求失败！");
                       }

                   }
                   @Override
                   public void onFailure(Call<ResponseBody> call, Throwable t) {

                   }
               });

                }
                break;
            //自动登录
            case R.id.login_auto:
                if (login_auto.isChecked()){
                    login_remember.setChecked(true);
                    editor.putBoolean("login_auto", true);
                    editor.putBoolean("login_remember", true);
                }else {
                    editor.putBoolean("login_auto", false);
                }
                editor.commit();
                break;
            //记住密码
            case R.id.login_remember:
                if (!login_remember.isChecked()){
                    login_auto.setChecked(false);
                    editor.putBoolean("login_auto", false);
                    editor.putBoolean("login_remember", false);
                    editor.putString("password", "");
                }else {
                    editor.putBoolean("login_remember", true);
                }
                editor.commit();
                break;
            //注册
            case R.id.login_register:
                intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            //重置密码
            case R.id.login_reset:
                intent = new Intent(LoginActivity.this, ResetActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void initView(){
        et_login_user_name = (EditText)findViewById(R.id.login_user_name);
        et_login_user_name.setText(sharedPreferences.getString("userName",""));
        et_login_password = (EditText)findViewById(R.id.login_password);
        et_login_password.setText(sharedPreferences.getString("password",""));

        back =(ImageView)findViewById(R.id.back);
        login_auth_code = (EditText)findViewById(R.id.login_auth_code);
        login_img_code = (ImageView)findViewById(R.id.login_img_code);
        login_img_code.setImageBitmap(authCodeUtils.getInstance().getBitmap());
        getCode = authCodeUtils.getInstance().getCode(); // 获取显示的验证码

        login_auto = (CheckBox)findViewById(R.id.login_auto);
        login_auto.setChecked(autoLogin);//记忆设置
        login_remember = (CheckBox)findViewById(R.id.login_remember);
        login_remember.setChecked(remember);
        login_btn = (Button)findViewById(R.id.login_btn);

        tv_login_reset = (TextView)findViewById(R.id.login_reset);
        tv_login_register = (TextView)findViewById(R.id.login_register);
    }

    private void setListener(){
        login_img_code.setOnClickListener(this);
        login_btn.setOnClickListener(this);
        login_auto.setOnClickListener(this);
        login_remember.setOnClickListener(this);
        tv_login_register.setOnClickListener(this);
        tv_login_reset.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    protected void login(){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        LoginActivity.this.finish();
    }

    public void autoLogin() {
        Call<ResponseBody> call = requestApi.autoLogin();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response<ResponseBody> response = call.execute();
                    int code = response.code();
                    Log.w("code---->", String.valueOf(code));
                    if (call.isExecuted()) {
                        String body = new String(response.body().bytes());
                        Log.e("响应body---->", body);
                        Result result = JsonUtil.respBodyParse(body);
                        if (result == null){
                            ToastUtils.onErrorShowToast("系统繁忙，稍后重试！");
                        }
                        else if (result.isSuccess()){
                            Log.e("token:",result.getMessage());
                            ToastUtils.onSuccessShowToast("自动登录成功！");
                            login();
                        }else {
                            ToastUtils.onErrorShowToast("身份验证过期，请重新登录！");
                            et_login_password.setText("");
                        }
                    } else {
                        Log.e("---->", "请求失败");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}