package com.yang.cae.function.login.reset;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.mengpeng.mphelper.ToastUtils;
import com.yang.cae.R;
import com.yang.cae.bean.result.Result;
import com.yang.cae.configure.requestAPI.RequestAPI;
import com.yang.cae.util.jsonUtil.JsonUtil;
import com.yang.cae.util.okHttpUtil.RetrofitUtil;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText reset_email;
    private EditText reset_password;
    private EditText reset_password2;
    private EditText reset_auth_code;
    private Button reset_code;
    private Button reset_btn;
    private Runnable runnable;
    private Handler handler;
    private ImageView back;
    RequestAPI requestApi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);
        //ToastUtils 初始化
        ToastUtils.getInstance().initToast(this);
        requestApi = RetrofitUtil.getRetrofit()
                .create(RequestAPI.class);
        initView();
        listener();
    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //返回
            case R.id.back:
                finish();
                break;
            //获取验证码
            case R.id.reset_code:
                String email1 = reset_email.getText().toString();
                if (email1.isEmpty()){
                    ToastUtils.onWarnShowToast("请输入邮箱地址！");
                }else {
                    Call<ResponseBody> authCode = requestApi.getAuthCode(email1);
                    authCode.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                try {
                                    String resp = new String(response.body().bytes());
                                    Result result = JsonUtil.respBodyParse(resp);
                                    ToastUtils.onInfoShowToast(result.getMessage());
                                }catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }else {
                                ToastUtils.onWarnShowToast("请求失败！");
                            }
                        }
                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {}});
                    CountDownTimer timer = new CountDownTimer(1000 * 60, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            reset_code.setEnabled(false);
                            reset_code.setText("发送中("+millisUntilFinished/1000+"s)");
                        }
                        @Override
                        public void onFinish() {
                            reset_code.setEnabled(true);
                            reset_code.setText("重新获取");
                        }
                    };
                    timer.start();
                }

                break;
            //确定
            case R.id.reset_btn:
                UserResetDTO userResetDTO = new UserResetDTO();
                String email = reset_email.getText().toString();
                String password = reset_password.getText().toString();
                String password2 = reset_password2.getText().toString();
                String authCode = reset_auth_code.getText().toString();
                if (email.isEmpty() || password.isEmpty()
                     || password2.isEmpty() || authCode.isEmpty()){
                    ToastUtils.onWarnShowToast("请完善信息！");
                }else if (!password.equals(password2)) {
                    ToastUtils.onWarnShowToast("密码不一致！");
                }else {
                    userResetDTO.setAuthCode(authCode);
                    userResetDTO.setPassword(password);
                    userResetDTO.setUserName(email);
                    Call<ResponseBody> reset = requestApi.reset(userResetDTO);
                    reset.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                try {
                                    String resp = new String(response.body().bytes());
                                    Result result = JsonUtil.respBodyParse(resp);
                                    ToastUtils.onInfoShowToast(result.getMessage());
                                }catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }else {
                                ToastUtils.onWarnShowToast("请求失败！");
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) { }});
                }
                break;
        }
    }

    private void initView(){
        reset_email = (EditText)findViewById(R.id.reset_email);
        reset_password = (EditText)findViewById(R.id.reset_password);
        reset_password2 = (EditText)findViewById(R.id.reset_password2);
        reset_auth_code = (EditText)findViewById(R.id.reset_auth_code);
        reset_code = (Button) findViewById(R.id.reset_code);
        reset_btn = (Button) findViewById(R.id.reset_btn);
        back =(ImageView)findViewById(R.id.back);
    }
    private void listener(){
        reset_code.setOnClickListener(this);
        reset_btn.setOnClickListener(this);
        back.setOnClickListener(this);
    }
}