package com.lovejazz.kyle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class MainRegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

//    @Override
//    protected void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if (currentUser != null) {
//            Intent intentToHome = new Intent(MainRegisterActivity.this,MainActivity.class);
//            startActivity(intentToHome);
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_register);
        //Deleting toolbar
        try {
            Objects.requireNonNull(this.getSupportActionBar()).hide();
        } catch (NullPointerException e) {
            Log.d("MainRegisterActivity", "Toolbar produced null pointer exception");
        }
        mAuth = FirebaseAuth.getInstance();
    }

    //Login button is clicked
    public void onLoginButtonClicked(View view) {
        Intent loginIntent = new Intent(MainRegisterActivity.this, LoginActivity.class);
        startActivity(loginIntent);
    }

    //Register button is clicked
    public void onRegisterButtonClicked(View view) {
        Intent registrationIntent = new Intent(MainRegisterActivity.this, RegistrationActivity.class);
        startActivity(registrationIntent);
    }


    //User want to use app without registration
    public void onTextAccountClicked(View view) {
        Intent intent = new Intent(MainRegisterActivity.this, MainActivity.class);
        startActivity(intent);
    }

}