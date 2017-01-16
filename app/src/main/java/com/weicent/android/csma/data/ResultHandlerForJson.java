package com.weicent.android.csma.data;

import com.google.gson.Gson;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;

//使用gson将json请求结果对象化
public abstract class ResultHandlerForJson<T extends Object> extends TextHttpResponseHandler {
	private Class<T> clazz;
	private Gson gson;

	protected ResultHandlerForJson(Class<T> clazz) {
		this.clazz = clazz;
		gson = new Gson();
	}

	/**
	 * 使用Gson解析resultString字符串数据
	 */
	@Override
	public void onSuccess(int statusCode, Header[] headers, String resultString) {
		try {
			T t = gson.fromJson(resultString, clazz);
			if (statusCode != 200) {
				return;
			}
			try {
				onSuccess(statusCode, headers, t);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (TypeNotPresentException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onFailure(int statusCode, Header[] headers,
			String resultString, Throwable throwable) {
	}

	/**
	 * 使用Gson获取Json对象
	 * 
	 * @param statusCode
	 *            状态代码
	 * @param headers
	 *            http头信息
	 * @param resultJson
	 *            Json转换之后的对象
	 */
	public abstract void onSuccess(int statusCode, Header[] headers,
			T resultJson);

}
