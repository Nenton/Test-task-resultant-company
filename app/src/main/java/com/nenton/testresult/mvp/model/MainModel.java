package com.nenton.testresult.mvp.model;

import com.nenton.testresult.data.storage.realm.CurrencyRealm;

import java.util.List;

import rx.Observable;

public class MainModel extends AbstractModel{
    public Observable<Void> updateInfo() {
        return mDataManager.updateInfoCurrencies();
    }

    public Observable<List<CurrencyRealm>> takeInfo() {
        return mDataManager.getRealmManager().getInfoAboutCurrencies();
    }
}
