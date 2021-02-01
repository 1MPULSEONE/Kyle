package com.lovejazz.kyle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseFirestore fstore;
    String userID;
    private EditText loginEntry;
    private EditText emailEntry;
    private EditText passwordEntry;
    private LoadingDialog loadingDialog;

    private static final String TAG = "RegistrationActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        //Deleting toolbar
        try {
            Objects.requireNonNull(this.getSupportActionBar()).hide();
        } catch (NullPointerException e) {
            Log.d("RegistrationActivity", "Toolbar produced null pointer exception");
        }
        //Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }

    //Registration button is clicked
    public void onRegistrationButtonClicked(View view) {
        //Remembering all registration fields
        loginEntry = findViewById(R.id.login_entry);
        emailEntry = findViewById(R.id.email_entry);
        passwordEntry = findViewById(R.id.password_entry);

        EditText repeatPasswordEntry = findViewById(R.id.repeat_password_entry);

        //initializing loading dialog
        loadingDialog = new LoadingDialog(RegistrationActivity.this);
        fstore = FirebaseFirestore.getInstance();
        //Checking if empty fields exist
        if (checkAllFieldsAreFilled(loginEntry, emailEntry, passwordEntry, repeatPasswordEntry)) {
            makeSnackbarError(getString(R.string.error_empty_fields));
            //Checking if password equals repeated password
        } else if (checkIfPasswordEqual(passwordEntry, repeatPasswordEntry)) {
            makeSnackbarError(getString(R.string.error_passwords_dont_equal));
            //Checking if login length is okay
        } else if (checkLoginLength(loginEntry)) {
            makeSnackbarError(getString(R.string.error_login_length));
            //Checking if password length is okay
        } else if (checkPasswordLength(passwordEntry)) {
            makeSnackbarError(getString(R.string.error_password_length));
            //Checking if email length is okay
        } else if (checkEmailLength(emailEntry)) {
            makeSnackbarError(getString(R.string.error_email_length));
            //Checking if text in fields are valid
        } else if (checkEmailValid(emailEntry)) {
            makeSnackbarError(getString(R.string.error_contains_not_valid_symbols));
        } else if (checkLoginValid(loginEntry)) {
            makeSnackbarError(getString(R.string.email_error_contains_not_valid_symbols));
        } else if (checkPasswordValid(passwordEntry)) {
            makeSnackbarError(getString(R.string.not_valid_password));
        } else {
            fstore.collection("users")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull final Task<QuerySnapshot> task) {
                            createUser(task);
                        }
                    });
        }
    }

    public void onArrowButtonClicked(View view) {
        Intent backIntent = new Intent(RegistrationActivity.this, MainRegisterActivity.class);
        startActivity(backIntent);
    }

    //login in existed account text clicked
    public void onLoginTextClicked(View view) {
        Intent loginIntent = new Intent(RegistrationActivity.this, LoginActivity.class);
        startActivity(loginIntent);
    }

    private void makeSnackbarError(String error) {
        Snackbar
                .make(
                        findViewById(R.id.activity_registration),
                        error,
                        Snackbar.LENGTH_LONG
                )
                .setTextColor(getResources().getColor(R.color.full_white))
                .setBackgroundTint(getResources().getColor(R.color.red))
                .show();
    }

    private boolean checkAllFieldsAreFilled(EditText loginEntry, EditText emailEntry, EditText
            passwordEntry, EditText passwordRepeatEntry) {
        return (loginEntry.getText().toString().equals("") || emailEntry.getText().toString().
                equals("") || passwordEntry.getText().toString().equals("") || passwordRepeatEntry
                .getText().toString().equals(""));
    }


    private boolean checkIfPasswordEqual(EditText passwordEntry, EditText passwordRepeatEntry) {
        return !passwordEntry.getText().toString().equals(passwordRepeatEntry.getText().
                toString());
    }

    private boolean checkLoginLength(EditText loginEntry) {
        return loginEntry.getText().toString().length() > 12 || loginEntry.getText().toString()
                .length() < 5;
    }

    private boolean checkPasswordLength(EditText passwordEntry) {
        return passwordEntry.getText().toString().length() > 18 || passwordEntry.getText().
                toString().length() < 8;
    }

    private boolean checkEmailLength(EditText emailEntry) {
        return emailEntry.getText().toString().length() > 254 || emailEntry.getText().
                toString().length() < 3;
    }

    private boolean checkEmailValid(EditText emailEntry) {
        return !isValidEmail(emailEntry.getText().toString());
    }

    private boolean checkLoginValid(EditText loginEntry) {
        return !isValidLogin(loginEntry.getText().toString());
    }

    private boolean checkPasswordValid(EditText passwordEntry) {
        return !isValidPassword(passwordEntry.getText().toString());
    }

    //Checking if user`s entry text is valid
    private static boolean isValidEmail(String text) {
        String textRegex = "[^@ \\t\\r\\n]+@[^@ \\t\\r\\n]+\\.[^@ \\t\\r\\n]+";
        Pattern pat = Pattern.compile(textRegex);
        if (text == null)
            return false;
        return pat.matcher(text).matches();
    }

    //Checking if user`s entry text is valid
    private static boolean isValidLogin(String text) {
        String textRegex = "^[a-zA-Z0-9]{5,12}$";
        Pattern pat = Pattern.compile(textRegex);
        if (text == null)
            return false;
        return pat.matcher(text).matches();
    }

    //Checking if user`s entry text is valid
    private static boolean isValidPassword(String text) {
        String textRegex = "^(?=.*?[A-Z])(?=.*?[a-z]).{8,18}$";
        Pattern pat = Pattern.compile(textRegex);
        if (text == null)
            return false;
        return pat.matcher(text).matches();
    }

    private void createUser(@NonNull Task<QuerySnapshot> task) {
        loadingDialog.startLoadingDialog();
        Log.d(TAG, "Loading dialog started");
        if (task.isSuccessful()) {
            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                if (emailEntry.getText().toString().equals(document.getString("email"))) {
                    Snackbar
                            .make(
                                    findViewById(R.id.activity_registration),
                                    getString(R.string
                                            .user_with_this_email_already_exist)
                                    ,
                                    Snackbar.LENGTH_LONG
                            )
                            .setTextColor(getResources().getColor(R.color.
                                    full_white))
                            .setBackgroundTint(getResources().getColor(R.color
                                    .red))
                            .show();
                    Log.d(TAG, "User with email already exist");
                } else if (loginEntry.getText().toString().equals(document.
                        getString("nickname"))) {
                    Snackbar
                            .make(
                                    findViewById(R.id.activity_registration),
                                    getString(R.string
                                            .user_with_this_login_already_exist)
                                    ,
                                    Snackbar.LENGTH_LONG
                            )
                            .setTextColor(getResources().getColor(R.color.
                                    full_white))
                            .setBackgroundTint(getResources().getColor(R.color
                                    .red))
                            .show();
                    Log.d(TAG, "User with nickname already");
                } else {
                    mAuth.createUserWithEmailAndPassword(emailEntry.getText().toString(), passwordEntry
                            .getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                userID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                                DocumentReference documentReference = fstore.collection("users")
                                        .document(userID);
                                Map<String, Object> user = new HashMap<>();
                                user.put("nickname", loginEntry.getText().toString());
                                user.put("email", emailEntry.getText().toString());
                                user.put("id", userID);
                                loadingDialog.dismissDialog();
                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("Registration", "Document is created");
                                        Intent intent = new Intent(RegistrationActivity.this,
                                                MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }

                                })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                loadingDialog.dismissDialog();
                                                Snackbar
                                                        .make(
                                                                findViewById(R.id.activity_registration),
                                                                getString(R.string.some_problem_occurred),
                                                                Snackbar.LENGTH_LONG
                                                        )
                                                        .setTextColor(getResources().getColor(R.color.white))
                                                        .setBackgroundTint(getResources().getColor(R.color.red))
                                                        .show();
                                                Log.d("Registration", "Document isn`t created");
                                            }
                                        });
                            } else {
                                Log.d("Registration", Objects.requireNonNull(task.getException()).toString());
                            }
                            loadingDialog.dismissDialog();
                        }
                    });
                }
            }
        }

        Log.d(TAG, "LoadingDialog dismissed");
    }
}
