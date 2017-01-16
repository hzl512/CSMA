package com.weicent.android.csma.ui.detail;

import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ab.activity.AbActivity;
import com.ab.fragment.AbAlertDialogFragment;
import com.ab.image.AbImageLoader;
import com.ab.util.AbDateUtil;
import com.ab.util.AbDialogUtil;
import com.ab.view.titlebar.AbTitleBar;
import com.weicent.android.csma.App;
import com.weicent.android.csma.R;
import com.weicent.android.csma.data.BaseResult;
import com.weicent.android.csma.data.Global;
import com.weicent.android.csma.data.NetWorkWeb;
import com.weicent.android.csma.data.ResultHandlerForJson;
import com.weicent.android.csma.data.model.result.Commodity1;
import com.weicent.android.csma.data.result.model.ResCommodity;
import com.weicent.android.csma.support.SharedTool;
import com.weicent.android.csma.support.T;
import com.weicent.android.csma.ui.IBinDing;
import com.weicent.android.csma.ui.LoginActivity;

import org.apache.http.Header;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 详情
 */
public class CommodityDetailActivity extends AbActivity implements IBinDing {

    @Bind(R.id.textCommodityName)
    TextView textCommodityName;

    @Bind(R.id.textCommodityDetail)
    TextView textCommodityDetail;

    @Bind(R.id.textCommodityAddress)
    TextView textCommodityAddress;

    @Bind(R.id.textCommodityPrice)
    TextView textCommodityPrice;

    @Bind(R.id.textCommodityBargain)
    TextView textCommodityBargain;

    @Bind(R.id.textCommodityAddTime)
    TextView textCommodityAddTime;

    @Bind(R.id.textCommodityViews)
    TextView textCommodityViews;

