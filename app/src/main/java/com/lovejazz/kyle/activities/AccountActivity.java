package com.lovejazz.kyle.activities;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
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
    private TextView accountName;
    private TextView accountEmail;
    private EditText nameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private Spinner categorySpinner;
    private Spinner appSpinner;
    private ImageButton copyButton;
    private boolean copyButtonActive;
    private String activeEditText;

    @Override

    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        //Initializing firebase firestore
        fstore = FirebaseFirestore.getInstance();
        //Initializing firebase firestore
        mAuth = FirebaseAuth.getInstance();
        appIcon = findViewById(R.id.account_icon);
        nameEditText = findViewById(R.id.name_edit_text);
        emailEditText = findViewById(R.id.email_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        categorySpinner = findViewById(R.id.spinner_category);
        appSpinner = findViewById(R.id.spinner_app);
        accountName = findViewById(R.id.account_name);
        accountEmail = findViewById(R.id.account_email);
        copyButton = findViewById(R.id.copy_button);
        //Getting id of account record
        String ID = getAccountId();
        setAccountInfo(ID);
        setFocusListeners();
        copyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (copyButtonActive) {
                    copyTextToClipBoard(activeEditText);
                }
            }
        });
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
                                accountName.setText(name);
                                //Setting document email
                                String email = document.getString("email");
                                emailEditText.setText(email);
                                accountEmail.setText(email);
                                //Setting document password
                                String password = document.getString("password");
                                passwordEditText.setText(password);
                                String appName = document.getString("appName");
                                //Getting current category
                                final String currentCategory = document.getString("category");
                                //Getting current appName
                                final String currentAppName = document.getString("appName");
                                //Setting category spinner
                                CollectionReference categoryDocRef = fstore.collection("categories");
                                setSpinner(categoryDocRef, categorySpinner, currentCategory);
                                //Setting app spinner
                                CollectionReference appDocRef = fstore.collection("apps");
                                setSpinner(appDocRef, appSpinner, currentAppName);
                                //Setting app icon
                                fstore.collection("apps").
                                        whereEqualTo("name", appName).get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                                        String linkToIcon = document.getString("icon");
                                                        loadAccountIcon(linkToIcon);
                                                    }
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

    private void setSpinner(CollectionReference collectionReference, final Spinner spinnerView, final String currentElement) {
        collectionReference.get().
                addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Collection successfully got");
                            List<String> spinnerElements = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String spinnerElement = document.getString("name");
                                Log.d(TAG, spinnerElement + " - spinnerElement+");
                                spinnerElements.add(spinnerElement);
                            }
                            //Creating adapter for spinner
                            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                                    getBaseContext(), R.layout.spinner_item, spinnerElements);
                            spinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
                            spinnerView.setAdapter(spinnerAdapter);
                            //Getting position of current category
                            Log.d(TAG, currentElement + " - currentElement");
                            int categoryPosition = spinnerElements.indexOf(currentElement);
                            spinnerView.setSelection(categoryPosition);
                        }
                    }
                });
    }

    private void setFocusListeners() {
        //Setting focus listener for name editText
        nameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                setStatusButton(b, "name");
            }
        });
        //Setting focus listener for emailEditText
        emailEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                setStatusButton(b, "email");
            }
        });
        //Setting focus listener for passwordsEditText
        passwordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                setStatusButton(b, "password");
            }
        });
    }

    private void setStatusButton(boolean isFocused, String name) {
        if (isFocused) {
            copyButton.setImageResource(R.drawable.copy_button_active);
            copyButtonActive = true;
            activeEditText = name;
        } else {
            copyButton.setImageResource(R.drawable.copy_button_inactive);
            copyButtonActive = false;
        }
    }

    private void copyTextToClipBoard(String activeEditText) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip;
        switch (activeEditText) {
            case "name":
                clip = ClipData.newPlainText("Data", nameEditText.getText().toString());
                clipboard.setPrimaryClip(clip);
                break;
            case "email":
                clip = ClipData.newPlainText("Data", emailEditText.getText().toString());
                clipboard.setPrimaryClip(clip);
                break;
            case "password":
                clip = ClipData.newPlainText("Data", passwordEditText.getText().toString());
                clipboard.setPrimaryClip(clip);
                break;
        }
    }
}