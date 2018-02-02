package com.nenton.testresult.data.storage.realm;

import com.nenton.testresult.data.network.res.Stocks;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class CurrencyRealm extends RealmObject {

    @PrimaryKey
    private String mName;

    private int mVolume;
    private float mAmount;

    public CurrencyRealm() {
    }

    public CurrencyRealm(String name, int volume, float amount) {
        this.mName = name;
        this.mVolume = volume;
        this.mAmount = amount;
    }

    public CurrencyRealm(Stocks.Stock stock) {
        this.mName = stock.getName();
        this.mVolume = stock.getVolume();
        this.mAmount = stock.getPrice().getAmount();
    }

    public String getName() {
        return mName;
    }

    public int getVolume() {
        return mVolume;
    }

    public float getAmount() {
        return mAmount;
    }
}
