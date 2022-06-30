package com.yang.cae.show;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Binder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mengpeng.mphelper.ToastUtils;
import com.yang.cae.R;
import com.yang.cae.DataDTO;
import com.yang.cae.bean.result.Result;
import com.yang.cae.configure.requestAPI.RequestAPI;
import com.yang.cae.show.entity.MessageCertificate;
import com.yang.cae.show.entity.MessageExam;
import com.yang.cae.show.entity.MessageWork;
import com.yang.cae.util.jsonUtil.JsonUtil;
import com.yang.cae.util.okHttpUtil.RetrofitUtil;
import com.yang.cae.util.xbasebrowerUtil.XBaseBrowserUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowActivity extends AppCompatActivity implements View.OnClickListener {
    private List<Map<String,Object>> messageList;
    DataDTO dataDTO;
    Button access;
    Button collect;
    RequestAPI requestApi;
    TextView showTitle;
    String addressUrl = null;
    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        ToastUtils.getInstance().initToast(this);
        dataDTO =(DataDTO) getIntent().getSerializableExtra("dataDTO");
        requestApi = RetrofitUtil.getRetrofit()
                .create(RequestAPI.class);
        initView();
        String[] attributes = {"信息ID"
                ,"考试时长"
                ,"报名开始时间"
                ,"报名截止时间"
                ,"报名方式"
                ,"报名地址"
                ,"报名需要材料"
                ,"考试开始时间"
                ,"考试结束时间"
                ,"考试地点"
                ,"考试材料"
                ,"备考信息"
                ,"性价比"
                ,"备注"};
        messageList = new ArrayList<>();

        if ("certificate".equals(dataDTO.getFlag())){
            Call<ResponseBody> call = requestApi.getMessageCertificate(dataDTO.getMessageId());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        if (response.isSuccessful()){
                            String resp = new String(response.body().bytes());
                            JSONObject jsonObject = JSON.parseObject(resp);
                            MessageCertificate certificate = jsonObject.getObject("data", MessageCertificate.class);
                            showTitle.setText(certificate.getCertificateName());
                            if (certificate != null){
                                String[] data = {certificate.getId()
                                        ,certificate.getExamTime()
                                        ,certificate.getStartRegistrationTime()
                                        ,certificate.getEndRegistrationTime()
                                        ,certificate.getRegistrationWay()
                                        ,certificate.getRegistrationAddress()
                                        ,certificate.getRegistrationPrepareMaterial()
                                        ,certificate.getStartExamTime()
                                        ,certificate.getEndExamTime()
                                        ,certificate.getExamAddress()
                                        ,certificate.getPrepareMaterial()
                                        ,certificate.getExamPreparationMessage()
                                        ,certificate.getCostEffective()
                                        ,certificate.getRemark()};
                                for (int i = 0; i < attributes.length ; i++) {
                                    Map<String,Object> item = new HashMap<>();
                                    item.put("show",attributes[i]);
                                    item.put("data",data[i]);
                                    messageList.add(item);
                                }
                                renderListView(messageList,certificate.getRegistrationAddress());
                            }else {
                                ToastUtils.onErrorShowToast("data is null");
                            }
                        }else {
                            ToastUtils.onErrorShowToast("请求失败！");

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                }
            });
        }
        else if("exam".equals(dataDTO.getFlag())){
            Call<ResponseBody> call = requestApi.getMessageExam(dataDTO.getMessageId());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        if (response.isSuccessful()){
                            String resp = new String(response.body().bytes());
                            JSONObject jsonObject = JSON.parseObject(resp);
                            MessageExam exam = jsonObject.getObject("data", MessageExam.class);
                            showTitle.setText(exam.getExamName());
                            if (exam != null){
                                String[] data = {exam.getId()
                                        ,exam.getExamTime()
                                        ,exam.getStartRegistrationTime()
                                        ,exam.getEndRegistrationTime()
                                        ,exam.getRegistrationWay()
                                        ,exam.getRegistrationAddress()
                                        ,exam.getRegistrationPrepareMaterial()
                                        ,exam.getStartExamTime()
                                        ,exam.getEndExamTime()
                                        ,exam.getExamAddress()
                                        ,exam.getPrepareMaterial()
                                        ,exam.getExamPreparationMessage()
                                        ,exam.getCostEffective()
                                        ,exam.getRemark()};
                                for (int i = 0; i < attributes.length ; i++) {
                                    Map<String,Object> item = new HashMap<>();
                                    item.put("show",attributes[i]);
                                    item.put("data",data[i]);
                                    messageList.add(item);
                                }
                                renderListView(messageList, exam.getRegistrationAddress());
                            }else {
                                ToastUtils.onErrorShowToast("data is null");
                            }
                        }else {
                            ToastUtils.onErrorShowToast("请求失败！");

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                }
            });
        }else if ("work".equals(dataDTO.getFlag())){
            Call<ResponseBody> call = requestApi.getMessageWork(dataDTO.getMessageId());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        if (response.isSuccessful()){
                            String resp = new String(response.body().bytes());
                            JSONObject jsonObject = JSON.parseObject(resp);
                            MessageWork work = jsonObject.getObject("data", MessageWork.class);
                            showTitle.setText(work.getWorkName());
                            if (work != null){
                                String[] attributes = {"信息ID"
                                        ,"部门"
                                        ,"流程"
                                        ,"所需材料"
                                        ,"信息地址"
                                        ,"备注"};
                                String[] data = {work.getId()
                                        ,work.getDepartment()
                                        ,work.getProcess()
                                        ,work.getMaterialsNeeded()
                                        ,work.getAddress()
                                        ,work.getRemark()};
                                for (int i = 0; i < attributes.length ; i++) {
                                    Map<String,Object> item = new HashMap<>();
                                    item.put("show",attributes[i]);
                                    item.put("data",data[i]);
                                    messageList.add(item);
                                }
                                renderListView(messageList,work.getAddress());
                            }else {
                                ToastUtils.onErrorShowToast("data is null");
                            }
                        }else {
                            ToastUtils.onErrorShowToast("请求失败！");

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                }
            });

        }else {
            ToastUtils.onErrorShowToast("数据获取失败！");
        }



    }

    public void initView(){
        back =(ImageView)findViewById(R.id.back);
        back.setOnClickListener(this);
        access = (Button)findViewById(R.id.btn_access);
        access.setOnClickListener(this);
        showTitle = (TextView)findViewById(R.id.show_title);
        collect = (Button)findViewById(R.id.btn_collect);
        collect.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.btn_access:
                XBaseBrowserUtil.startActivity(ShowActivity.this,addressUrl);
                break;
            case R.id.btn_collect:
                Call<ResponseBody> addCollect = requestApi.addCollect(dataDTO);
                addCollect.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            String resp = null;
                            try {
                                resp = new String(response.body().bytes());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Result result = JsonUtil.respBodyParse(resp);
                            ToastUtils.onSuccessShowToast(result.getMessage());
                            collect.setText("已收藏");
                        }else {
                            ToastUtils.onErrorShowToast("收藏失败！");
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {}});
                break;
        }
    }
    private void renderListView(List<Map<String,Object>> messageList, String url){
        this.addressUrl = url;
        //startManagingCursor(cursor);
        String[] from=new String[]{"show","data"};
        int to[]=new int[]{R.id.show,R.id.data};
        SimpleAdapter adapter=new SimpleAdapter(ShowActivity.this,messageList,R.layout.layout_show_list,from,to);
        //五个参数：上下文，ListView的布局，游标，来自数据库表的那些列，ListView布局中的显示控件，0.
        ListView listView=findViewById(R.id.show_item);
        listView.setAdapter(adapter);
    }

}