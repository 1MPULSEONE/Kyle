package com.lovejazz.kyle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Deleting toolbar
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e){
            Log.d("MainRegisterActivity","Toolbar produced null pointer exception");
        }
        setContentView(R.layout.activity_main);
        //Connecting ViewPager with SectionPagerAdapter
        SectionsPagerAdapter pagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        ViewPager pager = findViewById(R.id.main_pager);
        pager.setAdapter(pagerAdapter);
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
                    return new SettingsFragment();
            }
            return null;
        }
        //Returns the number of fragments
        @Override
        public int getCount() {
            return 2;
        }
    }
}