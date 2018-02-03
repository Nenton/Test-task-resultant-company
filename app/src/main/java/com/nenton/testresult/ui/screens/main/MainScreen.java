package com.nenton.testresult.ui.screens.main;

import android.os.Bundle;
import android.util.Log;

import com.nenton.testresult.R;
import com.nenton.testresult.data.network.errors.NetworkAvailableError;
import com.nenton.testresult.data.network.res.Stocks;
import com.nenton.testresult.di.DaggerService;
import com.nenton.testresult.di.sqopes.DaggerScope;
import com.nenton.testresult.flow.AbstractScreen;
import com.nenton.testresult.flow.Screen;
import com.nenton.testresult.mvp.model.MainModel;
import com.nenton.testresult.mvp.presenters.AbstractPresenter;
import com.nenton.testresult.mvp.presenters.IMainPresenter;
import com.nenton.testresult.mvp.presenters.MenuItemHolder;
import com.nenton.testresult.mvp.presenters.RootPresenter;
import com.nenton.testresult.ui.activities.RootActivity;
import com.nenton.testresult.utils.NetworkStatusChecker;

import java.util.List;
import java.util.concurrent.TimeUnit;

import dagger.Provides;
import mortar.MortarScope;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;

@Screen(R.layout.screen_main)
public class MainScreen extends AbstractScreen<RootActivity.RootComponent> {

    @Override
    public Object createScreenComponent(RootActivity.RootComponent parentComponent) {
        return DaggerMainScreen_Component.builder()
                .rootComponent(parentComponent)
                .module(new Module())
                .build();
    }

    @dagger.Module
    public class Module {
        @Provides
        @DaggerScope(MainScreen.class)
        MainModel provideMainModel() {
            return new MainModel();
        }

        @Provides
        @DaggerScope(MainScreen.class)
        MainPresenter provideMainPresenter() {
            return new MainPresenter();
        }
    }

    @dagger.Component(dependencies = RootActivity.RootComponent.class, modules = Module.class)
    @DaggerScope(MainScreen.class)
    public interface Component {
        void inject(MainPresenter presenter);

        void inject(MainView view);

        RootPresenter getRootPresenter();
    }

    public class MainPresenter extends AbstractPresenter<MainView, MainModel> implements IMainPresenter {

        private Subscription subscribe;

        @Override
        protected void initActionBar() {
            mRootPresenter.newActionBarBuilder()
                    .setTitle("Валюты")
                    .addAction(new MenuItemHolder("Refresh", R.drawable.ic_refresh_black_24dp, item -> {
                        refreshData();
                        return true;
                    }))
                    .build();
        }

        @Override
        protected void initDagger(MortarScope scope) {
            ((Component) scope.getService(DaggerService.SERVICE_NAME)).inject(this);
        }

        @Override
        public void refreshData() {
            if (subscribe != null) {
                mCompSubs.remove(subscribe);
            }
            subscribe = mModel.updateInfo().subscribe(new MainSubscriber());
            mCompSubs.add(subscribe);
        }

        @Override
        protected void onLoad(Bundle savedInstanceState) {
            super.onLoad(savedInstanceState);
            if (getRootView() != null && getView() != null) {
                getView().initView();
                refreshData();
            }
        }

        public void clickOnRefresh() {
            refreshData();
        }

        private class MainSubscriber extends Subscriber<List<Stocks.Stock>> {
            MainAdapter mAdapter = getView().getAdapter();

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (getRootView() != null) {
                    getRootView().showError(e);
                    getRootView().hideLoad();
                }
            }

            @Override
            public void onNext(List<Stocks.Stock> listCurrencies) {
                mAdapter.updateListCurrencies(listCurrencies);
                if (getRootView() != null) {
                    getRootView().hideLoad();
                    getView().hideRefreshButton();
                }
            }

            @Override
            public void onStart() {
                super.onStart();
                if (NetworkStatusChecker.isNetworkAvailible()){
                    getRootView().showLoad();
                    getView().hideRefreshButton();
                } else {
                    getView().showRefreshButton();
                    onError(new NetworkAvailableError());
                }
            }
        }
    }
}
