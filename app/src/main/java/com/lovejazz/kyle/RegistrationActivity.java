package com.lovejazz.kyle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.Objects;

public class RegistrationActivity extends AppCompatActivity {

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

    }

    //Arrow button is clicked
    public void onArrowButtonClicked(View view) {
        Intent backIntent = new Intent(RegistrationActivity.this, MainRegisterActivity.class);
        startActivity(backIntent);
    }

    //login in existed account text clicked
    public void onLoginTextClicked(View view) {
        Intent loginIntent = new Intent(RegistrationActivity.this,LoginActivity.class);
        startActivity(loginIntent);
    }
}
