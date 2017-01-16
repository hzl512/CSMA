// Generated code from Butter Knife. Do not modify!
package com.weicent.android.csma.ui.detail;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class UsersDetailActivity$$ViewBinder<T extends com.weicent.android.csma.ui.detail.UsersDetailActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427493, "field 'layoutLoading'");
    target.layoutLoading = finder.castView(view, 2131427493, "field 'layoutLoading'");
    view = finder.findRequiredView(source, 2131427494, "field 'layoutMsg'");
    target.layoutMsg = finder.castView(view, 2131427494, "field 'layoutMsg'");
    view = finder.findRequiredView(source, 2131427416, "field 'layoutContext'");
    target.layoutContext = finder.castView(view, 2131427416, "field 'layoutContext'");
    view = finder.findRequiredView(source, 2131427449, "field 'textUsersName'");
    target.textUsersName = finder.castView(view, 2131427449, "field 'textUsersName'");
    view = finder.findRequiredView(source, 2131427456, "field 'textUsersAddTime'");
    target.textUsersAddTime = finder.castView(view, 2131427456, "field 'textUsersAddTime'");
    view = finder.findRequiredView(source, 2131427452, "field 'textUsersNickName'");
    target.textUsersNickName = finder.castView(view, 2131427452, "field 'textUsersNickName'");
    view = finder.findRequiredView(source, 2131427453, "field 'textUsersPhone'");
    target.textUsersPhone = finder.castView(view, 2131427453, "field 'textUsersPhone'");
    view = finder.findRequiredView(source, 2131427454, "field 'textUsersQQ'");
    target.textUsersQQ = finder.castView(view, 2131427454, "field 'textUsersQQ'");
    view = finder.findRequiredView(source, 2131427447, "field 'textDepartmentsID'");
    target.textDepartmentsID = finder.castView(view, 2131427447, "field 'textDepartmentsID'");
    view = finder.findRequiredView(source, 2131427448, "field 'textProfessionID'");
    target.textProfessionID = finder.castView(view, 2131427448, "field 'textProfessionID'");
    view = finder.findRequiredView(source, 2131427455, "field 'textUsersGrade'");
    target.textUsersGrade = finder.castView(view, 2131427455, "field 'textUsersGrade'");
  }

  @Override public void unbind(T target) {
    target.layoutLoading = null;
    target.layoutMsg = null;
    target.layoutContext = null;
    target.textUsersName = null;
    target.textUsersAddTime = null;
    target.textUsersNickName = null;
    target.textUsersPhone = null;
    target.textUsersQQ = null;
    target.textDepartmentsID = null;
    target.textProfessionID = null;
    target.textUsersGrade = null;
  }
}
