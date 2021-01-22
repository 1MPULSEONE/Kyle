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
    private boolean userWithLoginOrEmailAlreadyExist;
    private EditText loginEntry;
    private EditText emailEntry;
    private EditText passwordEntry;

    public RegistrationActivity() {
    }

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
        userWithLoginOrEmailAlreadyExist = false;
        fstore = FirebaseFirestore.getInstance();
        //Checking if empty fields exist
        if (loginEntry.getText().toString().equals("") || emailEntry.getText().toString().
                equals("") || passwordEntry.getText().toString().equals("") || repeatPasswordEntry
                .getText().toString().equals("")) {
            Snackbar
                    .make(
                            findViewById(R.id.activity_registration),
                            getString(R.string.error_empty_fields),
                            Snackbar.LENGTH_LONG
                    )
                    .setTextColor(getResources().getColor(R.color.full_white))
                    .setBackgroundTint(getResources().getColor(R.color.red))
                    .show();
            //Checking if password equals repeated password
        } else if (!passwordEntry.getText().toString().equals(repeatPasswordEntry.getText().
                toString())) {
            Snackbar
                    .make(
                            findViewById(R.id.activity_registration),
                            getString(R.string.error_passwords_dont_equal),
                            Snackbar.LENGTH_LONG
                    )
                    .setTextColor(getResources().getColor(R.color.full_white))
                    .setBackgroundTint(getResources().getColor(R.color.red))
                    .show();
            //Checking if login length is okay
        } else if (loginEntry.getText().toString().length() > 12 || loginEntry.getText().toString()
                .length() < 5) {
            Snackbar
                    .make(
                            findViewById(R.id.activity_registration),
                            getString(R.string.error_login_length),
                            Snackbar.LENGTH_LONG
                    )
                    .setTextColor(getResources().getColor(R.color.full_white))
                    .setBackgroundTint(getResources().getColor(R.color.red))
                    .show();
            //Checking if password length is okay
        } else if (passwordEntry.getText().toString().length() > 18 || passwordEntry.getText().
                toString().length() < 8) {
            Snackbar
                    .make(
                            findViewById(R.id.activity_registration),
                            getString(R.string.error_password_length),
                            Snackbar.LENGTH_LONG
                    )
                    .setTextColor(getResources().getColor(R.color.full_white))
                    .setBackgroundTint(getResources().getColor(R.color.red))
                    .show();
            //Checking if email length is okay
        } else if (emailEntry.getText().toString().length() > 254 || emailEntry.getText().
                toString().length() < 3) {
            Snackbar
                    .make(
                            findViewById(R.id.activity_registration),
                            getString(R.string.error_password_length),
                            Snackbar.LENGTH_LONG
                    )
                    .setTextColor(getResources().getColor(R.color.full_white))
                    .setBackgroundTint(getResources().getColor(R.color.red))
                    .show();
            //Checking if text in fields are valid
        } else if (!isValidEmail(emailEntry.getText().toString())) {
            Snackbar
                    .make(
                            findViewById(R.id.activity_registration),
                            getString(R.string.email_error_contains_not_valid_symbols),
                            Snackbar.LENGTH_LONG
                    )
                    .setTextColor(getResources().getColor(R.color.full_white))
                    .setBackgroundTint(getResources().getColor(R.color.red))
                    .show();
        } else if (!isValidLogin(loginEntry.getText().toString())) {
            Snackbar
                    .make(
                            findViewById(R.id.activity_registration),
                            getString(R.string.error_contains_not_valid_symbols),
                            Snackbar.LENGTH_LONG
                    )
                    .setTextColor(getResources().getColor(R.color.full_white))
                    .setBackgroundTint(getResources().getColor(R.color.red))
                    .show();
        } else if (!isValidPassword(passwordEntry.getText().toString())) {
            Snackbar
                    .make(
                            findViewById(R.id.activity_registration),
                            getString(R.string.not_valid_password),
                            Snackbar.LENGTH_LONG
                    )
                    .setTextColor(getResources().getColor(R.color.full_white))
                    .setBackgroundTint(getResources().getColor(R.color.red))
                    .show();
        } else {
            fstore.collection("users")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            checkIfUserWithEmailOrLoginExist(task);
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
        String textRegex = "^[a-z0-9_-]{5,12}$";
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

    private void checkIfUserWithEmailOrLoginExist(@NonNull Task<QuerySnapshot> task) {
        if (task.isSuccessful()) {
            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                if (emailEntry.getText().toString().equals(document.getString(
                        "email"))) {
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
                } else {
                    mAuth.createUserWithEmailAndPassword(emailEntry.getText().toString(), passwordEntry
                            .getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                userID = mAuth.getCurrentUser().getUid();
                                DocumentReference documentReference = fstore.collection("users")
                                        .document(userID);
                                Map<String, Object> user = new HashMap<>();
                                user.put("nickname", loginEntry.getText().toString());
                                user.put("email", emailEntry.getText().toString());
                                user.put("id", userID);
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
                                Log.d("Registration", task.getException().toString());
                            }
                        }
                    });
                }
            }
        } else {
            Log.d("Registration", task.getException().toString());
        }
    }
}
