package com.lovejazz.kyle.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.lovejazz.kyle.CategoryActivity;
import com.lovejazz.kyle.R;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private static final String TAG = "CategoryAdapter";
    private String[] titles;
    private String[] linksToIcons;
    private String[] linksToBackgroundImages;
    Context context;

    public CategoryAdapter(Context context, String[] titles, String[] linksToIcons,
                           String[] linksToBackgroundImages) {
        this.titles = titles;
        this.linksToIcons = linksToIcons;
        this.linksToBackgroundImages = linksToBackgroundImages;
        this.context = context;

    }

    //This methods is used, when RecycleView requires new ViewHolder object.
    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_card, parent, false);
        return new CategoryAdapter.ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, final int position) {
        CardView categoryView = holder.cardView;
        final TextView categoryTitle = categoryView.findViewById(R.id.category_title);
        ImageButton backgroundImage = categoryView.findViewById(R.id.category_background);
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
        backgroundImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "cardView is clicked");
                String categoryName = titles[position];
                Log.d(TAG, position + " - position");
                Intent intentToCategoryActivity = new Intent(context, CategoryActivity.class);
                intentToCategoryActivity.putExtra("categoryName", categoryName);
                context.startActivity(intentToCategoryActivity);
            }
        });
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }

    //ViewHolder class, which connect cardView with RecyclerView.
    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;

        public ViewHolder(@NonNull CardView itemView) {
            super(itemView);
            cardView = itemView;
        }
    }

}
