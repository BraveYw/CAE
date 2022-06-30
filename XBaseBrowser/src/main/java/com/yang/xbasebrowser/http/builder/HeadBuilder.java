package com.yang.xbasebrowser.http.builder;

import com.yang.xbasebrowser.http.OkHttpUtils;
import com.yang.xbasebrowser.http.request.OtherRequest;
import com.yang.xbasebrowser.http.request.RequestCall;

/**
 * Created by zhy on 16/3/2.
 */
public class HeadBuilder extends GetBuilder
{
    @Override
    public RequestCall build()
    {
        return new OtherRequest(null, null, OkHttpUtils.METHOD.HEAD, url, tag, params, headers,id).build();
    }
}
