package com.nenton.testresult.mvp.model;

import com.nenton.testresult.data.managers.DataManager;
import com.nenton.testresult.di.DaggerService;
import com.nenton.testresult.di.components.DaggerModelComponent;
import com.nenton.testresult.di.components.ModelComponent;
import com.nenton.testresult.di.modules.ModelModule;

import javax.inject.Inject;

public abstract class AbstractModel {
    @Inject
    DataManager mDataManager;

    public AbstractModel() {
        ModelComponent component = DaggerService.getComponent(ModelComponent.class);
        if (component == null){
            component = createDaggerModelComponent();
            DaggerService.registerComponent(ModelComponent.class, component);
        }
        component.inject(this);
    }

    private ModelComponent createDaggerModelComponent() {
        return DaggerModelComponent.builder()
                .modelModule(new ModelModule())
                .build();
    }
}
