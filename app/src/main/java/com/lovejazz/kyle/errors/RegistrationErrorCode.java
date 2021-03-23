package com.lovejazz.kyle.errors;

public enum RegistrationErrorCode {
    EMPTY_FIELDS("Заполните все поля!"),
    WRONG_PASSWORD("Пароль должен содержать от 8 до 18 символов!"),
    WRONG_LOGIN("Логин должен содержать от 5 до 12 символов!"),
    WRONG_EMAIL("Введите действительную почту!"),
    DIFFERENT_PASSWORDS("Пароли не совпадают!"),
    WRONG_PASSWORD_LENGTH("Адрес почты должен содержать от 3 до 254 символов!"),
    WRONG_RECORD_NAME("Название записи должно содержать до 30 симоволов!"),

    ;


    private final String errorMessage;

    RegistrationErrorCode (String message) {
        errorMessage = message;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
