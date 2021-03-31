package com.lovejazz.kyle.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.lovejazz.kyle.Account;
import com.lovejazz.kyle.R;
import com.lovejazz.kyle.adapters.AccountAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.lovejazz.kyle.EntryUtils.makeSnackbarError;

public class CategoryActivity extends AppCompatActivity {
    private static final String TAG = "CategoryActivity";
    private FirebaseAuth mAuth;
    private FirebaseFirestore fstore;
    private List<Account> accountsArrayList;
    private RecyclerView accountsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        //Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        //Initializing fstore
        fstore = FirebaseFirestore.getInstance();
        String categoryName = getCategoryName();
        //Initializing list for accounts
        accountsArrayList = new ArrayList<>();
        accountsRecyclerView = findViewById(R.id.accounts_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        accountsRecyclerView.setLayoutManager(layoutManager);
        final AccountAdapter accountAdapter = new AccountAdapter(accountsArrayList);
        accountsRecyclerView.setAdapter(accountAdapter);
        fstore.collection("users").document(Objects.requireNonNull(mAuth.getUid())).
                collection("accounts").whereEqualTo("category", categoryName)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (final QueryDocumentSnapshot document : task.getResult()) {
                        final String accountName = document.getString("name");
                        final String email = document.getString("email");
                        String appName = document.getString("appName");
                        fstore.collection("apps").
                                whereEqualTo("name", appName).get().
                                addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot documentSnapshot :
                                                    task.getResult()) {
                                                Log.d(TAG, "onComplete: i am here!");
                                                String iconLink = documentSnapshot.getString("icon");
                                                accountsArrayList.add(new Account(accountName, email, iconLink));
                                                accountAdapter.notifyDataSetChanged();
                                            }
                                        }
                                    }
                                });
                    }
                } else {
                    makeSnackbarError(findViewById(R.id.activity_category), getResources()
                            .getString(R.string.unexpected_error_try_later));
                }
            }
        });
    }

    private String getCategoryName() {
        Intent intentFromPasswordsFragment = getIntent();
        String categoryName = intentFromPasswordsFragment.getStringExtra("categoryName");
        Log.d(TAG, categoryName + " - categoryName");
        return categoryName;
    }
}