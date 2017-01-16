package com.weicent.android.csma.support;

import android.content.Context;

import com.ab.util.AbStrUtil;

/**
 * Created by admin on 2016/4/10.
 */
public class MyStringTool {

    public static boolean isNullToToast(Context context,String c,String t) {
        if (AbStrUtil.isEmpty(c))  {
            T.showShort(context,t+"不为空");
            return  true;
        }
        return  false;
    }
}
