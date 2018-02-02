package com.nenton.testresult.data.managers;


import android.util.Log;

import com.nenton.testresult.data.network.RestCallTransformer;
import com.nenton.testresult.data.network.RestService;
import com.nenton.testresult.data.network.res.Stocks;
import com.nenton.testresult.di.DaggerService;
import com.nenton.testresult.di.components.DaggerDataManagerComponent;
import com.nenton.testresult.di.components.DataManagerComponent;
import com.nenton.testresult.di.modules.NetworkModule;
import com.nenton.testresult.utils.App;
import com.nenton.testresult.utils.AppConfig;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import retrofit2.Retrofit;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DataManager {

    private static final String TAG = "DataManager";
    private static DataManager ourInstance;
    private final RestCallTransformer mRestCallTransformer;

    @Inject
    RestService mRestService;
    @Inject
    Retrofit mRetrofit;

    public RestService getRestService() {
        return mRestService;
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }

    public static DataManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new DataManager();
        }

        return ourInstance;
    }

    private DataManager() {
        DataManagerComponent component = DaggerService.getComponent(DataManagerComponent.class);
        if (component == null) {
            component = DaggerDataManagerComponent.builder()
                    .appComponent(App.getAppComponent())
                    .networkModule(new NetworkModule())
                    .build();
            DaggerService.registerComponent(DataManagerComponent.class, component);
        }
        component.inject(this);
        mRestCallTransformer = new RestCallTransformer<>();
    }

    public Observable<List<Stocks.Stock>> updateInfoCurrencies() {
        return Observable.interval(0, 15, TimeUnit.SECONDS, Schedulers.io())
                .flatMap(aLong -> mRestService.getStocksObs()
                        .compose(((RestCallTransformer<Stocks>) mRestCallTransformer))
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .flatMap(stocks -> Observable.just(stocks.getStock()))
                        .retryWhen(errorObservable ->
                                errorObservable
                                        .zipWith(Observable.range(1, AppConfig.RETRY_REQUEST_COUNT), (throwable, retryCount) -> retryCount)
                                        .doOnNext(retryCount -> Log.e(TAG, "LOCAL UPDATE request retry count: " + retryCount + " " + new Date()))
                                        .map(retryCount -> ((long) (AppConfig.RETRY_REQUEST_BASE_DELAY * Math.pow(Math.E, retryCount))))
                                        .doOnNext(delay -> Log.e(TAG, "LOCAL UPDATE delay: " + delay))
                                        .flatMap(delay -> Observable.timer(delay, TimeUnit.MILLISECONDS))));
    }
}
