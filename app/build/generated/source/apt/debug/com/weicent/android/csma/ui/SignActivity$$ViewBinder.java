// Generated code from Butter Knife. Do not modify!
package com.weicent.android.csma.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class SignActivity$$ViewBinder<T extends com.weicent.android.csma.ui.SignActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427447, "field 'textDepartmentsID' and method 'onClick'");
    target.textDepartmentsID = finder.castView(view, 2131427447, "field 'textDepartmentsID'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131427448, "field 'textProfessionID' and method 'onClick'");
    target.textProfessionID = finder.castView(view, 2131427448, "field 'textProfessionID'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131427449, "field 'textUsersName'");
    target.textUsersName = finder.castView(view, 2131427449, "field 'textUsersName'");
    view = finder.findRequiredView(source, 2131427450, "field 'textUsersPwd'");
    target.textUsersPwd = finder.castView(view, 2131427450, "field 'textUsersPwd'");
    view = finder.findRequiredView(source, 2131427451, "field 'textUsersRePwd'");
    target.textUsersRePwd = finder.castView(view, 2131427451, "field 'textUsersRePwd'");
    view = finder.findRequiredView(source, 2131427452, "field 'textUsersNickName'");
    target.textUsersNickName = finder.castView(view, 2131427452, "field 'textUsersNickName'");
    view = finder.findRequiredView(source, 2131427453, "field 'textUsersPhone'");
    target.textUsersPhone = finder.castView(view, 2131427453, "field 'textUsersPhone'");
    view = finder.findRequiredView(source, 2131427454, "field 'textUsersQQ'");
    target.textUsersQQ = finder.castView(view, 2131427454, "field 'textUsersQQ'");
    view = finder.findRequiredView(source, 2131427455, "field 'textUsersGrade'");
    target.textUsersGrade = finder.castView(view, 2131427455, "field 'textUsersGrade'");
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
    target.textDepartmentsID = null;
    target.textProfessionID = null;
    target.textUsersName = null;
    target.textUsersPwd = null;
    target.textUsersRePwd = null;
    target.textUsersNickName = null;
    target.textUsersPhone = null;
    target.textUsersQQ = null;
    target.textUsersGrade = null;
  }
}
