package com.nenton.testresult.data.managers;

import com.nenton.testresult.data.network.res.Stocks;
import com.nenton.testresult.data.storage.realm.CurrencyRealm;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;

public class RealmManager {

    private Realm mRealmInstance;

    private Realm getQueryRealmInstance() {
        if (mRealmInstance == null || mRealmInstance.isClosed()) {
            mRealmInstance = Realm.getDefaultInstance();
        }
        return mRealmInstance;
    }

    public void updateOrInsertInfoCurrencies(Stocks.Stock stock) {
        CurrencyRealm currencyRealm = new CurrencyRealm(stock);
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> realm1.insertOrUpdate(currencyRealm));
        realm.close();
    }

    public Observable<List<CurrencyRealm>> getInfoAboutCurrencies() {
        return getQueryRealmInstance()
                .where(CurrencyRealm.class)
                .findAllAsync()
                .sort("mName")
                .asObservable()
                .filter(RealmResults::isLoaded)
                .flatMap(currencyRealms -> Observable.just(currencyRealms.subList(0, currencyRealms.size())));
    }
}
