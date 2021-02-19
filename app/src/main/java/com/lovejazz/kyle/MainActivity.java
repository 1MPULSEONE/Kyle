package com.lovejazz.kyle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
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
        //CreatingAdapter for ViewPager
        final SectionsPagerAdapter pagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        final ViewPager pager = findViewById(R.id.main_pager);
        pager.setAdapter(pagerAdapter);
        //BottomNavigation items react on clicks.
        final BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setItemIconTintList(null);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        pager.setCurrentItem(0);
                        break;
                    case R.id.nav_create:
                        pager.setCurrentItem(1);
                        break;
                    case R.id.nav_settings:
                        pager.setCurrentItem(2);
                }
                return false;
            }
        });
        //Connecting ViewPager with BottomNavigationItem
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        //Connecting folder`s RecyclerView
        RecyclerView folderRecycler = findViewById(R.id.folder_recycler);
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
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
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
        int[] gradientId = new int[CreditCard.creditCards.length];
        for (int i = 0; i < gradientId.length; i++) {
            gradientId[i] = CreditCard.creditCards[i].getGradientId();
        }
        CreditCardAdapter creditCardAdapter = new CreditCardAdapter(creditsName, creditsLogoNames, gradientId);
        creditRecycler.setAdapter(creditCardAdapter);
        LinearLayoutManager cardManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
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
        int[] imageId = new int[Account.accounts.length];
        for (int i = 0; i < imageId.length; i++) {
            imageId[i] = Account.accounts[i].getImageId();
        }
        AccountAdapter accountAdapter = new AccountAdapter(accountsName, accountsLogins, imageId);
        accountRecycler.setAdapter(accountAdapter);
        LinearLayoutManager accountManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        accountRecycler.setLayoutManager(accountManager);
    }

    public void onSettingsButtonClicked(View view) {
        //SettingButtonClicked
    }

    //Creating adapter, which will provide information to ViewPager
    private static class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new PasswordsFragment();
                case 1:
                    return new CreateFragment();
                case 2:
                    return new SettingsFragment();
            }
            return null;
        }

        //Returns the number of fragments
        @Override
        public int getCount() {
            return 3;
        }
    }
}


