package com.lovejazz.kyle;

import android.util.Log;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.lovejazz.kyle.errors.RegistrationErrorCode;
import com.lovejazz.kyle.errors.RegistrationException;

import java.util.regex.Pattern;

public abstract class EntryUtils {

    private static final String TAG = "Entries";

    public static void makeSnackbarError(View view, String error) {
        Snackbar
                .make(
                        view,
                        error,
                        Snackbar.LENGTH_LONG
                )
                .setTextColor(view.getContext().getResources().getColor(R.color.full_white))
                .setBackgroundTint(view.getContext().getResources().getColor(R.color.red))
                .show();
    }

    public static void registrationValidateEntries(String userLogin, String userEmail,
                                                   String userPassword, String userRepeatedPassword) throws RegistrationException {
        Log.d(TAG, "start validating");
        checkAllFieldsAreFilled(userLogin, userEmail, userPassword, userRepeatedPassword);
        Log.d(TAG, "entries not empty");
        validateLogin(userLogin);
        Log.d(TAG, "login is valid");
        validateEmail(userEmail);
        Log.d(TAG, "email is valid");
        validatePassword(userPassword);
        Log.d(TAG, "password is valid");
        checkIfPasswordEqual(userPassword, userRepeatedPassword);
        Log.d(TAG, "password is equals");

    }

    public static void loginValidateEntries(String userEmail, String userPassword)
            throws RegistrationException {
        validateEmail(userEmail);
        validatePassword(userPassword);
        checkPasswordLength(userPassword);
        checkEmailLength(userEmail);

    }

    public static void createNotationValidateEntries(String recordEmail, String recordName, String recordPassword) throws RegistrationException {
        validateEmail(recordEmail);
        isValidName(recordName);
    }

    static void validatePassword(String text) throws RegistrationException {
        String textRegex = "^(?=.*?[A-Z]).{8,18}$";
        Pattern pat = Pattern.compile(textRegex);
        if (text == null || !pat.matcher(text).matches()) {
            throw new RegistrationException(RegistrationErrorCode.WRONG_PASSWORD);
        }
    }

    static void validateLogin(String text) throws RegistrationException {
        String textRegex = "^[a-zA-Z0-9]{5,12}$";
        Pattern pat = Pattern.compile(textRegex);
        if (text == null || !pat.matcher(text).matches()) {
            throw new RegistrationException(RegistrationErrorCode.WRONG_LOGIN);

        }
    }

    static void isValidName(String text) throws RegistrationException {
        if (text.length() > 30) {
            throw new RegistrationException(RegistrationErrorCode.WRONG_RECORD_NAME);
        }
    }

    static void validateEmail(String text) throws RegistrationException {
        String textRegex = "[^@ \\t\\r\\n]+@[^@ \\t\\r\\n]+\\.[^@ \\t\\r\\n]+";
        Pattern pat = Pattern.compile(textRegex);
        if (text == null || !pat.matcher(text).matches()) {
            throw new RegistrationException(RegistrationErrorCode.WRONG_EMAIL);

        }
    }

    static boolean isValidEmail(String email) {
        String textRegex = "[^@ \\t\\r\\n]+@[^@ \\t\\r\\n]+\\.[^@ \\t\\r\\n]+";
        Pattern pat = Pattern.compile(textRegex);
        return pat.matcher(email).matches();
    }

    static void checkEmailLength(String email) throws RegistrationException {
        if (email.length() > 254 || email.length() < 3) {
            throw new RegistrationException(RegistrationErrorCode.WRONG_PASSWORD_LENGTH);
        }
    }


    static void checkPasswordLength(String password) throws RegistrationException {

        if (password.length() >= 18 || password.length() <= 8) {
            throw new RegistrationException(RegistrationErrorCode.WRONG_PASSWORD);
        }

    }

    static boolean checkAllFieldsAreFilled(String recordName, String recordEmail, String
            recordPassword) {
        return (recordName.equals("") || recordEmail.equals("") || recordPassword.equals(""));
    }

    static boolean checkAllFieldsAreFilled(String email, String password) {
        return (email.equals("") || password.equals(""));
    }

    static void checkAllFieldsAreFilled(String userLogin, String userEmail, String
            userPassword, String userPasswordRepeatEntry) throws RegistrationException {

        if (userLogin.isEmpty() || userEmail.isEmpty() || userPassword.isEmpty() ||
                userPasswordRepeatEntry.isEmpty()) {
            throw new RegistrationException(RegistrationErrorCode.EMPTY_FIELDS);

        }
    }

    static void checkIfPasswordEqual(String userPassword, String userPasswordRepeated)
            throws RegistrationException {
        if (!userPassword.equals(userPasswordRepeated)) {
            throw new RegistrationException(RegistrationErrorCode.DIFFERENT_PASSWORDS);
        }
    }

    static void checkLoginLength(String userLogin) throws RegistrationException {
        if (userLogin.length() > 12 || userLogin.length() < 5) {
            throw new RegistrationException(RegistrationErrorCode.WRONG_LOGIN);
        }
    }

}
