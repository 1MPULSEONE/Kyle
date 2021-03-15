package com.lovejazz.kyle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflaterView = inflater.inflate(R.layout.profile_fragment, container,
                false);
        Button saveDefaultEmailBtn = inflaterView.findViewById(R.id.save_default_email_button);
        // Default email button
        saveDefaultEmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });
        return inflaterView;
    }


    public void openDialog() {
        DialogFragment dialogFragment = new DialogFragment();
        dialogFragment.show(getFragmentManager(), "Dialog fragment");
    }
}