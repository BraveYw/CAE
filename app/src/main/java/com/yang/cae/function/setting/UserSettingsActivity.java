package com.yang.cae.function.setting;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mengpeng.mphelper.ToastUtils;
import com.mylhyl.circledialog.CircleDialog;
import com.yang.cae.R;
import com.yang.cae.bean.result.Result;
import com.yang.cae.configure.requestAPI.RequestAPI;
import com.yang.cae.function.login.register.pojo.NameMajor;
import com.yang.cae.function.login.register.pojo.NameProfession;
import com.yang.cae.function.login.register.pojo.UserRegisterDTO;
import com.yang.cae.function.setting.pojo.UserBasicInformation;
import com.yang.cae.show.entity.MessageWork;
import com.yang.cae.util.authCodeUtils.authCodeUtils;
import com.yang.cae.util.dataUtil.GetAddressDataUtil;
import com.yang.cae.util.jsonUtil.JsonUtil;
import com.yang.cae.util.okHttpUtil.RetrofitUtil;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserSettingsActivity extends AppCompatActivity implements View.OnClickListener {

    private String majorId=null;
    private String id=null;
    private String createTime;
    private String professionId=null;
    EditText user_name;
    EditText phone_number;
    EditText email;
    EditText auth_code;

    TextView major;
    TextView profession;
    TextView address;

    ImageView img_code;

    Button save;
    private ImageView back;
    private GetAddressDataUtil getAddressDataUtil;
    RequestAPI requestApi;
    private String getCode = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_settings_activity);
        //ToastUtils 初始化
        ToastUtils.getInstance().initToast(this);
        requestApi = RetrofitUtil.getRetrofit()
                .create(RequestAPI.class);
        initView();
        setListener();

        Call<ResponseBody> message = requestApi.getUserMessage();
        message.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        String resp = new String(response.body().bytes());
                        JSONObject jsonObject = JSON.parseObject(resp);
                        UserBasicInformation information = jsonObject.getObject("data", UserBasicInformation.class);
                        if (information != null) {
                            id = information.getId();
                            majorId = information.getMajorId();
                            professionId = information.getProfessionId();
                            createTime = information.getCreateTime();
                            user_name.setText(information.getNickName());
                            phone_number.setText(information.getPhoneNumber());
                            email.setText(information.getEmail());
                            major.setText(information.getMajor());
                            profession.setText(information.getProfession());
                            address.setText(information.getAddress());
                        }else {
                            ToastUtils.onErrorShowToast("数据为空");
                        }
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //返回
            case R.id.back:
                finish();
                break;
            //选择专业
            case R.id.major:
                Call<ResponseBody> call = requestApi.getMajor();
                call.enqueue(new Callback<ResponseBody>() {
                    @SuppressLint("ResourceAsColor")
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
                                        .setTitleColor(R.color.red)
                                        .configItems(params -> params.dividerHeight = 1)
                                        .setItems(majorList
                                                , (view, position) -> {
                                                    major.setText(majorList.get(position).getMajorName());
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
            case R.id.profession:
                Call<ResponseBody> call1 = requestApi.getProfession();
                call1.enqueue(new Callback<ResponseBody>() {
                    @SuppressLint("ResourceAsColor")
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
                                                    profession.setText(professionList.get(position).getProfessionName());
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
            case R.id.address:
                getAddressDataUtil.showPickerView(address);
                break;
            //刷新验证码
            case R.id.img_code:
                img_code.setImageBitmap(authCodeUtils.getInstance().getBitmap());
                getCode = authCodeUtils.getInstance().getCode();
                break;
            //选择职业
            case R.id.save:
                UserBasicInformation userBasicInformation = new UserBasicInformation();
                String userName = user_name.getText().toString();
                String phoneNumber = phone_number.getText().toString();
                String email1 = email.getText().toString();
                String major1 = major.getText().toString();
                String profession1 = profession.getText().toString();
                String address1 = address.getText().toString();


                String v_code = auth_code.getText().toString().trim();
                if (userName.isEmpty() ||  phoneNumber.isEmpty() || email1.isEmpty()
                        || major1.isEmpty() || profession1.isEmpty() || address1.isEmpty()){
                    ToastUtils.onWarnShowToast("请完善信息!");
                } else if (v_code == null || v_code.equals("")) {
                    ToastUtils.onWarnShowToast("验证码为空");
                } else if (!v_code.equalsIgnoreCase(getCode)) {
                    ToastUtils.onErrorShowToast("验证码错误");
                    img_code.setImageBitmap(authCodeUtils.getInstance().getBitmap());
                    getCode = authCodeUtils.getInstance().getCode();
                } else {
                    userBasicInformation.setId(id);
                    userBasicInformation.setNickName(userName);
                    userBasicInformation.setCreateTime(createTime);
                    userBasicInformation.setPhoneNumber(phoneNumber);
                    userBasicInformation.setEmail(email1);
                    userBasicInformation.setMajor(major1);
                    userBasicInformation.setMajorId(majorId);
                    userBasicInformation.setProfession(profession1);
                    userBasicInformation.setProfessionId(professionId);
                    userBasicInformation.setAddress(address1);
                    Call<ResponseBody> userMessage = requestApi.setUserMessage(userBasicInformation);
                    userMessage.enqueue(new Callback<ResponseBody>() {
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
        back =(ImageView)findViewById(R.id.back);
        getAddressDataUtil = new GetAddressDataUtil(this);

        user_name=(EditText)findViewById(R.id.user_name);
        phone_number=(EditText)findViewById(R.id.phone_number);
        email=(EditText)findViewById(R.id.email);
        auth_code=(EditText)findViewById(R.id.auth_code);

        major=(TextView)findViewById(R.id.major);
        profession=(TextView)findViewById(R.id.profession);
        address=(TextView)findViewById(R.id.address);
        img_code=(ImageView)findViewById(R.id.img_code);
        img_code.setImageBitmap(authCodeUtils.getInstance().getBitmap());
        getCode = authCodeUtils.getInstance().getCode(); // 获取显示的验证码
        save=(Button) findViewById(R.id.save);
    }
    private void setListener(){
        back.setOnClickListener(this);
        major.setOnClickListener(this);
        profession.setOnClickListener(this);
        address.setOnClickListener(this);
        img_code.setOnClickListener(this);
        save.setOnClickListener(this);
    }
}