package com.nenton.testresult.mvp.views;

import com.nenton.testresult.mvp.presenters.MenuItemHolder;

import java.util.List;

public interface IActionBarView {
    void setTitle(CharSequence title);
    void setMenuItem(List<MenuItemHolder> items);
}
