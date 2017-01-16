// Generated code from Butter Knife. Do not modify!
package com.weicent.android.csma.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MainActivity$$ViewBinder<T extends com.weicent.android.csma.ui.MainActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427446, "field 'abBottomTabView'");
    target.abBottomTabView = finder.castView(view, 2131427446, "field 'abBottomTabView'");
  }

  @Override public void unbind(T target) {
    target.abBottomTabView = null;
  }
}
