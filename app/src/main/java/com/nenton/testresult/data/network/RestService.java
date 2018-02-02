package com.nenton.testresult.data.network;

import com.nenton.testresult.data.network.res.Stocks;

import retrofit2.Response;
import retrofit2.http.GET;

import rx.Observable;


public interface RestService {

    @GET("stocks.json")
    Observable<Response<Stocks>> getStocksObs();
}
