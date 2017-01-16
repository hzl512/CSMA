package com.weicent.android.csma.ui.list;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ab.activity.AbActivity;
import com.ab.view.pullview.AbPullToRefreshView;
import com.ab.view.titlebar.AbTitleBar;
import com.weicent.android.csma.App;
import com.weicent.android.csma.R;
import com.weicent.android.csma.adapter.result.CategoryAdapter;
import com.weicent.android.csma.data.Global;
import com.weicent.android.csma.data.NetWorkWeb;
import com.weicent.android.csma.data.ResultHandlerForJson;
import com.weicent.android.csma.data.result.list.ResCategoryList;
import com.weicent.android.csma.support.T;
import com.weicent.android.csma.ui.IBinDing;

import org.apache.http.Header;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 类别列表
 */
public class CategoryListActivity extends AbActivity implements IBinDing, AbPullToRefreshView.OnHeaderRefreshListener
        , AbPullToRefreshView.OnFooterLoadListener {
    @Bind(R.id.layoutLoading)
    RelativeLayout layoutLoading;
    @Bind(R.id.layoutMsg)
    RelativeLayout layoutMsg;
    @Bind(R.id.textMsg)
    TextView layoutMsgText;
    @Bind(R.id.layoutContext)
    LinearLayout mLayoutContext;

    @Bind(R.id.listView)
    ListView listView;
    @Bind(R.id.abPullToRefreshView)
    AbPullToRefreshView abPullToRefreshView;

    private CategoryAdapter adapter = null;//数据适配器
    private int currentPage = 1;//当前页
    private int total = 0;//页总数
    private int pageSize = 10;//页大小
    private AbTitleBar abTitleBar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAbContentView(R.layout.activity_category_list);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    public void initView() {
        abTitleBar = this.getTitleBar();
        abTitleBar.setTitleText("类别");
        abTitleBar.setLogo(R.drawable.button_selector_back);
        abTitleBar.setTitleBarBackgroundColor(Color.parseColor("#FC5720"));
        abTitleBar.setTitleTextMargin(10, 0, 0, 0);
        abTitleBar.setLogoLine(R.drawable.line);
        layoutMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
            }
        });

        abPullToRefreshView.setOnHeaderRefreshListener(this);
        abPullToRefreshView.setOnFooterLoadListener(this);
        abPullToRefreshView.getHeaderView().setHeaderProgressBarDrawable(this.getResources().getDrawable(R.drawable.progress_circular));
        abPullToRefreshView.getFooterView().setFooterProgressBarDrawable(this.getResources().getDrawable(R.drawable.progress_circular));
        adapter = new CategoryAdapter(this, null);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position >= 0 && position < adapter.getCount()) {
                    setResult(RESULT_OK, new Intent().putExtra(App.MODEL_NAME, adapter.getItem(position)));
                    finish();
                }
            }
        });
        initData();
    }

    @Override
    public void initData() {
        mLayoutContext.setVisibility(View.GONE);
        layoutLoading.setVisibility(View.VISIBLE);
        layoutMsg.setVisibility(View.GONE);
        onHeaderRefresh(null);
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
                    if (resultJson.data.size() > 0) {
                        //显示内容
                        layoutMsg.setVisibility(View.GONE);
                        mLayoutContext.setVisibility(View.VISIBLE);
                    } else {
                        //显示重试
                        layoutMsgText.setText("暂无数据，请点击屏幕重试");
                        layoutMsg.setVisibility(View.VISIBLE);
                        mLayoutContext.setVisibility(View.GONE);
                    }
                } else {
                    //显示重试
                    layoutMsgText.setText("请求出错，请点击屏幕重试");
                    layoutMsg.setVisibility(View.VISIBLE);
                    mLayoutContext.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String resultString, Throwable throwable) {
                if (adapter.isEmpty()) {
                    layoutMsgText.setText("请求出错，请点击屏幕重试");
                    layoutMsg.setVisibility(View.VISIBLE);
                    mLayoutContext.setVisibility(View.GONE);
                } else {
                    T.showShort(CategoryListActivity.this, App.NET_OUT_MSG);
                }
            }

            @Override
            public void onFinish() {
                currentPage = 1;
                abPullToRefreshView.onHeaderRefreshFinish();
                layoutLoading.setVisibility(View.GONE);
            }
        }, "{\"paging\":true,\"page_no\":1,\"page_size\":" + pageSize + "}", 4);
    }

    @Override
    public void onFooterLoad(AbPullToRefreshView view) {
        if (adapter.getCount() >= total) {
            abPullToRefreshView.onFooterLoadFinish();
            T.showShort(CategoryListActivity.this, App.NOT_DATA_MSG);
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
                        T.showShort(CategoryListActivity.this, App.NET_OUT_MSG);
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String resultString, Throwable throwable) {
                    currentPage--;
                    T.showShort(CategoryListActivity.this, App.NET_OUT_MSG);
                }

                @Override
                public void onFinish() {
                    abPullToRefreshView.onFooterLoadFinish();
                }
            }, "{\"paging\":true,\"page_no\":" + currentPage + ",\"page_size\":" + pageSize + "}", 4);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (NetWorkWeb.getInstance().getHttpClient() == null) {
//            AbLogUtil.d("cancelAllRequests", " is null");
            return;
        } else {
            NetWorkWeb.getInstance().getHttpClient().cancelAllRequests(true);//关闭所有请求
//            AbLogUtil.d("cancelAllRequests"," is ok");
        }
    }
}

