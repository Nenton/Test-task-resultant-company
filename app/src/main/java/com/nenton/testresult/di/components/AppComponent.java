package com.nenton.testresult.di.components;

import android.content.Context;

import com.nenton.testresult.di.modules.AppModule;

import dagger.Component;

@Component(modules = AppModule.class)
public interface AppComponent {
    Context getContext();
}
