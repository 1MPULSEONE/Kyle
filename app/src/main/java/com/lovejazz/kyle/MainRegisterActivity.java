package com.lovejazz.kyle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainRegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e){
            Log.d("MainRegisterActivity","Toolbar produced null pointer exception");
        }
        setContentView(R.layout.activity_main_register);
    }

    //If login button is clicked
    public void onLoginButtonClicked(View view) {

    }

    //If register button is clicked
    public void onRegisterButtonClicked(View view) {

    }

    //If user want to use app without registration
    public void onTextAccountClicked (View view) {
        Intent intent = new Intent(MainRegisterActivity.this,MainActivity.class);
        startActivity(intent);
    }
}