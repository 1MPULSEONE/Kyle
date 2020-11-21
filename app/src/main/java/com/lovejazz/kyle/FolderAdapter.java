/*
This is the recycler view adapter class, which help us to show folders in folder.card.xml.
*/
package com.lovejazz.kyle;


import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

class FolderAdapter extends
        RecyclerView.Adapter<FolderAdapter.ViewHolder> {

    private String[] names;
    private int[] imageIds;

    public FolderAdapter(String[] names, int[] imageIds) {
        this.names = names;
        this.imageIds = imageIds;
    }

    //This methods is used, when RecycleView requires new ViewHolder object.
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.folder_card,parent,false);
        return new ViewHolder(cv);
    }
    //This method is used, when RecyclerView shows our data.
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CardView folderCard = holder.cardView;
        ImageView imageView = folderCard.findViewById(R.id.folder_image);
        Drawable backgroundImage = ContextCompat.getDrawable(folderCard.getContext(), imageIds[position]);
        imageView.setImageDrawable(backgroundImage);
        imageView.setContentDescription(names[position]);
        TextView folderName = folderCard.findViewById(R.id.folder_name);
        folderName.setText(names[position]);
    }

    @Override
    public int getItemCount() {
        return names.length;
    }

    //ViewHolder class, which connect cardView with RecyclerView.
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;
        public ViewHolder(CardView card) {
            super(card);
            cardView = card;
        }

    }
}
