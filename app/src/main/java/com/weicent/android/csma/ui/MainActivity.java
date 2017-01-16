package com.weicent.android.csma.ui;

import android.app.Fragment;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;

import com.ab.activity.AbActivity;
import com.ab.view.sliding.AbBottomTabView;
import com.weicent.android.csma.R;
import com.weicent.android.csma.support.T;
import com.weicent.android.csma.ui.fragment.BuysFragment;
import com.weicent.android.csma.ui.fragment.CategoryFragment;
import com.weicent.android.csma.ui.fragment.CommodityFragment;
import com.weicent.android.csma.ui.fragment.UsersFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 主界面
 */
public class MainActivity extends AbActivity {

    @Bind(R.id.abBottomTabView)
    AbBottomTabView abBottomTabView;
    private List<Drawable> tabDrawables = null;
    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        List<Fragment> fragments = new ArrayList<>();

        fragments.add(new CommodityFragment());
        fragments.add(new CategoryFragment());
        fragments.add(new BuysFragment());
        fragments.add(new UsersFragment());
		
        List<String> tabTexts = new ArrayList<>();
        tabTexts.add("首页");
		tabTexts.add("分类");
        tabTexts.add("求购");
        tabTexts.add("我");
		        
        //缓存数量
        abBottomTabView.getViewPager().setOffscreenPageLimit(0);
        //设置样式
        abBottomTabView.setTabTextColor(Color.parseColor("#FC5720"));
        abBottomTabView.setTabSelectColor(Color.parseColor("#FC5720"));
        abBottomTabView.setTabBackgroundResource(R.drawable.tablayout_bg2);
        abBottomTabView.setTabLayoutBackgroundResource(R.drawable.tablayout_bg2);
        //注意图片的顺序
        tabDrawables = new ArrayList<>();
         
        tabDrawables.add(this.getResources().getDrawable(R.drawable.tab_home_normal));
        tabDrawables.add(this.getResources().getDrawable(R.drawable.tab_home_checked));
		 
        tabDrawables.add(this.getResources().getDrawable(R.drawable.tab_classify_normal));
        tabDrawables.add(this.getResources().getDrawable(R.drawable.tab_classify_checked));
		 
        tabDrawables.add(this.getResources().getDrawable(R.drawable.tab_find_normal));
        tabDrawables.add(this.getResources().getDrawable(R.drawable.tab_find_checked));

        tabDrawables.add(this.getResources().getDrawable(R.drawable.tab_profilo_normal));
        tabDrawables.add(this.getResources().getDrawable(R.drawable.tab_profilo_checked));
		 
        abBottomTabView.setTabCompoundDrawablesBounds(0, 0, 40, 40);
        //增加一组
        abBottomTabView.addItemViews(tabTexts, fragments, tabDrawables);
        abBottomTabView.setTabPadding(10, 10, 10, 10);
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
                T.showLong(MainActivity.this,"再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                this.finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}


