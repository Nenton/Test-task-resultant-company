package com.nenton.testresult.di.components;

import com.nenton.testresult.di.modules.ModelModule;
import com.nenton.testresult.mvp.model.AbstractModel;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = ModelModule.class)
@Singleton
public interface ModelComponent {
    void inject(AbstractModel abstractModel);
}
