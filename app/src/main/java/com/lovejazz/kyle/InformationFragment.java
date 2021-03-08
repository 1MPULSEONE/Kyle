package com.lovejazz.kyle;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.snackbar.Snackbar;

import java.util.regex.Pattern;

public class InformationFragment extends Fragment {
    private View inflaterView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        inflaterView = inflater.inflate(R.layout.fragment_information, container,
                false);
        //Creating spinner for fragment to choose categories
        Spinner typeSpinner = inflaterView.findViewById(R.id.type_spinner);
        ArrayAdapter<CharSequence> typeSpinnerAdapter = ArrayAdapter.createFromResource(
                inflaterView.getContext(), R.array.type_spinner, R.layout.spinner_item);
        typeSpinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        typeSpinner.setAdapter(typeSpinnerAdapter);
        //Creating record
        Button button = inflaterView.findViewById(R.id.button_create_record);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createRecord();
            }
        });
        return inflaterView;

    }

    private void createRecord() {
    }

    private void makeSnackbarError(String error, int activityId) {
        Snackbar
                .make(
                        inflaterView.findViewById(activityId),
                        error,
                        Snackbar.LENGTH_LONG
                )
                .setTextColor(getResources().getColor(R.color.full_white))
                .setBackgroundTint(getResources().getColor(R.color.red))
                .show();
    }

    private boolean isValidPassword(String text) {
        String textRegex = "^(?=.*?[A-Z]).{8,18}$";
        Pattern pat = Pattern.compile(textRegex);
        if (text == null)
            return false;
        return pat.matcher(text).matches();
    }

    private boolean isValidEmail(String text) {
        String textRegex = "[^@ \\t\\r\\n]+@[^@ \\t\\r\\n]+\\.[^@ \\t\\r\\n]+";
        Pattern pat = Pattern.compile(textRegex);
        if (text == null)
            return false;
        return pat.matcher(text).matches();
    }

    boolean checkEmailLength(EditText emailEntry) {
        return emailEntry.getText().toString().length() > 254 || emailEntry.getText().
                toString().length() < 3;
    }
}