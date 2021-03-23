package com.lovejazz.kyle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private String[] titles;
    private String[] linksToIcons;
    private String[] linksToBackgroundImages;
    Context context;

    public CategoryAdapter(Context context, String[] titles, String[] linksToIcons, String[] linksToBackgroundImages) {
        this.titles = titles;
        this.linksToIcons = linksToIcons;
        this.linksToBackgroundImages = linksToBackgroundImages;
        this.context = context;
    }

    //This methods is used, when RecycleView requires new ViewHolder object.
    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View cardView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_card, parent, false);
        return new CategoryAdapter.ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
        View categoryView = holder.cardView;
        TextView categoryTitle = categoryView.findViewById(R.id.category_title);
        ImageView backgroundImage = categoryView.findViewById(R.id.category_background);
        ImageView iconImage = categoryView.findViewById(R.id.category_icon);
        categoryTitle.setText(titles[position]);
        Glide.with(context)
                .load(linksToBackgroundImages[position])
                .into(backgroundImage);
        backgroundImage.setContentDescription(titles[position]);
        Glide.with(context)
                .load(linksToIcons[position])
                .into(iconImage);
        iconImage.setContentDescription(titles[position]);
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }

    //ViewHolder class, which connect cardView with RecyclerView.
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private View cardView;

        public ViewHolder(@NonNull View card) {
            super(card);
            cardView = card;
        }
    }
}
