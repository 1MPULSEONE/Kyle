package com.lovejazz.kyle;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

public class PasswordsFragment extends Fragment {
    private static final String TAG = "PasswordsFragment";
    private FirebaseFirestore fstore;
    private FirebaseAuth mAuth;
    private FirebaseStorage storage;
    private List<Map.Entry<String, Integer>> maxCountOfClicks;
    private String[] mostPopularAccountsNames;
    private String currentName;
    private String[] bannerReferences;
    private String currentBannerReference;
    private String currentAppName;
    private View view;
    private int position;
    private int imagePosition;

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_passwords, container, false);
        //Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        //Initializing fstore
        fstore = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        maxCountOfClicks = new ArrayList<>();

        //Getting maxCountOfClicks
        fstore.collection("users").
                document(mAuth.getCurrentUser().getUid()).collection("accounts").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                int countOfClicks = Integer.
                                        parseInt(document.get("countOfClicks").toString());
                                Log.d(TAG, countOfClicks + " - count of clicks");
                                if (maxCountOfClicks.size() < 6) {
                                    maxCountOfClicks.add(new AbstractMap.SimpleEntry<>(document.getId(), countOfClicks));
                                } else {
                                    for (int i = 0; i < maxCountOfClicks.size(); i ++ ) {//Map.Entry<String, Integer> element : maxCountOfClicks) {
                                        if (countOfClicks > maxCountOfClicks.get(i).getValue()) {
                                            maxCountOfClicks.add(new AbstractMap.SimpleEntry<>(maxCountOfClicks.get(i).getKey(), countOfClicks));
                                            break;
                                        }
                                    }
                                }
                                //Sorting hashMap
                                Collections.sort(maxCountOfClicks, new Comparator<Map.Entry<String, Integer>>() {
                                    @Override
                                    public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                                        return o1.getValue().compareTo(o2.getValue());
                                    }
                                });
                                Collections.reverse(maxCountOfClicks);
                            }
                            Log.d(TAG, maxCountOfClicks + " - maxCountOfClicks");
                            createPopularAccountsRecyclerView();
                        }
                    }
                });
//        //Connecting credit card`s RecyclerView
//        RecyclerView accountRecycler = view.findViewById(R.id.account_recycler);
//        String[] accountsName = new String[Account.accounts.length];
//        for (int i = 0; i < accountsName.length; i++) {
//            accountsName[i] = Account.accounts[i].getName();
//        }
//        String[] accountsLogins = new String[Account.accounts.length];
//        for (int i = 0; i < accountsLogins.length; i++) {
//            accountsLogins[i] = Account.accounts[i].getLogin();
//        }
//        int[] imageId = new int[Account.accounts.length];
//        for (int i = 0; i < imageId.length; i++) {
//            imageId[i] = Account.accounts[i].getImageId();
//        }
//        AccountAdapter accountAdapter = new AccountAdapter(accountsName, accountsLogins, imageId);
//        accountRecycler.setAdapter(accountAdapter);
//        LinearLayoutManager accountManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
//        accountRecycler.setLayoutManager(accountManager);
        return view;
    }

    private void createPopularAccountsRecyclerView() {
        if (maxCountOfClicks.size() == 0) {
            Log.d(TAG, " - maxCountOfClicks == 0");
        } else {
            Log.d(TAG, " - maxCountOfClicks != 0");
            mostPopularAccountsNames = new String[maxCountOfClicks.size()];
            bannerReferences = new String[maxCountOfClicks.size()];
            for (int i = 0; i < mostPopularAccountsNames.length; i++) {
                position = 0;
                fstore.collection("users").document(mAuth.getCurrentUser().getUid())
                        .collection("accounts").
                        whereEqualTo("countOfClicks", maxCountOfClicks.get(i)).get().
                        addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                getRecordInfo(task);
                            }
                        });
            }
        }
    }

    private void getRecordInfo(@NonNull Task<QuerySnapshot> task) {
        Log.d(TAG, " - getting record info");
        if (task.isSuccessful()) {
            for (QueryDocumentSnapshot document : task.getResult()) {
                currentName = document.getString("name");
                Log.d(TAG, currentName + " - currentName");
                mostPopularAccountsNames[position] = currentName;
                currentAppName = document.getString("appName");
                Log.d(TAG, currentAppName + " - currentAppName");
                Log.d(TAG, position + " - position");
                Log.d(TAG, "banners/" + currentAppName.toLowerCase() + "Banner.png");
                fstore.collection("apps").whereEqualTo("name", currentAppName).
                        get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        Log.d(TAG, imagePosition + " - imagePosition");
                        getBanner(task);
                        imagePosition++;
                    }
                });
                position++;
                break;
            }
        }
    }

    private void getBanner(@NonNull Task<QuerySnapshot> task) {
        if (task.isSuccessful()) {
            Log.d(TAG, " - getting banner");
            for (QueryDocumentSnapshot document : task.getResult()) {
                currentBannerReference = document.getString("banner");
                bannerReferences[imagePosition] = currentBannerReference;
                Log.d(TAG, currentBannerReference + " - currentBannerReference");
                if (imagePosition == mostPopularAccountsNames.length - 1) {
                    setRecycler();
                }
            }
        }
    }

    private void setRecycler() {
        Log.d(TAG, Arrays.toString(bannerReferences) + " - bannerReferences");
        Log.d(TAG, Arrays.toString(mostPopularAccountsNames) + " - mostPopularAccountsNames");
        Log.d(TAG, "Creating recycler");
        RecyclerView creditRecycler = view.findViewById(R.id.credit_card_recycler);
        MostPopularAccountsAdapter creditCardAdapter = new MostPopularAccountsAdapter
                (mostPopularAccountsNames, bannerReferences, getContext());
        creditRecycler.setAdapter(creditCardAdapter);
        LinearLayoutManager cardManager = new LinearLayoutManager(view.getContext(),
                LinearLayoutManager.HORIZONTAL, false);
        creditRecycler.setLayoutManager(cardManager);
    }
}
