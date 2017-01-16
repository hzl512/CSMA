// Generated code from Butter Knife. Do not modify!
package com.weicent.android.csma.ui.add;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class BuysAddActivity$$ViewBinder<T extends com.weicent.android.csma.ui.add.BuysAddActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427408, "field 'textBuysName'");
    target.textBuysName = finder.castView(view, 2131427408, "field 'textBuysName'");
    view = finder.findRequiredView(source, 2131427410, "field 'textBuysPrice'");
    target.textBuysPrice = finder.castView(view, 2131427410, "field 'textBuysPrice'");
    view = finder.findRequiredView(source, 2131427411, "field 'textBuysAddress'");
    target.textBuysAddress = finder.castView(view, 2131427411, "field 'textBuysAddress'");
    view = finder.findRequiredView(source, 2131427409, "field 'textBuysDetail'");
    target.textBuysDetail = finder.castView(view, 2131427409, "field 'textBuysDetail'");
    view = finder.findRequiredView(source, 2131427412, "field 'textBuysPhone'");
    target.textBuysPhone = finder.castView(view, 2131427412, "field 'textBuysPhone'");
    view = finder.findRequiredView(source, 2131427413, "field 'textBuysQQ'");
    target.textBuysQQ = finder.castView(view, 2131427413, "field 'textBuysQQ'");
    view = finder.findRequiredView(source, 2131427414, "method 'onClick'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
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
    target.textBuysName = null;
    target.textBuysPrice = null;
    target.textBuysAddress = null;
    target.textBuysDetail = null;
    target.textBuysPhone = null;
    target.textBuysQQ = null;
  }
}
