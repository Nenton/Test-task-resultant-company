package com.nenton.testresult.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import rx.Observable;

public class NetworkStatusChecker {

    public static Boolean isNetworkAvailible(){
        ConnectivityManager manager = (ConnectivityManager) App.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    public static Observable<Boolean> isInternetAvailible(){
        return Observable.just(isNetworkAvailible());
    }
}