package com.weicent.android.csma.ui.add;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.ab.activity.AbActivity;
import com.ab.fragment.AbDialogFragment;
import com.ab.fragment.AbLoadDialogFragment;
import com.ab.util.AbDialogUtil;
import com.ab.view.titlebar.AbTitleBar;
import com.weicent.android.csma.App;
import com.weicent.android.csma.R;
import com.weicent.android.csma.data.BaseResult;
import com.weicent.android.csma.data.Global;
import com.weicent.android.csma.data.NetWorkWeb;
import com.weicent.android.csma.data.ResultHandlerForJson;
import com.weicent.android.csma.support.SharedTool;
import com.weicent.android.csma.support.T;

import org.apache.http.Header;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 添加求购
 */
public class BuysAddActivity extends AbActivity {

    @Bind(R.id.textBuysName)
    EditText textBuysName;
    @Bind(R.id.textBuysPrice)
    EditText textBuysPrice;
    @Bind(R.id.textBuysAddress)
    EditText textBuysAddress;
    @Bind(R.id.textBuysDetail)
    EditText textBuysDetail;
    @Bind(R.id.textBuysPhone)
    EditText textBuysPhone;
    @Bind(R.id.textBuysQQ)
    EditText textBuysQQ;

    private AbTitleBar abTitleBar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAbContentView(R.layout.activity_buys_add);
        ButterKnife.bind(this);
        initView();
    }

    public void initView() {
        abTitleBar = this.getTitleBar();
        abTitleBar.setTitleText("添加求购");
        abTitleBar.setLogo(R.drawable.button_selector_back);
        abTitleBar.setTitleBarBackgroundColor(Color.parseColor("#FC5720"));
        abTitleBar.setTitleTextMargin(10, 0, 0, 0);
        abTitleBar.setLogoLine(R.drawable.line);
    }

    @OnClick({R.id.btnEsc, R.id.btnSure})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnEsc://清空
                textBuysName.setText("");
                textBuysPrice.setText("");
                textBuysAddress.setText("");
                textBuysDetail.setText("");
                textBuysPhone.setText("");
                textBuysQQ.setText("");
                break;
            case R.id.btnSure://确定
                final AbLoadDialogFragment dialogFragment = AbDialogUtil.showLoadDialog(this, R.drawable.ic_load, "操作中...");
                dialogFragment.setTextColor(R.color.black);
                dialogFragment.setAbDialogOnLoadListener(new AbDialogFragment.AbDialogOnLoadListener() {
                    @Override
                    public void onLoad() {
                        NetWorkWeb.getInstance().doRequest(Global.URL_BUYS_SERVLET, new ResultHandlerForJson<BaseResult>(BaseResult.class) {

                            @Override
                            public void onSuccess(int statusCode, Header[] headers, BaseResult resultJson) {
                                if (resultJson.errorcode == 0) {
                                    dialogFragment.loadFinish();
                                    T.showShort(BuysAddActivity.this, "添加成功！");
                                    finish();
                                } else {
                                    T.showShort(BuysAddActivity.this, resultJson.errormsg);
                                }
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, String resultString, Throwable throwable) {
                                T.showShort(BuysAddActivity.this, App.REQUEST_OUT_MSG);
                            }

                            @Override
                            public void onFinish() {
                                dialogFragment.loadFinish();
                            }
                        }, "{\"usersID\":" + SharedTool.getInt(App.SHARED_ID) + ",\"buysName\":\"" + textBuysName.getText().toString().trim() + "\",\"buysPrice\":\"" + textBuysPrice.getText().toString().trim()
                                + "\",\"buysAddress\":\"" + textBuysAddress.getText().toString().trim() + "\",\"buysDetail\":\"" + textBuysDetail.getText().toString().trim()
                                + "\",\"buysPhone\":\"" + textBuysPhone.getText().toString().trim() + "\",\"buysQQ\":" + textBuysQQ.getText().toString().trim() + "}", 1);
                    }
                });
                break;
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

