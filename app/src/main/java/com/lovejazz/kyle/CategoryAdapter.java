package com.lovejazz.kyle;

import android.content.Context;
import android.util.Log;
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
    private OnCategoryListener onCategoryListener;
    Context context;

    public CategoryAdapter(Context context, String[] titles, String[] linksToIcons,
                           String[] linksToBackgroundImages, OnCategoryListener onCategoryListener) {
        this.titles = titles;
        this.linksToIcons = linksToIcons;
        this.linksToBackgroundImages = linksToBackgroundImages;
        this.context = context;
        this.onCategoryListener = onCategoryListener;
    }

    //This methods is used, when RecycleView requires new ViewHolder object.
    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View cardView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_card, parent, false);
        return new CategoryAdapter.ViewHolder(cardView, onCategoryListener);
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
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private View cardView;
        OnCategoryListener onCategoryListener;

        public ViewHolder(@NonNull View card, OnCategoryListener onCategoryListener) {
            super(card);
            cardView = card;
            this.onCategoryListener = onCategoryListener;
            card.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.d("CategoryAdapter", "onClick: " + getAdapterPosition());
            onCategoryListener.onItemClicked(getAdapterPosition());
        }
    }

    //Making listener for our elements
    public interface OnCategoryListener {
        void onItemClicked(int position);
    }
}
