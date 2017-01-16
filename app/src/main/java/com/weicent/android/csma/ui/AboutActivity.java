package com.weicent.android.csma.ui;

import android.graphics.Color;
import android.os.Bundle;

import com.ab.activity.AbActivity;
import com.ab.view.titlebar.AbTitleBar;
import com.weicent.android.csma.R;

/**
 * 关于
 */
public class AboutActivity extends AbActivity {

    private AbTitleBar abTitleBar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAbContentView(R.layout.activity_about);
        abTitleBar = this.getTitleBar();
        abTitleBar.setTitleText("关于我们");
        abTitleBar.setLogo(R.drawable.button_selector_back);
        abTitleBar.setTitleBarBackgroundColor(Color.parseColor("#FC5720"));
        abTitleBar.setTitleTextMargin(10, 0, 0, 0);
        abTitleBar.setLogoLine(R.drawable.line);
    }

}
