package com.nenton.testresult.data.network.errors;

import retrofit2.Response;

public class ErrorUtils {
    public static ApiError parseError(Response<?> response) {
        return new ApiError(response.code());
    }
}