    @Bind(R.id.layoutLoading)
    RelativeLayout layoutLoading;
    @Bind(R.id.layoutMsg)
    RelativeLayout layoutMsg;
    @Bind(R.id.layoutContext)
    LinearLayout layoutContext;

    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.imgView)
    ImageView imgView;
    @Bind(R.id.textUsersDetail)
    TextView textUsersDetail;
    @Bind(R.id.layoutBottom)
    LinearLayout layoutBottom;

    private AbTitleBar abTitleBar = null;
    private Commodity1 model = new Commodity1();
    //图片下载器
    private AbImageLoader abImageLoader = null;
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAbContentView(R.layout.activity_commodity_detail);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    public void initView() {
        type = getIntent().getIntExtra("type", 0);
        if (type==1){
            layoutBottom.setVisibility(View.GONE);
        }
        abTitleBar = this.getTitleBar();
        abTitleBar.setTitleText("商品详情");
        abTitleBar.setLogo(R.drawable.button_selector_back);
        abTitleBar.setTitleBarBackgroundColor(Color.parseColor("#FC5720"));
        abTitleBar.setTitleTextMargin(10, 0, 0, 0);
        abTitleBar.setLogoLine(R.drawable.line);
        model = getIntent().getParcelableExtra(App.MODEL_NAME);
        initData();
    }

    @Override
    public void initData() {
        layoutContext.setVisibility(View.GONE);
        layoutLoading.setVisibility(View.VISIBLE);
        layoutMsg.setVisibility(View.GONE);
        NetWorkWeb.getInstance().doRequest(Global.URL_COMMODITY_SERVLET, new ResultHandlerForJson<ResCommodity>(ResCommodity.class) {

            @Override
            public void onSuccess(int statusCode, Header[] headers, ResCommodity resultJson) {
                if (resultJson.errorcode == 0) {
                    NetWorkWeb.getInstance().doLogResultModelString("UsersServlet first", resultJson.data);
                    setValue(resultJson.data);
                } else {
                    layoutMsg.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String resultString, Throwable throwable) {
                T.showShort(CommodityDetailActivity.this,App.NET_OUT_MSG);
                layoutMsg.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFinish() {
                layoutLoading.setVisibility(View.GONE);
            }
        }, "{\"id\":" + model.id +"}", 6);

    }

    private void setValue(Commodity1 data) {
        layoutContext.setVisibility(View.VISIBLE);
        //图片下载器
        abImageLoader = AbImageLoader.getInstance(this);
        abImageLoader.setDesiredWidth(200);
        abImageLoader.setDesiredHeight(200);
        abImageLoader.setLoadingImage(R.drawable.image_loading);
        abImageLoader.setErrorImage(R.drawable.image_error);
        abImageLoader.setEmptyImage(R.drawable.image_empty);
        abImageLoader.display(imgView, progressBar, App.BASE_URL + model.commodityImageUrl);

        textCommodityName.setText(model.commodityName);
        textUsersDetail.setText(model.usersDetail);
        textCommodityDetail.setText(model.commodityDetail);
        textCommodityAddress.setText(model.commodityAddress);
        textCommodityPrice.setText(model.commodityPrice);
        textCommodityBargain.setText(model.commodityBargain == 0 ? "可刀" : "不刀");
        textCommodityAddTime.setText(AbDateUtil.formatDateStr2Desc(model.commodityAddTime, AbDateUtil.dateFormatYMDHM2));
        textCommodityViews.setText("浏览次数 " + String.valueOf(data.commodityViews));
        httpUpdateViews(data.commodityViews);
    }

    private void httpUpdateViews(final int views) {
        NetWorkWeb.getInstance().doRequest(Global.URL_COMMODITY_SERVLET, new ResultHandlerForJson<BaseResult>(BaseResult.class) {

            @Override
            public void onSuccess(int statusCode, Header[] headers, BaseResult resultJson) {
                if (resultJson.errorcode == 0) {
                    textCommodityViews.setText("浏览次数 " + String.valueOf(views + 1));
                }
            }
        }, "{\"id\":" + model.id + "}", 5);
    }

    @OnClick({R.id.btnContactTheSeller})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnContactTheSeller:
                if (SharedTool.getInt(App.SHARED_ID) <= 0) {
                    AbDialogUtil.showAlertDialog(this, "提示", "登录后可以查看联系方式，现在登录？", new AbAlertDialogFragment.AbDialogOnClickListener() {
                        @Override
                        public void onPositiveClick() {
                            startActivity(new Intent(CommodityDetailActivity.this, LoginActivity.class)
                                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                            finish();
                        }

                        @Override
                        public void onNegativeClick() {
                        }
                    });
                } else {
                    LayoutInflater layoutInflater = LayoutInflater.from(this);
                    View view1 = layoutInflater.inflate(R.layout.dialog_contact_the_seller, null);
                    Button btnQQ = ButterKnife.findById(view1, R.id.btnQQ);
                    Button btnPhone = ButterKnife.findById(view1, R.id.btnPhone);
                    Button btnMessage = ButterKnife.findById(view1, R.id.btnMessage);
                    Button btnEsc = ButterKnife.findById(view1, R.id.btnEsc);
                    btnEsc.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AbDialogUtil.removeDialog(CommodityDetailActivity.this);
                        }
                    });
                    btnQQ.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //复制短信号码至粘贴板
                            ClipboardManager cmb = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                            cmb.setText(String.valueOf(model.commodityQQ));
                            T.showLong(CommodityDetailActivity.this,"已复制QQ号码至粘贴板");
                        }
                    });
                    btnPhone.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AbDialogUtil.showAlertDialog(CommodityDetailActivity.this, "确认呼叫", String.valueOf(model.commodityPhone)
                                    , new AbAlertDialogFragment.AbDialogOnClickListener() {
                                @Override
                                public void onPositiveClick() {
                                    startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + String.valueOf(model.commodityPhone))));
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
                            startActivity(new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + String.valueOf(model.commodityPhone))));
                        }
                    });
                    AbDialogUtil.showDialog(view1, Gravity.BOTTOM);
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (NetWorkWeb.getInstance().getHttpClient()==null){
//            AbLogUtil.d("cancelAllRequests", " is null");
            return;
        }else {
            NetWorkWeb.getInstance().getHttpClient().cancelAllRequests(true);//关闭所有请求
//            AbLogUtil.d("cancelAllRequests"," is ok");
        }
    }

}

