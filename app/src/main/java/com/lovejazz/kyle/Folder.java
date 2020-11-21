/*
This is the folder view class which is used in recycler view.
*/
package com.lovejazz.kyle;

public class Folder {
    private String name;
    private int imageRecourseId;
    // test folders
    public static final Folder[] folders = {
            new Folder("Социальные сети", R.drawable.folder_background),
            new Folder("Дизайн аккаунты", R.drawable.folder_background_1),
            new Folder("Образование", R.drawable.folder_background)
    };

    public Folder(String name, int imageRecourseId) {
        this.name = name;
        this.imageRecourseId = imageRecourseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageRecourseId() {
        return imageRecourseId;
    }

    public void setImageRecourseId(int imageRecourseId) {
        this.imageRecourseId = imageRecourseId;
    }
}
