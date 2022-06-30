package com.yang.cae.function.login.register;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.mengpeng.mphelper.ToastUtils;
import com.mylhyl.circledialog.CircleDialog;
import com.yang.cae.R;
import com.yang.cae.bean.result.Result;
import com.yang.cae.configure.requestAPI.RequestAPI;
import com.yang.cae.function.login.register.pojo.NameMajor;
import com.yang.cae.function.login.register.pojo.NameProfession;
import com.yang.cae.function.login.register.pojo.UserRegisterDTO;
import com.yang.cae.util.authCodeUtils.authCodeUtils;
import com.yang.cae.util.dataUtil.GetAddressDataUtil;
import com.yang.cae.util.jsonUtil.JsonUtil;
import com.yang.cae.util.okHttpUtil.RetrofitUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private String majorId=null;
    private String professionId=null;
    EditText reg_user_name;
    EditText reg_password;
    EditText reg_password2;
    EditText reg_phone_number;
    EditText reg_email;
    EditText reg_auth_code;

    TextView reg_major;
    TextView reg_profession;
    TextView reg_address;

    ImageView reg_img_code;

    Button register_btn;
    private ImageView back;
    private GetAddressDataUtil getAddressDataUtil;
    RequestAPI requestApi;
    private String getCode = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
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
            //选择专业
            case R.id.reg_major:
                Call<ResponseBody> call = requestApi.getMajor();
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            try {
                              String resp = new String(response.body().bytes());
                                Result result = JsonUtil.respBodyParse(resp);
                                List<NameMajor> majorList = result.getData().toJavaList(NameMajor.class);
                                new CircleDialog.Builder()
                                        .setMaxHeight(0.7f)
                                        .configDialog(params -> params.backgroundColorPress = Color.CYAN)
                                        .setTitle("专业选择")
                                        .setTitleColor(R.color.main_color)
                                        .configItems(params -> params.dividerHeight = 1)
                                        .setItems(majorList
                                                , (view, position) -> {
                                                    reg_major.setText(majorList.get(position).getMajorName());
                                                    majorId = majorList.get(position).getId();
                                                    return true;
                                                })
                                        .setNegative("取消", null)
                                        .show(getSupportFragmentManager());
                            }catch (IOException e) {
                                e.printStackTrace();
                            }
                        }else {
                            ToastUtils.onErrorShowToast("请求失败！");
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) { }});
                break;
            //选择职业
            case R.id.reg_profession:
                Call<ResponseBody> call1 = requestApi.getProfession();
                call1.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            try {
                                String resp = new String(response.body().bytes());
                                Result result = JsonUtil.respBodyParse(resp);
                                List<NameProfession> professionList = result.getData().toJavaList(NameProfession.class);
                                new CircleDialog.Builder()
                                        .setMaxHeight(0.7f)
                                        .configDialog(params -> params.backgroundColorPress = Color.CYAN)
                                        .setTitle("职业选择")
                                        .setTitleColor(R.color.main_color)
                                        .configItems(params -> params.dividerHeight = 1)
                                        .setItems(professionList
                                                , (view, position) -> {
                                                    reg_profession.setText(professionList.get(position).getProfessionName());
                                                    professionId = professionList.get(position).getId();
                                                    return true;
                                                })
                                        .setNegative("取消", null)
                                        .show(getSupportFragmentManager());
                            }catch (IOException e) {
                                e.printStackTrace();
                            }

                        }else {
                            ToastUtils.onErrorShowToast("请求失败！");
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) { }});
                break;
            //选择地址
            case R.id.reg_address:
                getAddressDataUtil.showPickerView(reg_address);
                break;
            //刷新验证码
            case R.id.reg_img_code:
                reg_img_code.setImageBitmap(authCodeUtils.getInstance().getBitmap());
                getCode = authCodeUtils.getInstance().getCode();
                break;
            //选择职业
            case R.id.register_btn:
                UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
                String userName = reg_user_name.getText().toString();
                String password = reg_password.getText().toString();
                String password2 = reg_password2.getText().toString();
                String phoneNumber = reg_phone_number.getText().toString();
                String email = reg_email.getText().toString();
                String major = reg_major.getText().toString();
                String profession = reg_profession.getText().toString();
                String address = reg_address.getText().toString();


                String v_code = reg_auth_code.getText().toString().trim();
                if (userName.isEmpty() || password.isEmpty() || password2.isEmpty() || phoneNumber.isEmpty()
                    || email.isEmpty() || major.isEmpty() || profession.isEmpty() || address.isEmpty()){
                    ToastUtils.onWarnShowToast("请完善信息!");
                }else if (!password.equals(password2)){
                    ToastUtils.onWarnShowToast("密码不一致!");
                } else if (v_code == null || v_code.equals("")) {
                    ToastUtils.onWarnShowToast("验证码为空");
                } else if (!v_code.equalsIgnoreCase(getCode)) {
                    ToastUtils.onErrorShowToast("验证码错误");
                    reg_img_code.setImageBitmap(authCodeUtils.getInstance().getBitmap());
                    getCode = authCodeUtils.getInstance().getCode();
                } else {
                    userRegisterDTO.setNickName(userName);
                    userRegisterDTO.setPassword(password);
                    userRegisterDTO.setPhoneNumber(phoneNumber);
                    userRegisterDTO.setEmail(email);
                    userRegisterDTO.setMajor(major);
                    userRegisterDTO.setMajorId(majorId);
                    userRegisterDTO.setProfession(profession);
                    userRegisterDTO.setProfessionId(professionId);
                    userRegisterDTO.setAddress(address);
                    Call<ResponseBody> register = requestApi.register(userRegisterDTO);
                    register.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()){
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
                }
                break;
        }
    }

    private void initView(){
        getAddressDataUtil = new GetAddressDataUtil(this);

        reg_user_name=(EditText)findViewById(R.id.reg_user_name);
        reg_password=(EditText)findViewById(R.id.reg_password);
        reg_password2=(EditText)findViewById(R.id.reg_password2);
        reg_phone_number=(EditText)findViewById(R.id.reg_phone_number);
        reg_email=(EditText)findViewById(R.id.reg_email);
        reg_auth_code=(EditText)findViewById(R.id.reg_auth_code);

        reg_major=(TextView)findViewById(R.id.reg_major);
        reg_profession=(TextView)findViewById(R.id.reg_profession);
        reg_address=(TextView)findViewById(R.id.reg_address);
        reg_img_code=(ImageView)findViewById(R.id.reg_img_code);
        reg_img_code.setImageBitmap(authCodeUtils.getInstance().getBitmap());
        getCode = authCodeUtils.getInstance().getCode(); // 获取显示的验证码
        register_btn=(Button) findViewById(R.id.register_btn);
        back =(ImageView)findViewById(R.id.back);
    }
    private void listener(){
        reg_major.setOnClickListener(this);
        reg_profession.setOnClickListener(this);
        reg_address.setOnClickListener(this);
        reg_img_code.setOnClickListener(this);
        register_btn.setOnClickListener(this);
        back.setOnClickListener(this);
    }
}