package com.weicent.android.csma.ui.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ab.fragment.AbAlertDialogFragment;
import com.ab.util.AbDialogUtil;
import com.weicent.android.csma.App;
import com.weicent.android.csma.R;
import com.weicent.android.csma.data.NetWorkWeb;
import com.weicent.android.csma.data.model.result.Users;
import com.weicent.android.csma.support.SharedTool;
import com.weicent.android.csma.support.T;
import com.weicent.android.csma.ui.AboutActivity;
import com.weicent.android.csma.ui.LoginActivity;
import com.weicent.android.csma.ui.detail.UsersDetailActivity;
import com.weicent.android.csma.ui.list.BuysListActivity;
import com.weicent.android.csma.ui.list.CommodityList1Activity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我
 */
public class UsersFragment extends Fragment {

    @Bind(R.id.textTitle)
    TextView textTitle;
    @Bind(R.id.textRight)
    TextView textRight;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_users, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        if (SharedTool.getInt(App.SHARED_ID) <= 0) {
            startActivity(new Intent(getActivity(), LoginActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        }
    }

    private void initView() {
        textRight.setVisibility(View.GONE);
        textTitle.setText("我");
    }

    //操作事件
    @OnClick({R.id.btnPersonalData, R.id.btnAbout, R.id.btnLoginOut, R.id.btnCommodity, R.id.btnBuys})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnPersonalData://个人资料
                startActivity(new Intent(getActivity(), UsersDetailActivity.class));
                break;
            case R.id.btnAbout://关于
                startActivity(new Intent(getActivity(), AboutActivity.class));
                break;
            case R.id.btnCommodity://我的商品
                startActivity(new Intent(getActivity(), CommodityList1Activity.class).putExtra("type", 1));
                break;
            case R.id.btnBuys://我的求购
                startActivity(new Intent(getActivity(), BuysListActivity.class));
                break;
            case R.id.btnLoginOut://注销
                AbDialogUtil.showAlertDialog(getActivity(), "提示", "确定要注销当前账号吗？"
                        , new AbAlertDialogFragment.AbDialogOnClickListener() {
                    @Override
                    public void onPositiveClick() {
                        new Users().setClearAll();
                        T.showShort(getActivity(), "注销成功！");
                        startActivity(new Intent(getActivity(), LoginActivity.class)
                                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                        getActivity().finish();
                    }

                    @Override
                    public void onNegativeClick() {
                    }
                });
                break;
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

