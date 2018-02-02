package com.nenton.testresult.data.network.errors;

public class ApiError extends Throwable {
    private int statusCode;
    private String message;


    public ApiError(int statusCode) {
        super("Ошибка сервера " + statusCode);
        this.statusCode = statusCode;
        message = "Ошибка сервера " + statusCode;
    }

    public ApiError() {
        super("Неизвестная ошибка сервера");
        message = "Неизвестная ошибка сервера";
    }

    public ApiError(String message){
        super(message);
        this.message = message;
    }
}
