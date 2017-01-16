package com.weicent.android.csma.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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
import com.weicent.android.csma.data.model.result.Departments;
import com.weicent.android.csma.data.model.result.Profession;
import com.weicent.android.csma.support.MyStringTool;
import com.weicent.android.csma.support.T;
import com.weicent.android.csma.ui.list.DepartmentsListActivity;
import com.weicent.android.csma.ui.list.ProfessionListActivity;

import org.apache.http.Header;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 注册
 */
public class SignActivity extends AbActivity implements IBinDing {

    @Bind(R.id.textDepartmentsID)
    TextView textDepartmentsID;
    @Bind(R.id.textProfessionID)
    TextView textProfessionID;
    @Bind(R.id.textUsersName)
    EditText textUsersName;
    @Bind(R.id.textUsersPwd)
    EditText textUsersPwd;
    @Bind(R.id.textUsersRePwd)
    EditText textUsersRePwd;
    @Bind(R.id.textUsersNickName)
    EditText textUsersNickName;
    @Bind(R.id.textUsersPhone)
    EditText textUsersPhone;
    @Bind(R.id.textUsersQQ)
    EditText textUsersQQ;
    @Bind(R.id.textUsersGrade)
    EditText textUsersGrade;

    private AbTitleBar abTitleBar = null;
    private Departments departments = new Departments();
    private Profession profession = new Profession();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAbContentView(R.layout.activity_sign);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    public void initView() {
        abTitleBar = this.getTitleBar();
        initData();
    }

    @Override
    public void initData() {
        abTitleBar.setTitleText("注册");
        abTitleBar.setLogo(R.drawable.button_selector_back);
        abTitleBar.setTitleBarBackgroundColor(Color.parseColor("#FC5720"));
        abTitleBar.setTitleTextMargin(10, 0, 0, 0);
        abTitleBar.setLogoLine(R.drawable.line);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                if (data == null) {
                    return;
                } else {
                    departments = data.getParcelableExtra(App.MODEL_NAME);
                    textDepartmentsID.setText(departments.departmentsName);
                }
            }
        } else if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                if (data == null) {
                    return;
                } else {
                    profession = data.getParcelableExtra(App.MODEL_NAME);
                    textProfessionID.setText(profession.professionName);
                }
            }
        }
    }

    @OnClick({R.id.textDepartmentsID, R.id.textProfessionID, R.id.btnSure})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.textDepartmentsID://院系
                startActivityForResult(new Intent(SignActivity.this, DepartmentsListActivity.class), 0);
                break;
            case R.id.textProfessionID://专业
                if (MyStringTool.isNullToToast(SignActivity.this, textDepartmentsID.getText().toString(), "院系"))
                    return;
                startActivityForResult(new Intent(SignActivity.this, ProfessionListActivity.class)
                        .putExtra(App.MODEL_NAME, departments), 1);
                break;
            case R.id.btnSure://注册
                if (!textUsersPwd.getText().toString().equals(textUsersRePwd.getText().toString())) {
                    T.showShort(SignActivity.this, "两次密码不一致");
                    return;
                }
                if (MyStringTool.isNullToToast(SignActivity.this, textUsersName.getText().toString(), "用户账号")
                        || MyStringTool.isNullToToast(SignActivity.this, textUsersNickName.getText().toString(), "用户昵称")
                        || MyStringTool.isNullToToast(SignActivity.this, textUsersPhone.getText().toString(), "联系电话")
                        || MyStringTool.isNullToToast(SignActivity.this, textUsersPwd.getText().toString(), "用户密码")
                        || MyStringTool.isNullToToast(SignActivity.this, textUsersRePwd.getText().toString(), "密码确认")
                        || MyStringTool.isNullToToast(SignActivity.this, textUsersQQ.getText().toString(), "联系QQ")
                        || MyStringTool.isNullToToast(SignActivity.this, textUsersGrade.getText().toString(), "年级")
                        || MyStringTool.isNullToToast(SignActivity.this, textDepartmentsID.getText().toString(), "院系")
                        || MyStringTool.isNullToToast(SignActivity.this, textProfessionID.getText().toString(), "专业")
                        ) return;
                final AbLoadDialogFragment mDialogFragment = AbDialogUtil.showLoadDialog(this, R.drawable.ic_load, "请稍候...");
                mDialogFragment.setTextColor(R.color.black);
                mDialogFragment.setAbDialogOnLoadListener(new AbDialogFragment.AbDialogOnLoadListener() {
                    @Override
                    public void onLoad() {
                        NetWorkWeb.getInstance().doRequest(Global.URL_USERS_SERVLET, new ResultHandlerForJson<BaseResult>(BaseResult.class) {

                            @Override
                            public void onSuccess(int statusCode, Header[] headers, BaseResult resultJson) {
                                if (resultJson.errorcode == 0) {
                                    mDialogFragment.loadFinish();
                                    T.showShort(SignActivity.this, "注册成功！");
                                    startActivity(new Intent(App.getContext(), LoginActivity.class)
                                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                                } else {
                                    T.showShort(SignActivity.this, resultJson.errormsg);
                                }
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, String resultString, Throwable throwable) {
                                T.showShort(SignActivity.this, App.NET_OUT_MSG);
                            }

                            @Override
                            public void onFinish() {
                                mDialogFragment.loadFinish();
                            }
                        }, "{\"usersName\":\"" + textUsersName.getText().toString()
                                + "\",\"usersPwd\":\"" + textUsersPwd.getText().toString()
                                + "\",\"usersNickName\":\"" + textUsersNickName.getText().toString()
                                + "\",\"usersPhone\":\"" + textUsersPhone.getText().toString()
                                + "\",\"usersQQ\":\"" + textUsersQQ.getText().toString()
                                + "\",\"departmentsID\":" + departments.id + ",\"professionID\":" + profession.id
                                + ",\"usersGrade\":" + textUsersGrade.getText().toString() + "}", 1);
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
