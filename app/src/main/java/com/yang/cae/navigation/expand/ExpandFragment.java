package com.yang.cae.navigation.expand;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mengpeng.mphelper.ToastUtils;
import com.yang.cae.R;
import com.yang.cae.configure.requestAPI.RequestAPI;
import com.yang.cae.util.okHttpUtil.RetrofitUtil;
import com.yang.cae.util.xbasebrowerUtil.XBaseBrowserUtil;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ExpandFragment extends Fragment implements View.OnClickListener  {
    private EditText webSearch;
    private RequestAPI requestApi;

    public static ExpandFragment newInstance() {
        return new ExpandFragment();
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        requestApi = RetrofitUtil.getRetrofit()
                .create(RequestAPI.class);
        View root = inflater.inflate(R.layout.fragment_expand, container, false);
        root.findViewById(R.id.baidu).setOnClickListener(this);
        root.findViewById(R.id.sougou).setOnClickListener(this);
        root.findViewById(R.id._360).setOnClickListener(this);
        root.findViewById(R.id.bing).setOnClickListener(this);
        root.findViewById(R.id.china).setOnClickListener(this);
        root.findViewById(R.id.google).setOnClickListener(this);
        root.findViewById(R.id.taobao).setOnClickListener(this);
        root.findViewById(R.id.pinduoduo).setOnClickListener(this);
        webSearch =(EditText) root.findViewById(R.id.web_search);
        //root.findViewById(R.id.bt).setOnClickListener(this);
        Call<ResponseBody> call;
        return root;

    }


    @Override
    public void onClick(View v) {
        String searchContent = webSearch.getText().toString();
        Call<ResponseBody> call = requestApi.getSearch(searchContent);
        getData(call);
        switch (v.getId()) {
            case R.id.baidu:
                XBaseBrowserUtil
                        .startActivity(getContext(),"https://www.baidu.com/s?wd="+searchContent);
                break;
            case R.id.sougou:
                XBaseBrowserUtil.startActivity(getContext(),"https://www.sogou.com/web?query="+searchContent);
                break;
            case R.id._360:
                XBaseBrowserUtil
                        .startActivity(getContext(),"https://www.so.com/s?q="+searchContent);
                break;
             case R.id.china:
                 XBaseBrowserUtil
                         .startActivity(getContext(),"http://www.chinaso.com/newssearch/all/allResults?q="+searchContent);
                break;
            case R.id.bing:
                XBaseBrowserUtil
                        .startActivity(getContext(),"https://cn.bing.com/search?q="+searchContent);
                break;
            case R.id.google:
                Toast.makeText(getActivity(), "google暂时关闭", Toast.LENGTH_SHORT).show();
                break;
            case R.id.taobao:
                XBaseBrowserUtil
                        .startActivity(getContext(),"http://taobao.com");
                break;
            case R.id.pinduoduo:
                XBaseBrowserUtil
                        .startActivity(getContext(),"https://mobile.pinduoduo.com/");
                break;
        }

    }
    public void getData(Call<ResponseBody> call){
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    ToastUtils.onSuccessShowToast("请求成功！");
                }else {
                    ToastUtils.onErrorShowToast("请求失败！");
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });
    }


}