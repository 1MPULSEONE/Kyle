package com.lovejazz.kyle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    //Creating variable for bottomNavigation
    private MenuItem prevMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Deleting toolbar
        try {
            Objects.requireNonNull(this.getSupportActionBar()).hide();
        } catch (NullPointerException e) {
            Log.d("MainRegisterActivity", "Toolbar produced null pointer exception");
        }
        setContentView(R.layout.activity_main);
    }
}

