package com.lovejazz.kyle;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class InformationFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflaterView = inflater.inflate(R.layout.fragment_information, container,
                false);
        Spinner typeSpinner = inflaterView.findViewById(R.id.type_spinner);
        ArrayAdapter<CharSequence> typeSpinnerAdapter = ArrayAdapter.createFromResource(
                inflaterView.getContext(), R.array.type_spinner,R.layout.spinner_item);
        typeSpinnerAdapter.setDropDownViewResource(R.layout.spinner_item);
        typeSpinner.setAdapter(typeSpinnerAdapter);
        return inflaterView;
    }
}