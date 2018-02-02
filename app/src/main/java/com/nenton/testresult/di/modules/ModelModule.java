package com.nenton.testresult.di.modules;


import com.nenton.testresult.data.managers.DataManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ModelModule{
    @Provides
    @Singleton
    DataManager provideDataManager(){
        return DataManager.getInstance();
    }
}