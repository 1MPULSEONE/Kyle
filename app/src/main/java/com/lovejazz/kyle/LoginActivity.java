package com.lovejazz.kyle;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.lovejazz.kyle.errors.RegistrationException;

import static com.lovejazz.kyle.EntryUtils.checkAllFieldsAreFilled;
import static com.lovejazz.kyle.EntryUtils.loginValidateEntries;
import static com.lovejazz.kyle.EntryUtils.makeSnackbarError;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private LoadingDialog loadingDialog;
    private String userEmail;
    private String userPassword;
    FirebaseAuth mAuth;
    FirebaseFirestore fstore;
    private View activityView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }

    public void onArrowButtonClicked(View view) {
        Intent loginIntent = new Intent(LoginActivity.this,
                MainRegisterActivity.class);
        startActivity(loginIntent);
    }


    public void onRegisterTextClicked(View view) {
        Intent registerIntent = new Intent(LoginActivity.this,
                RegistrationActivity.class);
        startActivity(registerIntent);
    }

    public void onLoginBtnClicked(View view) {
        activityView = view;
        //Remembering all login fields
        EditText emailEntry = findViewById(R.id.email_entry);
        EditText passwordEntry = findViewById(R.id.password_entry);
        userEmail = emailEntry.getText().toString();
        userPassword = passwordEntry.getText().toString();
        fstore = FirebaseFirestore.getInstance();
        //initializing loading dialog
        loadingDialog = new LoadingDialog(LoginActivity.this);
        try {
            loginValidateEntries(userEmail, userPassword);

            loadingDialog.startLoadingDialog();
            Log.d(TAG, "Loading dialog started");
            singIn(userEmail, userPassword);
        } catch (RegistrationException ex) {
            makeSnackbarError(activityView, ex.getErrorCode().getErrorMessage());
        }

//        if (checkAllFieldsAreFilled(userEmail, userPassword)) {
//            makeSnackbarError(activityView, getString(R.string.error_empty_fields));
//        } else if (!validateEmail(emailEntry.getText().toString())) {
//            makeSnackbarError(activityView, getString(R.string.
//                    email_error_contains_not_valid_symbols)
//            );
//        } else if (!isValidPassword(passwordEntry.getText().toString())) {
//            makeSnackbarError(activityView, getString(R.string.not_valid_password));
//        } else if (checkEmailLength(userEmail)) {
//            makeSnackbarError(activityView, getString(R.string.error_email_length));
//        } else if (checkPasswordLength(userPassword)) {
//            makeSnackbarError(activityView, getString(R.string.error_password_length));
//        } else {
//
//        }
    }

    private void singIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).
                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            makeSnackbarError(activityView, getString(
                                    R.string.incorrect_email_or_password_entered));
                            Log.d(TAG, "Some problem occurred");
                        } else {
                            Log.d(TAG, "Sing in is successful!");
                            Intent intentToHome = new Intent(LoginActivity.this
                                    , MainActivity.class);
                            startActivity(intentToHome);
                        }
                        loadingDialog.dismissDialog();
                        Log.d(TAG, "Loading dialog dismissed");
                    }
                });

    }
}
