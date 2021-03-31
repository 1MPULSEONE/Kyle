package com.lovejazz.kyle;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.lovejazz.kyle.adapters.CategoryAdapter;
import com.lovejazz.kyle.adapters.MostPopularAccountsAdapter;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static com.lovejazz.kyle.EntryUtils.makeSnackbarError;

public class PasswordsFragment extends Fragment {
    private static final String TAG = "c";
    private FirebaseFirestore fstore;
    private FirebaseAuth mAuth;
    private List<Map.Entry<String, Integer>> maxCountOfClicks;
    private List<String> bufferedStingsArray;
    private TreeMap<String, String> mostPopularAccountsNames;
    private String currentName;
    private TreeMap<String, String> bannerReferences;
    private String currentBannerReference;
    private String currentAppName;
    private View view;
    private List<Category> categoriesList;
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
                                    maxCountOfClicks.add(new AbstractMap.SimpleEntry<>
                                            (document.getId(), countOfClicks));
                                } else {
                                    for (int i = 0; i < maxCountOfClicks.size(); i++) {
                                        if (countOfClicks > maxCountOfClicks.get(i).getValue()) {
                                            maxCountOfClicks.add(new AbstractMap.SimpleEntry<>
                                                    (maxCountOfClicks.get(i).getKey(),
                                                            countOfClicks));
                                        }
                                    }
                                }
                                //Sorting hashMap
                                Collections.sort(maxCountOfClicks, new Comparator<Map.Entry<String,
                                        Integer>>() {
                                    @Override
                                    public int compare(Map.Entry<String, Integer> o1, Map.
                                            Entry<String, Integer> o2) {
                                        return o1.getValue().compareTo(o2.getValue());
                                    }
                                });
                                Collections.reverse(maxCountOfClicks);
                            }
                            Log.d(TAG, maxCountOfClicks + " - maxCountOfClicks");
                            //Setting most popular accounts recycleView
                            createPopularAccountsRecyclerView();
                        }
                    }
                });
        categoriesList = new ArrayList<>();
        //Getting categories from firestore
        fstore.collection("categories").get().
                addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String name = document.getString("name");
                                String linkToIcon = document.getString("icon");
                                String linkToBackgroundImage = document.getString("background");
                                categoriesList.add
                                        (new Category(name, linkToIcon, linkToBackgroundImage));
                            }
                            setCategoriesRecycler(categoriesList);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                makeSnackbarError(view, getResources().getString(R.string.unexpected_error_try_later));
            }
        });

        return view;
    }

    private void createPopularAccountsRecyclerView() {
        //Check if user have records
        if (maxCountOfClicks.size() == 0) {
            Log.d(TAG, " - maxCountOfClicks == 0");
        } else {
            Log.d(TAG, " - maxCountOfClicks != 0");
            mostPopularAccountsNames = new TreeMap<>();
            bannerReferences = new TreeMap<>();
            for (int i = 0; i < maxCountOfClicks.size(); i++) {
                //Asking for user document
                findUserById(i);
            }
        }
    }

    private void findUserById(final int position) {
        fstore.collection("users").document(mAuth.getCurrentUser().getUid())
                .collection("accounts").
                whereEqualTo("id", maxCountOfClicks.get(position).getKey()).get().
                addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        Log.d(TAG, maxCountOfClicks.get(position).getKey() + " - current id");
                        getRecordInfo(task, position);
                    }
                });
    }

    private void getRecordInfo(@NonNull Task<QuerySnapshot> task, int position) {
        Log.d(TAG, " - getting record info");
        if (task.isSuccessful()) {
            for (final QueryDocumentSnapshot document : task.getResult()) {
                Log.d(TAG, document.getString("id") + " - id of record");
                Log.d(TAG, position + " - position");
                currentName = document.getString("name");
                Log.d(TAG, currentName + " - currentName");
                mostPopularAccountsNames.put(document.getString("id"), currentName);
                currentAppName = document.getString("appName");
                Log.d(TAG, currentAppName + " - currentAppName");
                Log.d(TAG, "banners/" + currentAppName.toLowerCase() + "Banner.png");
                fstore.collection("apps").whereEqualTo("name", currentAppName).
                        get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        Log.d(TAG, imagePosition + " - imagePosition");
                        getBanner(task, document.getString("id"));
                        imagePosition++;
                    }
                });
            }
        }
    }

    private void getBanner(@NonNull Task<QuerySnapshot> task, String id) {
        if (task.isSuccessful()) {
            Log.d(TAG, " - getting banner");
            for (QueryDocumentSnapshot document : task.getResult()) {
                currentBannerReference = document.getString("banner");
                bannerReferences.put(id, currentBannerReference);
                Log.d(TAG, currentBannerReference + " - currentBannerReference");
                if (imagePosition == mostPopularAccountsNames.size() - 1) {
                    setMostPopularAccountsRecycler();
                }
            }
        }
    }

    private void setMostPopularAccountsRecycler() {
        Log.d(TAG, "Creating recycler");
        Log.d(TAG, bannerReferences.toString() + " - bannerReferences");
        //Sorting data from documents
        sortMaps();
        String[] accountNamesArray = new String[mostPopularAccountsNames.size()];
        String[] bannersArray = new String[bannerReferences.size()];

        for (int i = 0; i < bufferedStingsArray.size(); i++) {
            accountNamesArray[i] = mostPopularAccountsNames.get(bufferedStingsArray.get(i));
            bannersArray[i] = bannerReferences.get(bufferedStingsArray.get(i));
        }
        RecyclerView creditRecycler = view.findViewById(R.id.credit_card_recycler);
        MostPopularAccountsAdapter creditCardAdapter = new MostPopularAccountsAdapter
                (accountNamesArray, bannersArray, getContext());
        creditRecycler.setAdapter(creditCardAdapter);
        LinearLayoutManager cardManager = new LinearLayoutManager(view.getContext(),
                LinearLayoutManager.HORIZONTAL, false);
        creditRecycler.setLayoutManager(cardManager);
    }

    private void sortMaps() {
        bufferedStingsArray = new ArrayList<>();
        TreeMap<String, String> accountNamesBufferedHashMap = new TreeMap<>();
        TreeMap<String, String> bannersBufferedHashMap = new TreeMap<>();
        for (Map.Entry<String, Integer> entry : maxCountOfClicks) {
            bufferedStingsArray.add(entry.getKey());
        }
        for (int i = 0; i < bufferedStingsArray.size(); i++) {
            accountNamesBufferedHashMap.put(bufferedStingsArray.get(i),
                    mostPopularAccountsNames.get(bufferedStingsArray.get(i)));
            bannersBufferedHashMap.put(bufferedStingsArray.get(i),
                    bannerReferences.get(bufferedStingsArray.get(i)));

        }
        mostPopularAccountsNames = accountNamesBufferedHashMap;
        bannerReferences = bannersBufferedHashMap;
    }

    private void setCategoriesRecycler(List<Category> categoriesList) {
        String[] categoryNames = new String[categoriesList.size()];
        String[] linksToIcons = new String[categoriesList.size()];
        String[] linkToBackgroundImages = new String[categoriesList.size()];
        for (int i = 0; i < categoriesList.size(); i++) {
            String name = categoriesList.get(i).getName();
            categoryNames[i] = name;
            String linkToIcon = categoriesList.get(i).getLinkToIcon();
            linksToIcons[i] = linkToIcon;
            String linkToBackgroundImage = categoriesList.get(i).getLinkToBackgroundImage();
            linkToBackgroundImages[i] = linkToBackgroundImage;
        }
        RecyclerView recyclerView = view.findViewById(R.id.categories_recycler);
        CategoryAdapter categoryAdapter = new CategoryAdapter(getContext(), categoryNames,
                linksToIcons, linkToBackgroundImages);
        recyclerView.setAdapter(categoryAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
    }
}
