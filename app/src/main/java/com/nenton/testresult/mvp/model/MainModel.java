package com.nenton.testresult.mvp.model;

import com.nenton.testresult.data.network.res.Stocks;

import java.util.List;

import rx.Observable;

public class MainModel extends AbstractModel{

    public Observable<List<Stocks.Stock>> updateInfo() {
        return mDataManager.updateInfoCurrencies();
    }
}
