package com.nenton.testresult.di.modules;

import com.nenton.testresult.di.sqopes.RootScope;
import com.nenton.testresult.mvp.presenters.RootPresenter;

import dagger.Provides;

@dagger.Module
public class RootModule {
    @Provides
    @RootScope
    public RootPresenter provideRootPresenter() {
        return new RootPresenter();
    }
}
