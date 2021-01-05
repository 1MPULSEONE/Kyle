package com.lovejazz.kyle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

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
        //Connecting folder`s RecyclerView
        RecyclerView folderRecycler = findViewById(R.id.folder_recycler);
        String[] foldersName = new String[Folder.folders.length];
        for (int i = 0; i < foldersName.length; i++) {
            foldersName[i] = Folder.folders[i].getName();
        }
        int[] foldersImages = new int [Folder.folders.length];
        for (int i =0 ; i < foldersImages.length; i++) {
            foldersImages[i] = Folder.folders[i].getImageRecourseId();
        }
        FolderAdapter folderAdapter = new FolderAdapter(foldersName,foldersImages);
        folderRecycler.setAdapter(folderAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        folderRecycler.setLayoutManager(layoutManager);
        //Connecting credit card`s RecyclerView
        RecyclerView creditRecycler = findViewById(R.id.credit_card_recycler);
        String[] creditsName = new String[CreditCard.creditCards.length];
        for (int i = 0; i < creditsName.length; i++) {
            creditsName[i] = CreditCard.creditCards[i].getName();
        }
        String[] creditsLogoNames = new String[CreditCard.creditCards.length];
        for (int i = 0; i < creditsName.length; i++) {
            creditsLogoNames[i] = CreditCard.creditCards[i].getLogoName();
        }
        int[] gradientId = new int [CreditCard.creditCards.length];
        for (int i = 0; i < gradientId.length; i++) {
            gradientId[i] = CreditCard.creditCards[i].getGradientId();
        }
        CreditCardAdapter creditCardAdapter = new CreditCardAdapter(creditsName,creditsLogoNames,gradientId);
        creditRecycler.setAdapter(creditCardAdapter);
        LinearLayoutManager cardManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        creditRecycler.setLayoutManager(cardManager);
        //Connecting credit card`s RecyclerView
        RecyclerView accountRecycler = findViewById(R.id.account_recycler);
        String[] accountsName = new String[Account.accounts.length];
        for (int i = 0; i < accountsName.length; i++) {
            accountsName[i] = Account.accounts[i].getName();
        }
        String[] accountsLogins = new String[Account.accounts.length];
        for (int i = 0; i < accountsLogins.length; i++) {
            accountsLogins[i] = Account.accounts[i].getLogin();
        }
        int[] imageId = new int [Account.accounts.length];
        for (int i = 0; i < imageId.length; i++) {
            imageId[i] = Account.accounts[i].getImageId();
        }
        AccountAdapter accountAdapter = new AccountAdapter(accountsName,accountsLogins,imageId);
        accountRecycler.setAdapter(accountAdapter);
        LinearLayoutManager accountManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        accountRecycler.setLayoutManager(accountManager);
    }

    public void onSettingsButtonClicked(View view) {
        //SettingButtonClicked
    }

}

