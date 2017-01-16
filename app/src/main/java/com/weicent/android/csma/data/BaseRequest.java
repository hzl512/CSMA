package com.weicent.android.csma.data;

//响应基类
public class BaseRequest {
	public boolean paging;//是否分页
	public Integer page_no;//页码
	public Integer page_size;//页大小
	public Integer id;
	
	@Override
	public String toString() {
		return "BaseRequest [paging=" + paging + ", page_no=" + page_no
				+ ", page_size=" + page_size + ", id=" + id + "]";
	}
	
}
