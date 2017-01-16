// Generated code from Butter Knife. Do not modify!
package com.weicent.android.csma.ui.add;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class CommodityAddActivity$$ViewBinder<T extends com.weicent.android.csma.ui.add.CommodityAddActivity> implements ViewBinder<T> {
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
    view = finder.findRequiredView(source, 2131427424, "field 'textCategoryID'");
    target.textCategoryID = finder.castView(view, 2131427424, "field 'textCategoryID'");
    view = finder.findRequiredView(source, 2131427425, "field 'spinnerBargain'");
    target.spinnerBargain = finder.castView(view, 2131427425, "field 'spinnerBargain'");
    view = finder.findRequiredView(source, 2131427426, "field 'textCommodityPhone'");
    target.textCommodityPhone = finder.castView(view, 2131427426, "field 'textCommodityPhone'");
    view = finder.findRequiredView(source, 2131427427, "field 'textCommodityQQ'");
    target.textCommodityQQ = finder.castView(view, 2131427427, "field 'textCommodityQQ'");
    view = finder.findRequiredView(source, 2131427419, "field 'imgView'");
    target.imgView = finder.castView(view, 2131427419, "field 'imgView'");
    view = finder.findRequiredView(source, 2131427415, "method 'onClick'");
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
    target.textCategoryID = null;
    target.spinnerBargain = null;
    target.textCommodityPhone = null;
    target.textCommodityQQ = null;
    target.imgView = null;
  }
}
