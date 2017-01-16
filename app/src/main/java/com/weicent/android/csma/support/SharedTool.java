package com.weicent.android.csma.support;

import android.content.SharedPreferences;

import com.weicent.android.csma.App;


/**
 * SharedPreferences 工具类 
 */
public class SharedTool {
	public static final String SHARED_PATH = "reader_shared";

	public static SharedPreferences getDefaultSharedPreferences() {
		return App.getContext().getSharedPreferences(SHARED_PATH,
				App.getContext().MODE_PRIVATE);
	}

	public static void putInt(String key, int value) {
		SharedPreferences sharedPreferences = getDefaultSharedPreferences();
		SharedPreferences.Editor edit = sharedPreferences.edit();
		edit.putInt(key, value);
		edit.commit();
	}

	public static int getInt(String key,int defalutValue) {
		SharedPreferences sharedPreferences = getDefaultSharedPreferences();
		return sharedPreferences.getInt(key, defalutValue);
	}

	public static int getInt(String key) {
		SharedPreferences sharedPreferences = getDefaultSharedPreferences();
		return sharedPreferences.getInt(key, 0);
	}

    public static void putString(String key, String value) {
		SharedPreferences sharedPreferences = getDefaultSharedPreferences();
		SharedPreferences.Editor edit = sharedPreferences.edit();
		edit.putString(key, value);
		edit.commit();
	}

	public static void removeKey(String key) {
		SharedPreferences sharedPreferences = getDefaultSharedPreferences();
		SharedPreferences.Editor edit = sharedPreferences.edit();
		edit.remove(key);
		edit.commit();
	}

	public static String getString(String key) {
		SharedPreferences sharedPreferences = getDefaultSharedPreferences();
		return sharedPreferences.getString(key, "");
	}

	public static void putBoolean(String key, boolean value) {
		SharedPreferences sharedPreferences = getDefaultSharedPreferences();
		SharedPreferences.Editor edit = sharedPreferences.edit();
		edit.putBoolean(key, value);
		edit.commit();
	}

	public static boolean getBoolean(String key, boolean defValue) {
		SharedPreferences sharedPreferences = getDefaultSharedPreferences();
		return sharedPreferences.getBoolean(key, defValue);
	}

}
