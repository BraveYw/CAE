package com.yang.cae.function.search;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import com.mengpeng.mphelper.ToastUtils;
import com.yang.cae.DataDTO;
import com.yang.cae.R;
import com.yang.cae.bean.result.Result;
import com.yang.cae.configure.requestAPI.RequestAPI;
import com.yang.cae.show.ShowActivity;
import com.yang.cae.util.jsonUtil.JsonUtil;
import com.yang.cae.util.okHttpUtil.RetrofitUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {
    //搜索框
    private EditText system_search;
    //搜索按钮
    private Button search;
    private ImageView back;
    List<Map<String,Object> > messageList;
    List<DataDTO> dataDTOList;
    RequestAPI requestApi;
    ListView listView;
    Call<ResponseBody> call;
    SimpleAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ToastUtils.getInstance().initToast(this);
        initView();
        setListener();
        requestApi = RetrofitUtil.getRetrofit()
                .create(RequestAPI.class);
        call = requestApi.getSearchRecord();
              getData(call);

              //list点击
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //String flag = messageList.get(position).get("flag").toString();
                DataDTO dataDTO = dataDTOList.get(position);
                if ("search".equals(dataDTO.getFlag())){
                    Call<ResponseBody> search = requestApi.getSearch(dataDTO.getMessageName());
                    system_search.setText(dataDTO.getMessageName());
                    getData(search);
                }else {
                    Intent intent = new Intent(SearchActivity.this, ShowActivity.class);
                    intent.putExtra("dataDTO", dataDTO);
                    startActivity(intent);
                    //ToastUtils.onErrorShowToast(dataDTO.toString());

                }
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                DataDTO dataDTO = dataDTOList.get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(SearchActivity.this);
                if ("search".equals(dataDTO.getFlag())){
                    Call<ResponseBody> deleteSearchRecord = requestApi.deleteSearchRecord(dataDTO.getMessageId());
                    builder.setTitle("删除搜索记录");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getData(deleteSearchRecord);
                            ToastUtils.onSuccessShowToast("已删除");
                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            dialogInterface.dismiss();
                        }
                    });
                }else {
                    builder.setTitle("添加收藏");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
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
                                    }else {
                                        ToastUtils.onErrorShowToast("收藏失败！");
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {

                                }
                            });

                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            dialogInterface.dismiss();
                        }
                    });

                }
                builder.show();
                return true;
            }
        });

    }

    private void initView(){
        system_search = (EditText)findViewById(R.id.system_search);
        search = (Button) findViewById(R.id.search);
        back =(ImageView)findViewById(R.id.back);
        listView=findViewById(R.id.search_item);
        system_search.setFocusable(true);
        system_search.setFocusableInTouchMode(true);
        system_search.requestFocus();
        //方法一  获取焦点
        InputMethodManager imm = (InputMethodManager)SearchActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        //方法二
//      SearchActivity.this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }
    private void setListener(){
        search.setOnClickListener(this);
        back.setOnClickListener(this);

    }
    private void renderListView(List<DataDTO> dataDTOList){
        messageList = new ArrayList<>();
        int num = 0;
        for (DataDTO dataDTO : dataDTOList) {
            num = num+1;
            //定义一个界面与数据的混合体,一个item代表一行记录
            Map<String,Object> item = new HashMap<String,Object>();
            //一行记录，包含多个控件
            item.put("id", dataDTO.getMessageId());
            item.put("flag", dataDTO.getFlag());
            item.put("search_num",num);
            item.put("search_title", dataDTO.getMessageName());
            item.put("search_time", dataDTO.getDate());
            messageList.add(item);
        }
        String[] from=new String[]{"search_num","search_title","search_time"};
        int to[]=new int[]{R.id.search_num,R.id.search_title,R.id.search_time};
        adapter=new SimpleAdapter(SearchActivity.this,messageList,R.layout.layout_search_list,from,to);
        //五个参数：上下文，ListView的布局，游标，来自数据库表的那些列，ListView布局中的显示控件，0.
        listView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        final String systemSearch = system_search.getText().toString();
        switch (v.getId()){
            //返回
            case R.id.back:
                finish();
                break;
            case R.id.search:
                if (systemSearch.isEmpty()){
                    ToastUtils.onErrorShowToast("请输入搜索内容！");
                }else {
                    Call<ResponseBody> search = requestApi.getSearch(systemSearch);
                    getData(search);
                }
                break;
        }
    }

    public void getData(Call<ResponseBody> call){
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.isSuccessful()){
                        String resp = new String(response.body().bytes());
                        Result result = JsonUtil.respBodyParse(resp);
                        dataDTOList = result.getData().toJavaList(DataDTO.class);
                        renderListView(dataDTOList);
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
}