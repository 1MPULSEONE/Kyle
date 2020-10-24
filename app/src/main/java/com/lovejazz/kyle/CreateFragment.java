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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //Connecting pager with adapter
        View RootView = inflater.inflate(R.layout.fragment_create, container, false);
        final CreatePagerAdapter createPagerAdapter = new CreatePagerAdapter(Objects.requireNonNull(getChildFragmentManager()));
        final NonSwipeableViewPager pager = RootView.findViewById(R.id.create_pager);
        pager.setAdapter(createPagerAdapter);

        //Creating infoButton listener
        Button infoBtn = RootView.findViewById(R.id.button_info);
        infoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pager.setCurrentItem(0);
                Log.d("CreateFragment","Info button is clicked.");
            }
        });
        //Creating settingsButton listener
        Button settingsButton = RootView.findViewById(R.id.button_settings);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pager.setCurrentItem(1);
            }
        });
        return RootView;
    }


    private static class CreatePagerAdapter extends FragmentPagerAdapter {

        public CreatePagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new InformationFragment();
                case 1:
                    return new SettingsOfCreateFragment();
            }
            return null;
        }

    }
}