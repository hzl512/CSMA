package com.weicent.android.csma.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.ab.fragment.AbFragment;
import com.ab.view.pullview.AbPullToRefreshView;
import com.weicent.android.csma.App;
import com.weicent.android.csma.R;
import com.weicent.android.csma.adapter.result.CategoryAdapter;
import com.weicent.android.csma.data.Global;
import com.weicent.android.csma.data.NetWorkWeb;
import com.weicent.android.csma.data.ResultHandlerForJson;
import com.weicent.android.csma.data.result.list.ResCategoryList;
import com.weicent.android.csma.support.T;
import com.weicent.android.csma.ui.list.CommodityListActivity;

import org.apache.http.Header;

import butterknife.ButterKnife;

/**
 * 分类
 */
public class CategoryFragment extends AbFragment implements AbPullToRefreshView.OnHeaderRefreshListener
        , AbPullToRefreshView.OnFooterLoadListener {
    GridView gridView;
    AbPullToRefreshView abPullToRefreshView;
    TextView textTitle;
    private CategoryAdapter adapter = null;//数据适配器
    private int currentPage = 1;//当前页
    private int total = 0;//页总数
    private int pageSize = 5;//页大小

    @Override
    public View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, null);
        abPullToRefreshView = ButterKnife.findById(view, R.id.abPullToRefreshView);
        gridView = ButterKnife.findById(view, R.id.gridView);
        textTitle = ButterKnife.findById(view, R.id.textTitle);
        initView();
        this.setAbFragmentOnLoadListener(new AbFragmentOnLoadListener() {

            @Override
            public void onLoad() {
                onHeaderRefresh(null);
            }

        });
        return view;
    }

    public void initView() {
        textTitle.setText("分类");
        abPullToRefreshView.setOnHeaderRefreshListener(this);
        abPullToRefreshView.setOnFooterLoadListener(this);
        abPullToRefreshView.getHeaderView().setHeaderProgressBarDrawable(this.getResources().getDrawable(R.drawable.progress_circular));
        abPullToRefreshView.getFooterView().setFooterProgressBarDrawable(this.getResources().getDrawable(R.drawable.progress_circular));
        adapter = new CategoryAdapter(getActivity(), null);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position >= 0 && position < adapter.getCount()) {
                    startActivity(new Intent(getActivity(), CommodityListActivity.class).putExtra(App.MODEL_NAME,adapter.getItem(position)));
                }
            }
        });
    }

    @Override
    public void setResource() {
        //设置加载的资源
        this.setLoadDrawable(R.drawable.ic_load);
        this.setLoadMessage("正在查询,请稍候");
        this.setTextColor(Color.BLACK);
        this.setRefreshDrawable(R.drawable.ic_refresh);
        this.setRefreshMessage("请求出错，请点击按钮重试");
    }

    @Override
    public void onHeaderRefresh(AbPullToRefreshView view) {
        NetWorkWeb.getInstance().doRequest(Global.URL_CATEGORY_SERVLET, new ResultHandlerForJson<ResCategoryList>(ResCategoryList.class) {

            @Override
            public void onSuccess(int statusCode, Header[] headers, ResCategoryList resultJson) {
                if (resultJson.errorcode == 0) {
                    NetWorkWeb.getInstance().doLogResultDataString("CategoryServlet first", resultJson.data);
                    if (adapter != null)
                        adapter.update(resultJson.data);
                    total = resultJson.total;
                    if (total > 0) {
                        //显示内容
                        showContentView();
                    } else {
                        //显示重试的框
                        CategoryFragment.this.setRefreshMessage("暂无数据，请点击按钮重试");
                        showRefreshView();
                    }
                } else {
                    //显示重试的框
                    showRefreshView();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String resultString, Throwable throwable) {
                if (adapter.isEmpty()) {
                    //显示重试的框
                    CategoryFragment.this.setRefreshMessage("请求出错，请点击按钮重试");
                    if (getActivity() == null) return;
                    try {
                        showRefreshView();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    T.showShort(getActivity(),App.NET_OUT_MSG);
                }
            }

            @Override
            public void onFinish() {
                currentPage = 1;
                abPullToRefreshView.onHeaderRefreshFinish();
            }
        }, "{\"paging\":false,\"page_no\":1,\"page_size\":" + pageSize + "}", 4);
    }

    @Override
    public void onFooterLoad(AbPullToRefreshView view) {
        if (adapter.getCount() >= total) {
            abPullToRefreshView.onFooterLoadFinish();
            T.showShort(getActivity(),App.NOT_DATA_MSG);
        } else {
            currentPage++;
            NetWorkWeb.getInstance().doRequest(Global.URL_CATEGORY_SERVLET, new ResultHandlerForJson<ResCategoryList>(ResCategoryList.class) {

                @Override
                public void onSuccess(int statusCode, Header[] headers, ResCategoryList resultJson) {
                    if (resultJson.errorcode == 0) {
                        NetWorkWeb.getInstance().doLogResultDataString("CategoryServlet more", resultJson.data);
                        if (adapter != null)
                            adapter.addAll(resultJson.data);
                    } else {
                        currentPage--;
                        T.showShort(getActivity(),App.NET_OUT_MSG);
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String resultString, Throwable throwable) {
                    currentPage--;
                    T.showShort(getActivity(),App.NET_OUT_MSG);
                }

                @Override
                public void onFinish() {
                    abPullToRefreshView.onFooterLoadFinish();
                }
            }, "{\"paging\":false,\"page_no\":" + currentPage + ",\"page_size\":" + pageSize + "}", 4);
        }

    }

}

