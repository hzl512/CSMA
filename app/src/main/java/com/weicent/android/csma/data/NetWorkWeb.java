package com.weicent.android.csma.data;

import com.ab.util.AbLogUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.weicent.android.csma.App;

import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

import java.util.ArrayList;
import java.util.List;

//网络操作
public class NetWorkWeb<T extends Object> {

    private AsyncHttpClient requestClient;

    private static NetWorkWeb mContent;

    private NetWorkWeb() {
    }

    public static NetWorkWeb getInstance() {
        if (mContent == null) mContent = new NetWorkWeb();
        return mContent;
    }

    // HttpClient自动处理带有超时的功能
    public AsyncHttpClient getHttpClient() {
        AsyncHttpClient client = new AsyncHttpClient();
        PersistentCookieStore mCookieStore = new PersistentCookieStore(App.getContext());
        client.setCookieStore(mCookieStore);
        client.setTimeout(10000);//10秒
        return client;
    }

    // request for params
    private void post(String request, ResultHandlerForJson<T> handler, RequestParams params) {
        getHttpClient().post(request, params, handler);
        AbLogUtil.d("postJson", request + "\n" + params);
    }
    
    // request for json
    private void postJson(String request, String JsonStr, ResultHandlerForJson<T> handler) {
        StringEntity stringEntity;
        try {
            stringEntity = new StringEntity(JsonStr, HTTP.UTF_8);
            stringEntity.setContentType(RequestParams.APPLICATION_JSON);
            stringEntity.setContentEncoding(new BasicHeader(HTTP.CONTENT_ENCODING, HTTP.UTF_8));
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        AbLogUtil.d("postJson", request + "\n" + JsonStr);
//        getHttpClient().post(App.getContext(), request, stringEntity, RequestParams.APPLICATION_JSON, handler);
        requestClient= getHttpClient();
        requestClient.post(App.getContext(), request, stringEntity, RequestParams.APPLICATION_JSON, handler);
    }

    public AsyncHttpClient getRequestClient(){
        return requestClient;
    }

    //显示获取数据的log信息
    public void doLogResultDataString(String tag, List<Object> results) {
        try {
            for (Object model : results == null ? new ArrayList<Object>() : results) {
                AbLogUtil.d(tag, model.toString());
            }
        } catch (Exception e) {
            AbLogUtil.d(tag, "doLogResultDataString is fail");
        }
    }

    //显示获取数据的log信息
    public void doLogResultModelString(String tag, Object result) {
        try {
            result = result == null ? new Object() : result;
            AbLogUtil.d(tag, result.toString());
        } catch (Exception e) {
            AbLogUtil.d(tag, "doLogResultModelString is fail");
        }
    }
    
    //请求操作
    public void doRequest(String aName, ResultHandlerForJson<T> handler, String requestStr,int aType) {
        postJson(App.BASE_URL + aName + "?action=" + aType,requestStr,handler);
    }

    //请求操作
    public void doRequest(String aName, ResultHandlerForJson<T> handler, RequestParams  requestParams) {
        post(App.BASE_URL + aName,handler,requestParams);
    }
    
}
