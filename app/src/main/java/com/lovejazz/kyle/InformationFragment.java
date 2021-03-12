package com.lovejazz.kyle;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.lovejazz.kyle.EntryUtils.checkAllFieldsAreFilled;
import static com.lovejazz.kyle.EntryUtils.isValidEmail;
import static com.lovejazz.kyle.EntryUtils.isValidName;
import static com.lovejazz.kyle.EntryUtils.makeSnackbarError;


public class InformationFragment extends Fragment {
    private View inflaterView;
    private Spinner typeSpinner;
    private String recordName;
    private String recordEmail;
    private String recordPassword;
    private String recordTypeSpinnerValue;
    private View snackbarView;
    private EditText recordNameEntry;
    private EditText recordEmailEntry;
    private EditText recordPasswordEntry;
    FirebaseAuth mAuth;
    FirebaseFirestore fstore;
    private static final String TAG = "InformationFragment";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        inflaterView = inflater.inflate(R.layout.fragment_information, container,
                false);
        //Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        //Creating spinner for fragment to choose categories
        typeSpinner = inflaterView.findViewById(R.id.type_spinner);
        ArrayAdapter<CharSequence> typeSpinnerAdapter = ArrayAdapter.createFromResource(
                inflaterView.getContext(), R.array.type_spinner, R.layout.spinner_item);
        typeSpinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        typeSpinner.setAdapter(typeSpinnerAdapter);
        //Creating record
        Button button = inflaterView.findViewById(R.id.button_create_record);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCreateButtonClicked(view);
            }
        });
        return inflaterView;

    }

    private void onCreateButtonClicked(View view) {
        //Setting view for snackbar
        snackbarView = view;
        //Initializing fstore
        fstore = FirebaseFirestore.getInstance();
        //Getting all strings from editTexts
        recordNameEntry = inflaterView.findViewById(R.id.record_name_entry);
        recordEmailEntry = inflaterView.findViewById(R.id.record_email_entry);
        recordPasswordEntry = inflaterView.findViewById(R.id.record_password_entry);
        recordName = recordNameEntry.getText().toString();
        recordEmail = recordEmailEntry.getText().toString();
        recordPassword = recordPasswordEntry.getText().toString();
        recordTypeSpinnerValue = typeSpinner.getSelectedItem().toString();
        if (checkAllFieldsAreFilled(recordName, recordEmail, recordPassword)) {
            makeSnackbarError(view, getString(R.string.error_empty_fields));
        } else if (!isValidEmail(recordEmail)) {
            makeSnackbarError(view, getString(R.string.email_error_contains_not_valid_symbols));
        } else if (!isValidName(recordName)) {
            makeSnackbarError(view, getString(R.string.name_error_not_valid));
        } else {
            fstore.collection("users").document(Objects.requireNonNull(mAuth.getCurrentUser())
                    .getUid()).collection("accounts").get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            createRecord(task);
                        }
                    });

        }
    }

    private void createRecord(Task<QuerySnapshot> task) {
        boolean alreadyExist = false;
        //Getting userId
        String userID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        if (task.isSuccessful()) {
            Log.d(TAG, "Collection received successfully");
            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                if ((recordEmail.equals(document.getString("email")) && recordPassword.equals(
                        document.getString("password")) && recordTypeSpinnerValue.equals(
                        document.getString("category")))) {
                    alreadyExist = true;
                    makeSnackbarError(snackbarView, getString(R.string.
                            document_with_inserted_data_already_exist));
                    Log.d(TAG, "Document with inserted data already exist");
                    break;
                }
            }
            if (!alreadyExist) {
                Log.d(TAG, "Document created successfully");
                DocumentReference documentReference = fstore.collection("users").
                        document(userID).collection("accounts").document();
                documentReference.getId();
                Log.d(TAG, documentReference.getId() + " - documentReferenceId");
                Map<String, Object> record = new HashMap<>();
                record.put("id", documentReference.getId());
                record.put("name", recordName);
                record.put("category", recordTypeSpinnerValue);
                record.put("email", recordEmail);
                record.put("password", recordPassword);
                documentReference.set(record).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //Moving to accountsFragment
                        final ViewPager pager = Objects.requireNonNull(getActivity()).findViewById
                                (R.id.main_pager);
                        pager.setCurrentItem(0);
                        //Clearing entries
                        recordNameEntry.setText("");
                        recordEmailEntry.setText("");
                        recordPasswordEntry.setText("");
                        typeSpinner.setSelection(0);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        makeSnackbarError(snackbarView, getString(R.string.some_problem_occurred));
                        Log.d(TAG, "Some problem occurred");
                    }
                });
            }
        } else {
            Log.d(TAG, "Collection did not receive successfully");
        }
    }
}