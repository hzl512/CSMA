// Generated code from Butter Knife. Do not modify!
package com.weicent.android.csma.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class LoginActivity$$ViewBinder<T extends com.weicent.android.csma.ui.LoginActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427442, "field 'textName'");
    target.textName = finder.castView(view, 2131427442, "field 'textName'");
    view = finder.findRequiredView(source, 2131427443, "field 'textPwd'");
    target.textPwd = finder.castView(view, 2131427443, "field 'textPwd'");
    view = finder.findRequiredView(source, 2131427444, "method 'onClick'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131427445, "method 'onClick'");
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
    target.textName = null;
    target.textPwd = null;
  }
}
