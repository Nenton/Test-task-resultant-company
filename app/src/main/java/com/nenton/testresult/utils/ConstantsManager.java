package com.nenton.testresult.utils;

/**
 * Created by serge on 21.01.2017.
 */

public interface ConstantsManager {

    String USER_AUTH_TOKEN = "USER_AUTH_TOKEN";

    int REQUEST_PERMISSION_READ_EXTERNAL_STORAGE = 3001;
    int REQUEST_PROFILE_PHOTO_PICTURE = 1001;

    String LAST_MODIFIED_HEADER = "Last-Modified";
    String REG_EXP_EMAIL = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    String REG_EXP_PASSWORD_HARD = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{8,}";
    String REG_EXP_PASSWORD_SIMPLE = "[0-9a-zA-Z]{8,}";
    String REG_EXP_NAME = "([a-zA-Zа-яА-Я]){3,}";
    String REG_EXP_NAME_ALBUM = "([a-zA-Zа-я А-Я0-9]){3,}";
    String REG_EXP_LOGIN = "(?=.*[A-Za-z])[a-zA-Z0-9_]{3,}";
}
