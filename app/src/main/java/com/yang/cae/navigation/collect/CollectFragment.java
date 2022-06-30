package com.yang.cae.navigation.collect;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.mengpeng.mphelper.ToastUtils;
import com.yang.cae.R;
import com.yang.cae.DataDTO;
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

public class CollectFragment extends Fragment {
    private View root;
    private List<Map<String,Object> > messageList;
    private List<DataDTO> dataDTOList;
    private RequestAPI requestApi;
    ListView listView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_collect, container, false);
        listView=root.findViewById(R.id.collect_item);
        requestApi = RetrofitUtil.getRetrofit()
                .create(RequestAPI.class);
        Call<ResponseBody> call = requestApi.getCollect();
        getData(call);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), ShowActivity.class);
                intent.putExtra("dataDTO", dataDTOList.get(position));
                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String messageId = dataDTOList.get(position).getMessageId();
                Call<ResponseBody> deleteCollect = requestApi.deleteCollect(messageId);
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("移除收藏");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getData(deleteCollect);
                        ToastUtils.onSuccessShowToast("已删除");
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
                return true;
            }
        });
        return root;
    }

    private void renderListView(List<DataDTO> dataDTOList){
        messageList = new ArrayList<>();
        int num = 0;
        for (DataDTO dataDTO : dataDTOList) {
            num = num+1;
            //定义一个界面与数据的混合体,一个item代表一行记录
            Map<String,Object> item = new HashMap<>();
            //一行记录，包含多个控件
            item.put("num",num);
            item.put("flag", dataDTO.getFlag());
            item.put("id", dataDTO.getMessageId());
            item.put("title", dataDTO.getMessageName());
            item.put("collect_time", dataDTO.getDate());
            messageList.add(item);
        }
        String[] from=new String[]{"num","title","collect_time"};
        int to[]=new int[]{R.id.num,R.id.title,R.id.collect_time};
        SimpleAdapter adapter=new SimpleAdapter(getActivity(),messageList,R.layout.layout_collect_list,from,to);
        //五个参数：上下文，ListView的布局，游标，来自数据库表的那些列，ListView布局中的显示控件，0.
        listView.setAdapter(adapter);
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