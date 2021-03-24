package com.lovejazz.kyle;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class CategoryActivity extends AppCompatActivity {
    private String itemID;
    private static final String TAG = "CategoryActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        //Getting extra from PasswordsFragment
        Intent intentFromPasswordsFragment = getIntent();
        itemID = intentFromPasswordsFragment.getStringExtra("itemID");
        Log.d(TAG, itemID + " - itemID");
    }
}