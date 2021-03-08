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

import static com.lovejazz.kyle.EntryUtils.checkEmailLength;
import static com.lovejazz.kyle.EntryUtils.checkPasswordLength;
import static com.lovejazz.kyle.EntryUtils.isValidEmail;
import static com.lovejazz.kyle.EntryUtils.isValidPassword;
import static com.lovejazz.kyle.EntryUtils.makeSnackbarError;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private LoadingDialog loadingDialog;
    protected EditText emailEntry;
    protected EditText passwordEntry;
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
        emailEntry = findViewById(R.id.email_entry);
        passwordEntry = findViewById(R.id.password_entry);
        fstore = FirebaseFirestore.getInstance();
        //initializing loading dialog
        loadingDialog = new LoadingDialog(LoginActivity.this);
        if (allFieldsAreNotFilled(emailEntry, passwordEntry)) {
            makeSnackbarError(activityView, getString(R.string.error_empty_fields));
        } else if (!isValidEmail(emailEntry.getText().toString())) {
            makeSnackbarError(activityView, getString(R.string.
                    email_error_contains_not_valid_symbols)
            );
        } else if (!isValidPassword(passwordEntry.getText().toString())) {
            makeSnackbarError(activityView, getString(R.string.not_valid_password));
        } else if (checkEmailLength(emailEntry)) {
            makeSnackbarError(activityView, getString(R.string.error_email_length));
        } else if (checkPasswordLength(passwordEntry)) {
            makeSnackbarError(activityView, getString(R.string.error_password_length));
        } else {
            loadingDialog.startLoadingDialog();
            Log.d(TAG, "Loading dialog started");
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
