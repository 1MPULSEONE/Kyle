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

    static boolean isValidEmail(String text) {
        String textRegex = "[^@ \\t\\r\\n]+@[^@ \\t\\r\\n]+\\.[^@ \\t\\r\\n]+";
        Pattern pat = Pattern.compile(textRegex);
        if (text == null)
            return false;
        return pat.matcher(text).matches();
    }

    static boolean checkEmailLength(EditText emailEntry) {
        return emailEntry.getText().toString().length() > 254 || emailEntry.getText().
                toString().length() < 3;
    }


    static boolean checkPasswordLength(EditText passwordEntry) {
        return passwordEntry.getText().toString().length() > 18 || passwordEntry.getText().
                toString().length() < 8;
    }

}
