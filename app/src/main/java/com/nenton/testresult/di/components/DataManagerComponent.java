package com.nenton.testresult.di.components;

import com.nenton.testresult.data.managers.DataManager;
import com.nenton.testresult.di.modules.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;

@Component(dependencies = AppComponent.class, modules = NetworkModule.class)
@Singleton
public interface DataManagerComponent {
    void inject(DataManager dataManager);
}
