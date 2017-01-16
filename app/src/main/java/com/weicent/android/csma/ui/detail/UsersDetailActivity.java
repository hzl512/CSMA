package com.weicent.android.csma.ui.detail;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ab.activity.AbActivity;
import com.ab.view.titlebar.AbTitleBar;
import com.weicent.android.csma.App;
import com.weicent.android.csma.R;
import com.weicent.android.csma.data.Global;
import com.weicent.android.csma.data.NetWorkWeb;
import com.weicent.android.csma.data.ResultHandlerForJson;
import com.weicent.android.csma.data.model.result.Users1;
import com.weicent.android.csma.data.result.model.ResUsers1;
import com.weicent.android.csma.support.SharedTool;
import com.weicent.android.csma.support.T;
import com.weicent.android.csma.ui.IBinDing;

import org.apache.http.Header;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 个人资料
 */
public class UsersDetailActivity extends AbActivity implements IBinDing {

    @Bind(R.id.layoutLoading)
    RelativeLayout layoutLoading;
    @Bind(R.id.layoutMsg)
    RelativeLayout layoutMsg;
    @Bind(R.id.layoutContext)
    LinearLayout layoutContext;

    @Bind(R.id.textUsersName)
    TextView textUsersName;
    @Bind(R.id.textUsersAddTime)
    TextView textUsersAddTime;
    @Bind(R.id.textUsersNickName)
    TextView textUsersNickName;
    @Bind(R.id.textUsersPhone)
    TextView textUsersPhone;
    @Bind(R.id.textUsersQQ)
    TextView textUsersQQ;
    @Bind(R.id.textDepartmentsID)
    TextView textDepartmentsID;
    @Bind(R.id.textProfessionID)
    TextView textProfessionID;
    @Bind(R.id.textUsersGrade)
    TextView textUsersGrade;

    private AbTitleBar abTitleBar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAbContentView(R.layout.activity_users_detail);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    public void initView() {
        abTitleBar = this.getTitleBar();
        abTitleBar.setTitleText("个人资料");
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
        initData();
    }

    @Override
    public void initData() {
        layoutContext.setVisibility(View.GONE);
        layoutLoading.setVisibility(View.VISIBLE);
        layoutMsg.setVisibility(View.GONE);
        NetWorkWeb.getInstance().doRequest(Global.URL_USERS_SERVLET, new ResultHandlerForJson<ResUsers1>(ResUsers1.class) {

            @Override
            public void onSuccess(int statusCode, Header[] headers, ResUsers1 resultJson) {
                if (resultJson.errorcode == 0) {
                    NetWorkWeb.getInstance().doLogResultModelString("UsersServlet first", resultJson.data);
                    setValue(resultJson.data);
                } else {
                    layoutMsg.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String resultString, Throwable throwable) {
                T.showShort(UsersDetailActivity.this, App.NET_OUT_MSG);
                layoutMsg.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFinish() {
                layoutLoading.setVisibility(View.GONE);
            }
        }, "{\"usersName\":\"" + SharedTool.getString(App.SHARED_USERNAME) + "\"" +
                ",\"usersPwd\":\"" + SharedTool.getString(App.SHARED_PWD) + "\"}", 6);

    }

    private void setValue(Users1 model) {
        textUsersName.setText(model.usersName);
        textUsersAddTime.setText(model.usersAddTime);
        textUsersNickName.setText(model.usersNickName);
        textUsersPhone.setText(model.usersPhone);
        textUsersQQ.setText(model.usersQQ);
        textDepartmentsID.setText(model.departmentsID);
        textProfessionID.setText(model.professionID);
        textUsersGrade.setText(model.usersGrade);
        layoutContext.setVisibility(View.VISIBLE);
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


