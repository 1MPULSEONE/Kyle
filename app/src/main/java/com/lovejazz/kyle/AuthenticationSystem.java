package com.lovejazz.kyle;

import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.util.regex.Pattern;

public abstract class AuthenticationSystem extends AppCompatActivity {

    void makeSnackbarError(String error,int activityId) {
        Snackbar
                .make(
                        findViewById(activityId),
                        error,
                        Snackbar.LENGTH_LONG
                )
                .setTextColor(getResources().getColor(R.color.full_white))
                .setBackgroundTint(getResources().getColor(R.color.red))
                .show();
    }

    boolean isValidPassword(String text) {
        String textRegex = "^(?=.*?[A-Z]).{8,18}$";
        Pattern pat = Pattern.compile(textRegex);
        if (text == null)
            return false;
        return pat.matcher(text).matches();
    }

    boolean isValidEmail(String text) {
        String textRegex = "[^@ \\t\\r\\n]+@[^@ \\t\\r\\n]+\\.[^@ \\t\\r\\n]+";
        Pattern pat = Pattern.compile(textRegex);
        if (text == null)
            return false;
        return pat.matcher(text).matches();
    }

    boolean checkEmailLength(EditText emailEntry) {
        return emailEntry.getText().toString().length() > 254 || emailEntry.getText().
                toString().length() < 3;
    }


    boolean checkPasswordLength(EditText passwordEntry) {
        return passwordEntry.getText().toString().length() > 18 || passwordEntry.getText().
                toString().length() < 8;
    }

}
