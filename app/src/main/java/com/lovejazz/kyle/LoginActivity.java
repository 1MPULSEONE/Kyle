package com.lovejazz.kyle;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); //Deleting toolbar
        try {
            Objects.requireNonNull(this.getSupportActionBar()).hide();
        } catch (NullPointerException e) {
            Log.d("MainRegisterActivity", "Toolbar produced null pointer exception");
        }

    }

    //Arrow button is clicked
    public void onArrowButtonClicked(View view) {
        Intent loginIntent = new Intent(LoginActivity.this, MainRegisterActivity.class);
        startActivity(loginIntent);
    }
    //Register text is clicked
    public void onRegisterTextClicked(View view){
        Intent registerIntent = new Intent(LoginActivity.this,RegistrationActivity.class);
        startActivity(registerIntent);
    }
}
