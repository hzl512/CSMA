package com.weicent.android.csma.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.ab.activity.AbActivity;
import com.ab.fragment.AbDialogFragment;
import com.ab.fragment.AbLoadDialogFragment;
import com.ab.util.AbDialogUtil;
import com.ab.util.AbStrUtil;
import com.weicent.android.csma.R;
import com.weicent.android.csma.data.Global;
import com.weicent.android.csma.data.NetWorkWeb;
import com.weicent.android.csma.data.ResultHandlerForJson;
import com.weicent.android.csma.data.model.result.Users;
import com.weicent.android.csma.data.result.model.ResUsers;
import com.weicent.android.csma.support.T;

import org.apache.http.Header;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 登录
 */
public class LoginActivity extends AbActivity {

    @Bind(R.id.textName)
    EditText textName;
    @Bind(R.id.textPwd)
    EditText textPwd;
    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    //操作事件
    @OnClick({R.id.btnSign, R.id.btnLogin})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSign://注册
                sign();
                break;
            case R.id.btnLogin://登录
                login();
                break;
        }
    }

    //注册
    private void sign() {
        startActivity(new Intent(this, SignActivity.class));
    }

    //登录
    private void login() {
        final AbLoadDialogFragment dialogFragment = AbDialogUtil.showLoadDialog(this, R.drawable.ic_load, "正在登录,请稍候...");
        dialogFragment.setTextColor(R.color.black);
        dialogFragment.setAbDialogOnLoadListener(new AbDialogFragment.AbDialogOnLoadListener() {
            @Override
            public void onLoad() {
                NetWorkWeb.getInstance().doRequest(Global.URL_USERS_SERVLET, new ResultHandlerForJson<ResUsers>(ResUsers.class) {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, ResUsers resultJson) {
                        dialogFragment.loadFinish();
                        if (resultJson.errorcode == 0) {
                            NetWorkWeb.getInstance().doLogResultModelString("MineServlet first", resultJson.data);
                            if (resultJson.data != null && !AbStrUtil.isEmpty(resultJson.data.usersName)) {
                                Users model = new Users();
                                model.setAll(resultJson.data.usersName, resultJson.data.usersPwd, resultJson.data.id);
                                if (model != null) {
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    LoginActivity.this.finish();
                                }
                            } else {
                                T.showShort(LoginActivity.this, "登录失败，请重试");
                            }

                        } else {
                            T.showShort(LoginActivity.this, resultJson.errormsg);
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String resultString, Throwable throwable) {
                        T.showShort(LoginActivity.this, "登录失败，请重试");
                    }

                    @Override
                    public void onFinish() {
                        dialogFragment.loadFinish();
                    }
                }, "{\"usersName\":\"" + textName.getText().toString().trim() + "\"" +
                        ",\"usersPwd\":\"" + textPwd.getText().toString().trim() + "\"}", 5);
            }
        });
    }

    /**
     * 按键点击事件处理
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                T.showLong(LoginActivity.this, "再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                this.finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
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
