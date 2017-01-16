package com.weicent.android.csma.support;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast 工具类
 */
public class T {
    public static void showLong(Context context,String text) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }
    public static void showShort(Context context,String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
}
