package com.lovejazz.kyle;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Objects;

public class CreateFragment extends Fragment {
    private boolean infoBtnActive = true;
    private boolean settingsBtnActive = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        //Connecting pager with adapter
//        View RootView = inflater.inflate(R.layout.fragment_create, container, false);
//        final CreatePagerAdapter createPagerAdapter = new CreatePagerAdapter(Objects.requireNonNull(getChildFragmentManager()));
//        final NonSwipeableViewPager pager = RootView.findViewById(R.id.create_pager);
//        pager.setAdapter(createPagerAdapter);
//        //Creating infoButton listener
//        final Button infoBtn = RootView.findViewById(R.id.button_info);
//        final Button settingsButton = RootView.findViewById(R.id.button_settings);
//        infoBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                pager.setCurrentItem(0);
//                Log.d("CreateFragment", "Info button is clicked.");
//                if (!infoBtnActive) {
//                    infoBtn.setBackgroundResource(R.drawable.info_tab_btn);
//                    infoBtn.setTextColor(getResources().getColor(R.color.full_white));
//                    settingsButton.setBackgroundResource(R.drawable.settings_tab_btn);
//                    settingsButton.setTextColor(getResources().getColor(R.color.dark_grey));
//                    infoBtnActive = true;
//                    settingsBtnActive = false;
//                }
//            }
//        });
//        //Creating settingsButton listener
//        settingsButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                pager.setCurrentItem(1);
//                if (!settingsBtnActive) {
//                    settingsButton.setBackgroundResource(R.drawable.settings_tab_btn_active);
//                    settingsButton.setTextColor(getResources().getColor(R.color.full_white));
//                    infoBtn.setBackgroundResource(R.drawable.info_tab_btn_inactive);
//                    infoBtn.setTextColor(getResources().getColor(R.color.dark_grey));
//                    settingsBtnActive = true;
//                    infoBtnActive = false;
//                }
//            }
//        });
//        return RootView;
//    }
//
//
//    private static class CreatePagerAdapter extends FragmentPagerAdapter {
//
//        public CreatePagerAdapter(@NonNull FragmentManager fm) {
//            super(fm);
//        }
//
//        @Override
//        public int getCount() {
//            return 2;
//        }
//
//        @NonNull
//        @Override
//        public Fragment getItem(int position) {
//            switch (position){
//                case 0:
//                    return new InformationFragment();
//                case 1:
//                    return new SettingsOfCreateFragment();
//            }
//            return null;
//        }
        return inflater.inflate(R.layout.fragment_create, container, false);
    }
}