package com.weicent.android.csma.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.ab.fragment.AbAlertDialogFragment;
import com.ab.fragment.AbFragment;
import com.ab.util.AbDialogUtil;
import com.ab.view.pullview.AbPullToRefreshView;
import com.weicent.android.csma.App;
import com.weicent.android.csma.R;
import com.weicent.android.csma.adapter.result.BuysAdapter;
import com.weicent.android.csma.data.Global;
import com.weicent.android.csma.data.NetWorkWeb;
import com.weicent.android.csma.data.ResultHandlerForJson;
import com.weicent.android.csma.data.result.list.ResBuysList1;
import com.weicent.android.csma.support.SharedTool;
import com.weicent.android.csma.support.T;
import com.weicent.android.csma.ui.LoginActivity;
import com.weicent.android.csma.ui.add.BuysAddActivity;

import org.apache.http.Header;

import butterknife.ButterKnife;

/**
 * 求购列表
 */
public class BuysFragment extends AbFragment implements AbPullToRefreshView.OnHeaderRefreshListener
        , AbPullToRefreshView.OnFooterLoadListener {
    ListView listView;
    TextView textTitle, textRight;
    AbPullToRefreshView abPullToRefreshView;
    private BuysAdapter adapter = null;//数据适配器
    private int currentPage = 1;//当前页
    private int total = 0;//页总数
    private int pageSize = 10;//页大小

    @Override
    public View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_buys, null);
        abPullToRefreshView = ButterKnife.findById(view, R.id.abPullToRefreshView);
        listView = ButterKnife.findById(view, R.id.listView);
        textTitle = ButterKnife.findById(view, R.id.textTitle);
        textRight = ButterKnife.findById(view, R.id.textRight);
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
        textTitle.setText("求购专区");
        textRight.setVisibility(View.VISIBLE);
        textRight.setText("发布求购");
        textRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SharedTool.getInt(App.SHARED_ID) <= 0) {
                    AbDialogUtil.showAlertDialog(getActivity(), "提示", "登录后可以发布求购，现在登录？", new AbAlertDialogFragment.AbDialogOnClickListener() {
                        @Override
                        public void onPositiveClick() {
                            startActivity(new Intent(getActivity(), LoginActivity.class)
                                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                        }

                        @Override
                        public void onNegativeClick() {
                        }
                    });
                } else {
                    startActivity(new Intent(getActivity(), BuysAddActivity.class));
                }
            }
        });
        abPullToRefreshView.setOnHeaderRefreshListener(this);
        abPullToRefreshView.setOnFooterLoadListener(this);
        abPullToRefreshView.getHeaderView().setHeaderProgressBarDrawable(this.getResources().getDrawable(R.drawable.progress_circular));
        abPullToRefreshView.getFooterView().setFooterProgressBarDrawable(this.getResources().getDrawable(R.drawable.progress_circular));
        adapter = new BuysAdapter(getActivity(), null);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position >= 0 && position < adapter.getCount()) {
                    //startActivity(new Intent(mActivity, BuysDetailActivity.class).putExtra(App.MODEL_NAME,adapter.getItem(position)));
                    //startActivity(new Intent(mActivity, BuysReDetailActivity.class).putExtra(App.MODEL_NAME,adapter.getItem(position)));
                    //startActivity(new Intent(mActivity, BuysListActivity.class).putExtra(App.MODEL_NAME,adapter.getItem(position)));
                }
            }
        });
        adapter.setCallOnClickListener(new BuysAdapter.ICallOnClickListener() {
            @Override
            public void onClick(String phone) {
                final String phone1 = phone;
                LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
                View view = layoutInflater.inflate(R.layout.dialog_contact_the_seller, null);
                Button btnQQ = ButterKnife.findById(view, R.id.btnQQ);
                Button btnPhone = ButterKnife.findById(view, R.id.btnPhone);
                Button btnMessage = ButterKnife.findById(view, R.id.btnMessage);
                Button btnEsc = ButterKnife.findById(view, R.id.btnEsc);
                btnEsc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AbDialogUtil.removeDialog(getActivity());
                    }
                });
                btnQQ.setVisibility(View.GONE);
                btnPhone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AbDialogUtil.showAlertDialog(getActivity(), "确认呼叫", phone1
                                , new AbAlertDialogFragment.AbDialogOnClickListener() {
                            @Override
                            public void onPositiveClick() {
                                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone1)));
                            }

                            @Override
                            public void onNegativeClick() {
                            }
                        });
                    }
                });
                btnMessage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phone1)));
                    }
                });
                AbDialogUtil.showDialog(view, Gravity.BOTTOM);
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
        if (SharedTool.getInt(App.SHARED_ID) <= 0) {
            BuysFragment.this.setRefreshMessage("登录后可以查看联系方式，请点击现在登录");
        } else {
            NetWorkWeb.getInstance().doRequest("BuysServlet", new ResultHandlerForJson<ResBuysList1>(ResBuysList1.class) {

                @Override
                public void onSuccess(int statusCode, Header[] headers, ResBuysList1 resultJson) {
                    if (resultJson.errorcode == 0) {
                        NetWorkWeb.getInstance().doLogResultDataString("BuysServlet first", resultJson.data);
                        if (adapter != null)
                            adapter.update(resultJson.data);
                        total = resultJson.total;
                        if (total > 0) {
                            //显示内容
                            showContentView();
                        } else {
                            //显示重试的框
                            BuysFragment.this.setRefreshMessage("暂无数据，请点击按钮重试");
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
                        BuysFragment.this.setRefreshMessage("请求出错，请点击按钮重试");
                        if (getActivity() == null) return;
                        try {
                            showRefreshView();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        T.showShort(getActivity(), App.NET_OUT_MSG);
                    }
                }

                @Override
                public void onFinish() {
                    currentPage = 1;
                    abPullToRefreshView.onHeaderRefreshFinish();
                }
            }, "{\"paging\":true,\"page_no\":1,\"page_size\":" + pageSize + "}", 6);
        }
    }

    @Override
    public void onFooterLoad(AbPullToRefreshView view) {
        if (SharedTool.getInt(App.SHARED_ID) <= 0) {
            BuysFragment.this.setRefreshMessage("登录后可以查看联系方式，请点击登录");
        } else {
            if (adapter.getCount() >= total) {
                abPullToRefreshView.onFooterLoadFinish();
                T.showShort(getActivity(), App.NOT_DATA_MSG);
            } else {
                currentPage++;
                NetWorkWeb.getInstance().doRequest(Global.URL_BUYS_SERVLET, new ResultHandlerForJson<ResBuysList1>(ResBuysList1.class) {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, ResBuysList1 resultJson) {
                        if (resultJson.errorcode == 0) {
                            NetWorkWeb.getInstance().doLogResultDataString("BuysServlet more", resultJson.data);
                            if (adapter != null)
                                adapter.addAll(resultJson.data);
                        } else {
                            currentPage--;
                            T.showShort(getActivity(), App.NET_OUT_MSG);
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String resultString, Throwable throwable) {
                        currentPage--;
                        T.showShort(getActivity(), App.NET_OUT_MSG);
                    }

                    @Override
                    public void onFinish() {
                        abPullToRefreshView.onFooterLoadFinish();
                    }
                }, "{\"paging\":true,\"page_no\":" + currentPage + ",\"page_size\":" + pageSize + "}", 6);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (NetWorkWeb.getInstance().getHttpClient() == null) {
//            AbLogUtil.d("cancelAllRequests"," is null");
            return;
        } else {
            NetWorkWeb.getInstance().getHttpClient().cancelAllRequests(true);//关闭所有请求
//            AbLogUtil.d("cancelAllRequests"," is ok");
        }
    }
}

