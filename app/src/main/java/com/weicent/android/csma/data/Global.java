package com.weicent.android.csma.data;


import com.weicent.android.csma.R;

/**
 * 全局数据
 */
public class Global {

    private static Global mContext;

    private Global() {
    }
    //单例实现
    public static Global getInstance() {
        if (mContext == null)
            mContext = new Global();
        return mContext;
    }

    //商品添加URL
    public static final String URL_COMMODITY_ADD_SERVLET="CommodityAddServlet";
    //商品URL
    public static final String URL_COMMODITY_SERVLET="CommodityServlet";
    //用户URL
    public static final String URL_USERS_SERVLET="UsersServlet";
    //求购URL
    public static final String URL_BUYS_SERVLET="BuysServlet";
    //分类URL
    public static final String URL_CATEGORY_SERVLET="CategoryServlet";
    //院系URL
    public static final String URL_DEPARTMENTS_SERVLET="DepartmentsServlet";
    //专业URL
    public static final String URL_PROFESSION_SERVLET="ProfessionServlet";

    public int getCategoryView(int type){
        switch (type){
            case 1:
                return R.drawable.icon_xydb;
            case 2:
                return R.drawable.icon_sj;
            case 3:
                return R.drawable.icon_dn;
            case 4:
                return R.drawable.icon_smpj;
            case 5:
                return R.drawable.icon_sm;
            case 6:
                return R.drawable.icon_dq;
            case 7:
                return R.drawable.icon_ydjs;
            case 8:
                return R.drawable.icon_yfsm;
            case 9:
                return R.drawable.icon_tsjc;
            case 10:
                return R.drawable.icon_zl;
            case 11:
                return R.drawable.icon_shyl;
            case 12:
                return R.drawable.icon_qt;
        }
        return R.drawable.icon_qt;
    }
    
}
