package com.weicent.android.csma;

import android.app.Application;


//全局类
public class App extends Application {
    private static App context;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
        context = this;
    }

    //初始化
    private void init(){
    }

    public static App getContext() {
        return context;
    }

    //网络提示
    public final static String NET_OUT_MSG = "请求失败或超时，请稍后再试";
    public final static String REQUEST_OUT_MSG = "请求失败或超时，请点击重试按钮";
    public final static String NOT_DATA_MSG = "已显示全部内容";

    public final static String SHARED_ID="id";
    public final static String SHARED_USERNAME="name";
    public final static String SHARED_PWD="pwd";
    
    public final static String MODEL_NAME="model";
    public final static String INTENT_KEY_ID="id";

//    public final static String BASE_URL = "http://csma01.duapp.com/"; //根链接，互联网
    public final static String BASE_URL = "http://192.168.1.226:8080/CampusSecondaryMarket/"; //根链接，局域网
//    public final static String BASE_URL = "http://10.0.2.2:8080/CampusSecondaryMarket/"; //根链接，本地

}
