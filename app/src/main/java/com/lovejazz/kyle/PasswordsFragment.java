package com.lovejazz.kyle;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Objects;

public class PasswordsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_passwords, container, false);
        //Connecting folder`s RecyclerView
        RecyclerView folderRecycler = view.findViewById(R.id.folder_recycler);
        String[] foldersName = new String[Folder.folders.length];
        for (int i = 0; i < foldersName.length; i++) {
            foldersName[i] = Folder.folders[i].getName();
        }
        int[] foldersImages = new int[Folder.folders.length];
        for (int i = 0; i < foldersImages.length; i++) {
            foldersImages[i] = Folder.folders[i].getImageRecourseId();
        }
        FolderAdapter folderAdapter = new FolderAdapter(foldersName, foldersImages);
        folderRecycler.setAdapter(folderAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
        folderRecycler.setLayoutManager(layoutManager);
        //Connecting credit card`s RecyclerView
        RecyclerView creditRecycler = view.findViewById(R.id.credit_card_recycler);
        String[] creditsName = new String[CreditCard.creditCards.length];
        for (int i = 0; i < creditsName.length; i++) {
            creditsName[i] = CreditCard.creditCards[i].getName();
        }
        String[] creditsLogoNames = new String[CreditCard.creditCards.length];
        for (int i = 0; i < creditsName.length; i++) {
            creditsLogoNames[i] = CreditCard.creditCards[i].getLogoName();
        }
        int[] gradientId = new int[CreditCard.creditCards.length];
        for (int i = 0; i < gradientId.length; i++) {
            gradientId[i] = CreditCard.creditCards[i].getGradientId();
        }
        CreditCardAdapter creditCardAdapter = new CreditCardAdapter(creditsName, creditsLogoNames, gradientId);
        creditRecycler.setAdapter(creditCardAdapter);
        LinearLayoutManager cardManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
        creditRecycler.setLayoutManager(cardManager);
        //Connecting credit card`s RecyclerView
        RecyclerView accountRecycler = view.findViewById(R.id.account_recycler);
        String[] accountsName = new String[Account.accounts.length];
        for (int i = 0; i < accountsName.length; i++) {
            accountsName[i] = Account.accounts[i].getName();
        }
        String[] accountsLogins = new String[Account.accounts.length];
        for (int i = 0; i < accountsLogins.length; i++) {
            accountsLogins[i] = Account.accounts[i].getLogin();
        }
        int[] imageId = new int[Account.accounts.length];
        for (int i = 0; i < imageId.length; i++) {
            imageId[i] = Account.accounts[i].getImageId();
        }
        AccountAdapter accountAdapter = new AccountAdapter(accountsName, accountsLogins, imageId);
        accountRecycler.setAdapter(accountAdapter);
        LinearLayoutManager accountManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        accountRecycler.setLayoutManager(accountManager);
        return view;
    }

}