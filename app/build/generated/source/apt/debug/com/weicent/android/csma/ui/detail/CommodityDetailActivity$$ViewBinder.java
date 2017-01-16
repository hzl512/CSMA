// Generated code from Butter Knife. Do not modify!
package com.weicent.android.csma.ui.detail;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class CommodityDetailActivity$$ViewBinder<T extends com.weicent.android.csma.ui.detail.CommodityDetailActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427420, "field 'textCommodityName'");
    target.textCommodityName = finder.castView(view, 2131427420, "field 'textCommodityName'");
    view = finder.findRequiredView(source, 2131427421, "field 'textCommodityDetail'");
    target.textCommodityDetail = finder.castView(view, 2131427421, "field 'textCommodityDetail'");
    view = finder.findRequiredView(source, 2131427422, "field 'textCommodityAddress'");
    target.textCommodityAddress = finder.castView(view, 2131427422, "field 'textCommodityAddress'");
    view = finder.findRequiredView(source, 2131427423, "field 'textCommodityPrice'");
    target.textCommodityPrice = finder.castView(view, 2131427423, "field 'textCommodityPrice'");
    view = finder.findRequiredView(source, 2131427432, "field 'textCommodityBargain'");
    target.textCommodityBargain = finder.castView(view, 2131427432, "field 'textCommodityBargain'");
    view = finder.findRequiredView(source, 2131427430, "field 'textCommodityAddTime'");
    target.textCommodityAddTime = finder.castView(view, 2131427430, "field 'textCommodityAddTime'");
    view = finder.findRequiredView(source, 2131427429, "field 'textCommodityViews'");
    target.textCommodityViews = finder.castView(view, 2131427429, "field 'textCommodityViews'");
    view = finder.findRequiredView(source, 2131427493, "field 'layoutLoading'");
    target.layoutLoading = finder.castView(view, 2131427493, "field 'layoutLoading'");
    view = finder.findRequiredView(source, 2131427494, "field 'layoutMsg'");
    target.layoutMsg = finder.castView(view, 2131427494, "field 'layoutMsg'");
    view = finder.findRequiredView(source, 2131427416, "field 'layoutContext'");
    target.layoutContext = finder.castView(view, 2131427416, "field 'layoutContext'");
    view = finder.findRequiredView(source, 2131427428, "field 'progressBar'");
    target.progressBar = finder.castView(view, 2131427428, "field 'progressBar'");
    view = finder.findRequiredView(source, 2131427419, "field 'imgView'");
    target.imgView = finder.castView(view, 2131427419, "field 'imgView'");
    view = finder.findRequiredView(source, 2131427431, "field 'textUsersDetail'");
    target.textUsersDetail = finder.castView(view, 2131427431, "field 'textUsersDetail'");
    view = finder.findRequiredView(source, 2131427433, "field 'layoutBottom'");
    target.layoutBottom = finder.castView(view, 2131427433, "field 'layoutBottom'");
    view = finder.findRequiredView(source, 2131427434, "method 'onClick'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
  }

  @Override public void unbind(T target) {
    target.textCommodityName = null;
    target.textCommodityDetail = null;
    target.textCommodityAddress = null;
    target.textCommodityPrice = null;
    target.textCommodityBargain = null;
    target.textCommodityAddTime = null;
    target.textCommodityViews = null;
    target.layoutLoading = null;
    target.layoutMsg = null;
    target.layoutContext = null;
    target.progressBar = null;
    target.imgView = null;
    target.textUsersDetail = null;
    target.layoutBottom = null;
  }
}
