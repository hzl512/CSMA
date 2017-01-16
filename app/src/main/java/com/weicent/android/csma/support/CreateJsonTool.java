package com.weicent.android.csma.support;

import com.google.gson.Gson;

/**
 * Created by admin on 2016/4/9.
 * json字符串生成
 */
public class CreateJsonTool {
    public static String createJsonString(Object object) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(object); // 用Gson方式 把object 保存为 json字符串
        return jsonString;
    }
}
