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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.lovejazz.kyle.EntryUtils.checkAllFieldsAreFilled;
import static com.lovejazz.kyle.EntryUtils.checkEmailLength;
import static com.lovejazz.kyle.EntryUtils.checkIfPasswordEqual;
import static com.lovejazz.kyle.EntryUtils.checkLoginLength;
import static com.lovejazz.kyle.EntryUtils.checkPasswordLength;
import static com.lovejazz.kyle.EntryUtils.isValidEmail;
import static com.lovejazz.kyle.EntryUtils.isValidLogin;
import static com.lovejazz.kyle.EntryUtils.isValidPassword;
import static com.lovejazz.kyle.EntryUtils.makeSnackbarError;

//TODO Debug registration
//TODO Write documentation

public class RegistrationActivity extends AppCompatActivity {
    private String userID;
    private String userLogin;
    private String userEmail;
    private String userPassword;
    private LoadingDialog loadingDialog;

    FirebaseAuth mAuth;
    FirebaseFirestore fstore;
    private View activityView;

    private static final String TAG = "RegistrationActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        //Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }

    //Registration button is clicked
    public void onRegistrationButtonClicked(View view) {
        activityView = view;
        //Getting all strings from editTexts
        EditText loginEntry = findViewById(R.id.login_entry);
        EditText emailEntry = findViewById(R.id.email_entry);
        EditText passwordEntry = findViewById(R.id.password_entry);
        EditText repeatPasswordEntry = findViewById(R.id.repeat_password_entry);
        userLogin = loginEntry.getText().toString();
        userEmail = emailEntry.getText().toString();
        userPassword = passwordEntry.getText().toString();
        String userRepeatedPassword = repeatPasswordEntry.getText().toString();

        //Initializing loading dialog
        loadingDialog = new LoadingDialog(RegistrationActivity.this);
        fstore = FirebaseFirestore.getInstance();
        //Checking if empty fields exist
        if (checkAllFieldsAreFilled(userLogin, userEmail, userPassword, userRepeatedPassword)) {
            makeSnackbarError(activityView, getString(R.string.error_empty_fields));
            //Checking if password equals repeated password
        } else if (!checkIfPasswordEqual(userPassword, userRepeatedPassword)) {
            makeSnackbarError(activityView, getString(R.string.error_passwords_dont_equal));
            //Checking if login length is okay
        } else if (checkLoginLength(userEmail)) {
            makeSnackbarError(activityView, getString(R.string.error_login_length));
            //Checking if password length is okay
        } else if (checkPasswordLength(userPassword)) {
            makeSnackbarError(activityView, getString(R.string.error_password_length));
            //Checking if email length is okay
        } else if (checkEmailLength(userEmail)) {
            makeSnackbarError(activityView, getString(R.string.error_email_length));
            //Checking if text in fields are valid
        } else if (!isValidEmail(userEmail)) {
            makeSnackbarError(activityView, getString(R.string.error_contains_not_valid_symbols));
        } else if (!isValidLogin(userLogin)) {
            makeSnackbarError(activityView, getString(R.string.email_error_contains_not_valid_symbols));
        } else if (!isValidPassword(userPassword)) {
            makeSnackbarError(activityView, getString(R.string.not_valid_password));
        } else {
            loadingDialog.startLoadingDialog();
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
        Intent backIntent = new Intent(RegistrationActivity.this,
                MainRegisterActivity.class);
        startActivity(backIntent);
    }

    //Login in existed account text clicked
    public void onLoginTextClicked(View view) {
        Intent loginIntent = new Intent(RegistrationActivity.this,
                LoginActivity.class);
        startActivity(loginIntent);
    }

    private void createUser(@NonNull Task<QuerySnapshot> task) {
        Log.d(TAG, "Loading dialog started");
        if (task.isSuccessful()) {
            //TODO fix this, the cycle does not work correctly
            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                if (userEmail.equals(document.getString("email"))) {
                    makeSnackbarError(activityView, getString(R.string.user_with_this_email_already_exist));
                    Log.d(TAG, "User with email already exist");
                } else if (userLogin.equals(document.
                        getString("nickname"))) {
                    makeSnackbarError(activityView, getString(R.string.user_with_this_login_already_exist));
                    Log.d(TAG, "User with nickname already");
                } else {
                    mAuth.createUserWithEmailAndPassword(userEmail,
                            userPassword).addOnCompleteListener
                            (new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        userID = Objects.requireNonNull(mAuth.getCurrentUser())
                                                .getUid();
                                        DocumentReference documentReference = fstore.collection(
                                                "users")
                                                .document(userID);
                                        Map<String, Object> user = new HashMap<>();
                                        user.put("nickname", userLogin);
                                        user.put("email", userEmail);
                                        user.put("id", userID);
                                        loadingDialog.dismissDialog();
                                        documentReference.set(user).addOnSuccessListener(
                                                new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Log.d(TAG, "Document is created");
                                                        Intent intent = new Intent(
                                                                RegistrationActivity
                                                                        .this,
                                                                MainActivity.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }

                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        loadingDialog.dismissDialog();
                                                        makeSnackbarError(
                                                                activityView,
                                                                getString(R.string.some_problem_occurred));
                                                        Log.d(TAG, "Document isn`t created");
                                                    }
                                                });
                                    } else {
                                        Log.d(TAG, Objects.requireNonNull(task.getException())
                                                .toString());
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
