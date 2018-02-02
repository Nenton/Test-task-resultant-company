package com.nenton.testresult.data.network;

import com.fernandocejas.frodo.annotation.RxLogObservable;
import com.nenton.testresult.data.network.errors.ErrorUtils;
import com.nenton.testresult.data.network.errors.NetworkAvailableError;
import com.nenton.testresult.utils.NetworkStatusChecker;

import retrofit2.Response;
import rx.Observable;

public class RestCallTransformer<R> implements Observable.Transformer<Response<R>, R> {

    @Override
    @RxLogObservable
    public Observable<R> call(Observable<Response<R>> responseObservable) {
        Observable<Boolean> networkStatus;

        networkStatus = NetworkStatusChecker.isInternetAvailible();
        return networkStatus
                .flatMap(aBoolean -> aBoolean ? responseObservable : Observable.error(new NetworkAvailableError()))
                .flatMap(rResponse -> {
                    switch (rResponse.code()) {
                        case 200:
                            return Observable.just(rResponse.body());
                        case 304:
                            return Observable.empty();
                        default:
                            return Observable.error(ErrorUtils.parseError(rResponse));
                    }
                });
    }
}