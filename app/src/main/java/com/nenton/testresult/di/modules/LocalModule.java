package com.nenton.testresult.di.modules;

import android.content.Context;

import com.facebook.stetho.Stetho;
import com.nenton.testresult.data.managers.PreferencesManager;
import com.nenton.testresult.data.managers.RealmManager;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class LocalModule{

    @Provides
    @Singleton
    PreferencesManager provideAppPrefManager(Context context){
        return new PreferencesManager(context);
    }

    @Provides
    @Singleton
    RealmManager provideRealmManager(Context context){
        Stetho.initialize(Stetho.newInitializerBuilder(context)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(context))
                .enableWebKitInspector(RealmInspectorModulesProvider.builder(context).build())
                .build());
        return new RealmManager();
    }

}
