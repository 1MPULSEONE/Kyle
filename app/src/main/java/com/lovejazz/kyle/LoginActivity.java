package com.lovejazz.kyle;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class LoginActivity extends AuthenticationSystem {

    private final int activityID = R.id.login_activity;

    private static final String TAG = "LoginActivity";
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Deleting toolbar
        try {
            Objects.requireNonNull(this.getSupportActionBar()).hide();
        } catch (NullPointerException e) {
            Log.d(TAG, "Toolbar produced null pointer exception");
        }
        //Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }

    public void onArrowButtonClicked(View view) {
        Intent loginIntent = new Intent(LoginActivity.this, MainRegisterActivity.class);
        startActivity(loginIntent);
    }


    public void onRegisterTextClicked(View view) {
        Intent registerIntent = new Intent(LoginActivity.this, RegistrationActivity.class);
        startActivity(registerIntent);
    }

    public void onLoginBtnClicked(View view) {
        //Remembering all login fields
        emailEntry = findViewById(R.id.email_entry);
        passwordEntry = findViewById(R.id.password_entry);
        fstore = FirebaseFirestore.getInstance();
        //initializing loading dialog
        loadingDialog = new LoadingDialog(LoginActivity.this);
        if (allFieldsAreNotFilled(emailEntry, passwordEntry)) {
            makeSnackbarError(getString(R.string.error_empty_fields), activityID);
        } else if (!isValidEmail(emailEntry.getText().toString())) {
            makeSnackbarError(getString(R.string.email_error_contains_not_valid_symbols)
                    , activityID);
        } else if (!isValidPassword(passwordEntry.getText().toString())) {
            makeSnackbarError(getString(R.string.not_valid_password),
                    activityID);
        } else if (checkEmailLength(emailEntry)) {
            makeSnackbarError(getString(R.string.error_email_length), activityID);
        } else if (checkPasswordLength(passwordEntry)) {
            makeSnackbarError(getString(R.string.error_password_length), activityID);
        } else {
            loadingDialog.startLoadingDialog();
            Log.d(TAG,"Loading dialog started");
            singIn(emailEntry.getText().toString(), passwordEntry.getText()
                    .toString());
        }
    }

    private boolean allFieldsAreNotFilled(EditText emailEntry, EditText passwordEntry) {
        return emailEntry.getText().toString().equals("") || passwordEntry.getText().toString()
                .equals("");
    }

    private void singIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).
                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            makeSnackbarError(getString(R.string.incorrect_email_or_password_entered),
                                    activityID);
                            Log.d(TAG, "Some problem occurred");
                        } else {
                            Log.d(TAG, "Sing in is successful!");
                            Intent intentToHome = new Intent(LoginActivity.this
                                    , MainActivity.class);
                            startActivity(intentToHome);
                        }
                        loadingDialog.dismissDialog();
                        Log.d(TAG,"Loading dialog dismissed");
                    }
                });

    }
}
