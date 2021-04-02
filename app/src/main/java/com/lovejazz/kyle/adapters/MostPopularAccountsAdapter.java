package com.lovejazz.kyle.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.lovejazz.kyle.R;

public class MostPopularAccountsAdapter extends RecyclerView.Adapter<MostPopularAccountsAdapter.ViewHolder> {
    private String[] names;
    private String[] storageReferences;
    private Context context;

    public MostPopularAccountsAdapter(String[] names, String[] storageReferences, Context context) {
        this.names = names;
        this.storageReferences = storageReferences;
        this.context = context;
    }

    //This methods is used, when RecycleView requires new ViewHolder object.
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View cl =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.most_popular_acc, parent, false);
        return new ViewHolder(cl);
    }

    //This method is used, when RecyclerView shows our data.
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        View creditCardView = holder.cardView;
        ImageView imageView = creditCardView.findViewById(R.id.most_popular_account_background);
        Glide.with(context)
                .load(storageReferences[position])
                .into(imageView);
        imageView.setContentDescription(names[position]);
        TextView cardName = creditCardView.findViewById(R.id.most_popular_account_title);
        cardName.setText(names[position]);
    }

    @Override
    public int getItemCount() {
        return names.length;
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
