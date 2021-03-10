package com.lovejazz.kyle;

import android.view.View;
import android.widget.EditText;


import com.google.android.material.snackbar.Snackbar;

import java.util.regex.Pattern;

public abstract class EntryUtils {

    static void makeSnackbarError(View view, String error) {
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

    static boolean isValidPassword(String text) {
        String textRegex = "^(?=.*?[A-Z]).{8,18}$";
        Pattern pat = Pattern.compile(textRegex);
        if (text == null)
            return false;
        return pat.matcher(text).matches();
    }

    static boolean isValidLogin(String text) {
        String textRegex = "^[a-zA-Z0-9]{5,12}$";
        Pattern pat = Pattern.compile(textRegex);
        if (text == null)
            return false;
        return pat.matcher(text).matches();
    }

    static boolean isValidName(String text) {
        return (text.length() < 30);
    }

    static boolean isValidEmail(String text) {
        String textRegex = "[^@ \\t\\r\\n]+@[^@ \\t\\r\\n]+\\.[^@ \\t\\r\\n]+";
        Pattern pat = Pattern.compile(textRegex);
        if (text == null)
            return false;
        return pat.matcher(text).matches();
    }

    static boolean checkEmailLength(String email) {
        return email.length() > 254 || email.length() < 3;
    }


    static boolean checkPasswordLength(String password) {
        return password.length() > 18 || password.length() < 8;
    }

    static boolean checkAllFieldsAreFilled(String recordName, String recordEmail, String
            recordPassword) {
        return (recordName.equals("") || recordEmail.equals("") || recordPassword.equals(""));
    }

    static boolean checkAllFieldsAreFilled(String email, String password) {
        return (email.equals("") || password.equals(""));
    }

    static boolean checkAllFieldsAreFilled(String userLogin, String userEmail, String
            userPassword, String userPasswordRepeatEntry) {
        return (userLogin.equals("") || userEmail.equals("") || userPassword.equals("") ||
                userPasswordRepeatEntry.equals(""));
    }

    static boolean checkIfPasswordEqual(String userPassword, String userPasswordRepeated) {
        return userPassword.equals(userPasswordRepeated);
    }

    static boolean checkLoginLength(String userLogin) {
        return userLogin.length() > 12 || userLogin.length() < 5;
    }

}
