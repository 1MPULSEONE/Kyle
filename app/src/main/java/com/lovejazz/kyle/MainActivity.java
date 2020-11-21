package com.lovejazz.kyle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Deleting toolbar
        try {
            Objects.requireNonNull(this.getSupportActionBar()).hide();
        } catch (NullPointerException e) {
            Log.d("MainRegisterActivity", "Toolbar produced null pointer exception");
        }
        setContentView(R.layout.activity_main);
        //Connecting folder`s RecyclerView
        RecyclerView folderRecycler = findViewById(R.id.folder_recycler);
        String[] foldersName = new String[Folder.folders.length];
        for (int i = 0; i < foldersName.length; i++) {
            foldersName[i] = Folder.folders[i].getName();
        }
        int[] foldersImages = new int [Folder.folders.length];
        for (int i =0 ; i < foldersImages.length; i++) {
            foldersImages[i] = Folder.folders[i].getImageRecourseId();
        }
        FolderAdapter folderAdapter = new FolderAdapter(foldersName,foldersImages);
        folderRecycler.setAdapter(folderAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        folderRecycler.setLayoutManager(layoutManager);
    }

    public void onSettingsButtonClicked(View view) {
        //SettingButtonClicked
    }

}

