package com.weicent.android.csma.ui.list;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ab.activity.AbActivity;
import com.ab.view.pullview.AbPullToRefreshView;
import com.ab.view.titlebar.AbTitleBar;
import com.weicent.android.csma.App;
import com.weicent.android.csma.R;
import com.weicent.android.csma.adapter.result.CommodityAdapter;
import com.weicent.android.csma.data.Global;
import com.weicent.android.csma.data.NetWorkWeb;
import com.weicent.android.csma.data.ResultHandlerForJson;
import com.weicent.android.csma.data.model.result.Category;
import com.weicent.android.csma.data.request.model.ReqCommodity;
import com.weicent.android.csma.data.result.list.ResCommodityList;
import com.weicent.android.csma.support.CreateJsonTool;
import com.weicent.android.csma.support.T;
import com.weicent.android.csma.ui.IBinDing;
import com.weicent.android.csma.ui.detail.CommodityDetailActivity;

import org.apache.http.Header;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Commodity列表
 */
public class CommodityListActivity extends AbActivity implements IBinDing, AbPullToRefreshView.OnHeaderRefreshListener
        , AbPullToRefreshView.OnFooterLoadListener {
    @Bind(R.id.layoutLoading)
    RelativeLayout layoutLoading;
    @Bind(R.id.layoutMsg)
    RelativeLayout layoutMsg;
    @Bind(R.id.textMsg)
    TextView textMsg;
    @Bind(R.id.layoutContext)
    LinearLayout layoutContext;

    @Bind(R.id.gridView)
    GridView gridView;
    @Bind(R.id.abPullToRefreshView)
    AbPullToRefreshView abPullToRefreshView;

    private CommodityAdapter adapter = null;//数据适配器
    private int currentPage = 1;//当前页
    private int total = 0;//页总数
    private int pageSize = 5;//页大小

    private AbTitleBar abTitleBar = null;
    private Category model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAbContentView(R.layout.activity_commodity_list);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    public void initView() {
        model = getIntent().getParcelableExtra(App.MODEL_NAME);
        abTitleBar = this.getTitleBar();
        abTitleBar.setTitleText(model.categoryName);
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
        adapter = new CommodityAdapter(this, null);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position >= 0 && position < adapter.getCount()) {
                    startActivity(new Intent(CommodityListActivity.this, CommodityDetailActivity.class).putExtra(App.MODEL_NAME, adapter.getItem(position)));
                }
            }
        });
        initData();
    }

    @Override
    public void initData() {
        layoutContext.setVisibility(View.GONE);
        layoutLoading.setVisibility(View.VISIBLE);
        layoutMsg.setVisibility(View.GONE);
        onHeaderRefresh(null);
    }

    @Override
    public void onHeaderRefresh(AbPullToRefreshView view) {
        ReqCommodity reqCommodity = new ReqCommodity();
        reqCommodity.data.setCategoryID(model.id);
        reqCommodity.paging = true;
        reqCommodity.page_no = 1;
        reqCommodity.page_size = pageSize;
        NetWorkWeb.getInstance().doRequest(Global.URL_COMMODITY_SERVLET, new ResultHandlerForJson<ResCommodityList>(ResCommodityList.class) {

            @Override
            public void onSuccess(int statusCode, Header[] headers, ResCommodityList resultJson) {
                if (resultJson.errorcode == 0) {
                    NetWorkWeb.getInstance().doLogResultDataString("CommodityServlet first", resultJson.data);
                    if (adapter != null)
                        adapter.update(resultJson.data);
                    total = resultJson.total;
                    if (resultJson.data.size() > 0) {
                        //显示内容
                        layoutMsg.setVisibility(View.GONE);
                        layoutContext.setVisibility(View.VISIBLE);
                    } else {
                        //显示重试
                        textMsg.setText("暂无数据，请点击屏幕重试");
                        layoutMsg.setVisibility(View.VISIBLE);
                        layoutContext.setVisibility(View.GONE);
                    }
                } else {
                    //显示重试
                    textMsg.setText("请求出错，请点击屏幕重试");
                    layoutMsg.setVisibility(View.VISIBLE);
                    layoutContext.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String resultString, Throwable throwable) {
                if (adapter.isEmpty()) {
                    textMsg.setText("请求出错，请点击屏幕重试");
                    layoutMsg.setVisibility(View.VISIBLE);
                    layoutContext.setVisibility(View.GONE);
                } else {
                    T.showShort(CommodityListActivity.this, App.NET_OUT_MSG);
                }
            }

            @Override
            public void onFinish() {
                currentPage = 1;
                abPullToRefreshView.onHeaderRefreshFinish();
                layoutLoading.setVisibility(View.GONE);
            }
        }, CreateJsonTool.createJsonString(reqCommodity), 7);
    }

    @Override
    public void onFooterLoad(AbPullToRefreshView view) {
        ReqCommodity reqCommodity = new ReqCommodity();
        reqCommodity.data.setCategoryID(model.id);
        reqCommodity.paging = true;
        reqCommodity.page_no = currentPage;
        reqCommodity.page_size = pageSize;
        if (adapter.getCount() >= total) {
            abPullToRefreshView.onFooterLoadFinish();
            T.showShort(CommodityListActivity.this, App.NOT_DATA_MSG);
        } else {
            currentPage++;
            NetWorkWeb.getInstance().doRequest(Global.URL_COMMODITY_SERVLET, new ResultHandlerForJson<ResCommodityList>(ResCommodityList.class) {

                @Override
                public void onSuccess(int statusCode, Header[] headers, ResCommodityList resultJson) {
                    if (resultJson.errorcode == 0) {
                        NetWorkWeb.getInstance().doLogResultDataString("CommodityServlet more", resultJson.data);
                        if (adapter != null)
                            adapter.addAll(resultJson.data);
                    } else {
                        currentPage--;
                        T.showShort(CommodityListActivity.this, App.NET_OUT_MSG);
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String resultString, Throwable throwable) {
                    currentPage--;
                    T.showShort(CommodityListActivity.this, App.NET_OUT_MSG);
                }

                @Override
                public void onFinish() {
                    abPullToRefreshView.onFooterLoadFinish();
                }
            }, CreateJsonTool.createJsonString(reqCommodity), 7);
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

