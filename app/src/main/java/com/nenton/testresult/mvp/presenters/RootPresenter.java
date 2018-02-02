package com.nenton.testresult.mvp.presenters;

import android.support.annotation.Nullable;


import com.nenton.testresult.ui.activities.RootActivity;
import com.nenton.testresult.di.DaggerService;
import com.nenton.testresult.mvp.views.IRootView;

import java.util.ArrayList;
import java.util.List;

import mortar.MortarScope;
import mortar.Presenter;
import mortar.bundler.BundleService;

public class RootPresenter extends Presenter<IRootView> {

    @Override
    protected BundleService extractBundleService(IRootView view) {
        return BundleService.getBundleService((RootActivity) view);
    }

    @Override
    protected void onEnterScope(MortarScope scope) {
        super.onEnterScope(scope);
        ((RootActivity.RootComponent) scope.getService(DaggerService.SERVICE_NAME)).inject(this);
    }

    @Nullable
    public IRootView getRootView() {
        return getView();
    }

    public ActionBarBuilder newActionBarBuilder() {
        return this.new ActionBarBuilder();
    }


    public class ActionBarBuilder {

        private CharSequence title;
        private List<MenuItemHolder> items = new ArrayList<>();

        public ActionBarBuilder addAction(MenuItemHolder menuItem) {
            this.items.add(menuItem);
            return this;
        }

        public ActionBarBuilder setTitle(CharSequence title) {
            this.title = title;
            return this;
        }

        public void build() {
            if (getRootView() != null) {
                RootActivity activity = (RootActivity) getRootView();
                activity.setTitle(title);
                activity.setMenuItem(items);
            }
        }
    }
}