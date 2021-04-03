package com.lovejazz.kyle.adapters;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.lovejazz.kyle.R;

import java.util.ArrayList;
import java.util.List;


public class AccountActivity extends AppCompatActivity {
    private static final String TAG = "AccountActivity";
    private FirebaseAuth mAuth;
    private FirebaseFirestore fstore;
    private ImageView appIcon;
    private EditText nameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private Spinner categorySpinner;
    private Spinner appSpinner;

    @Override

    protected void onStart() {
        super.onStart();
        appIcon = findViewById(R.id.account_icon);
        nameEditText = findViewById(R.id.name_edit_text);
        emailEditText = findViewById(R.id.email_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        categorySpinner = findViewById(R.id.spinner_category);
        appSpinner = findViewById(R.id.spinner_app);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        //Initializing firebase firestore
        fstore = FirebaseFirestore.getInstance();
        //Initializing firebase firestore
        mAuth = FirebaseAuth.getInstance();
        //Getting id of account record
        String ID = getAccountId();
        setAccountInfo(ID);
    }

    private void setAccountInfo(final String id) {
        fstore.collection("users").document(mAuth.getCurrentUser().getUid())
                .collection("accounts").whereEqualTo("id", id).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (final QueryDocumentSnapshot document : task.getResult()) {
                                //Setting document name
                                String name = document.getString("name");
                                nameEditText.setText(name);
                                //Setting document email
                                String email = document.getString("email");
                                emailEditText.setText(email);
                                //Setting document password
                                String password = document.getString("password");
                                passwordEditText.setText(password);
                                String appName = document.getString("appName");
                                Log.d(TAG, appName + " - appName");
                                //Getting current category
                                final String currentCategory = document.getString("category");
                                //Getting current appName
                                final String currentAppName = document.getString("appName");
                                //Get current
                                //Getting link to app icon
                                fstore.collection("apps").
                                        whereEqualTo("name", appName).get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                                        String linkToIcon = document.getString("icon");
                                                        Log.d(TAG, linkToIcon + " - link to icon");
                                                        loadAccountIcon(linkToIcon);
                                                    }
                                                }
                                            }
                                        });
                                //Setting category spinner
                                fstore.collection("categories").get().
                                        addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    List<String> categories = new ArrayList<>();
                                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                                        String category = document.getString("name");
                                                        categories.add(category);
                                                    }
                                                    //Creating adapter for category spinner
                                                    ArrayAdapter<String> categorySpinnerAdapter = new ArrayAdapter<>(
                                                            getBaseContext(), R.layout.spinner_item, categories);
                                                    categorySpinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
                                                    categorySpinner.setAdapter(categorySpinnerAdapter);
                                                    //Getting position of current category
                                                    int categoryPosition = categories.indexOf(currentCategory);
                                                    categorySpinner.setSelection(categoryPosition);
                                                }
                                            }
                                        });
                                //Setting app spinner
                                fstore.collection("apps").get().
                                        addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    List<String> appsNames = new ArrayList<>();
                                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                                        String appName = document.getString("name");
                                                        appsNames.add(appName);
                                                    }
                                                    //Creating adapter for app spinner
                                                    ArrayAdapter<String> appSpinnerAdapter = new ArrayAdapter<>(
                                                            getBaseContext(), R.layout.spinner_item, appsNames);
                                                    appSpinner.setAdapter(appSpinnerAdapter);
                                                    appSpinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
                                                    int appNamePosition = appsNames.indexOf(currentAppName);
                                                    appSpinner.setSelection(appNamePosition);
                                                }
                                            }
                                        });
                            }
                        }
                    }
                });
    }

    private String getAccountId() {
        Intent intentFromPasswordsFragment = getIntent();
        String accountID = intentFromPasswordsFragment.getStringExtra("ID");
        return accountID;
    }

    private void loadAccountIcon(String accountIconLink) {
        Glide.with(this)
                .load(accountIconLink)
                .into(appIcon);
    }
}