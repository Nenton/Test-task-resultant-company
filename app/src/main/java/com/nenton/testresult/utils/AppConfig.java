package com.nenton.testresult.utils;

public interface AppConfig {
    String BASE_URL = "http://phisix-api3.appspot.com/";

    long MAX_CONNECTION_TIMEOUT = 5 * 1000;
    long MAX_READ_TIMEOUT = 5 * 1000;
    long MAX_WRITE_TIMEOUT = 5 * 1000;
    int RETRY_REQUEST_COUNT = 5;
    int RETRY_REQUEST_BASE_DELAY = 500;
}
