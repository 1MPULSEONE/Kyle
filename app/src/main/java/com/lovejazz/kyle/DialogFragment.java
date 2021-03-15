package com.lovejazz.kyle;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class DialogFragment extends AppCompatDialogFragment {
    //Edit text from dialogFragment
    private EditText editTextEmail;
    private FirebaseFirestore fstore;
    private FirebaseAuth mAuth;
    private String TAG = "DialogFragment";

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        fstore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        //Creating dialogFragment
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);
        editTextEmail = view.findViewById(R.id.email_confirm_entry);
        builder.setView(view)
                .setTitle(getString(R.string.insert_default_email))
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Check if value changed
                        DocumentReference documentReference = fstore.collection
                                ("users").document(
                                mAuth.getCurrentUser().getUid());
                        documentReference.get().
                                addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        Log.d(TAG, "User document has received successfully");
                                        DocumentSnapshot document = task.getResult();
                                        if (!(editTextEmail.getText().toString().equals(
                                                document.
                                                        getString("defaultEmail")))) {
                                            fstore.collection("users").document(mAuth.
                                                    getCurrentUser().getUid()).
                                                    update("defaultEmail", editTextEmail.
                                                            getText().toString())
                                                    .addOnSuccessListener(new OnSuccessListener
                                                            <Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            Log.d(TAG,
                                                                    "defaultEmail has been" +
                                                                            " written " +
                                                                            "successfully");
                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull
                                                                                      Exception e) {
                                                            Log.d(TAG,
                                                                    "defaultEmail has not been"
                                                                            + "written" +
                                                                            " succesfully");
                                                        }
                                                    });
                                        }
                                    }
                                });
                    }
                });
        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();
        //Setting color for alert dialog`s buttons
        ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_POSITIVE).setTextColor
                (getResources().getColor(R.color.red));
        ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor
                (getResources().getColor(R.color.red));
    }
}
