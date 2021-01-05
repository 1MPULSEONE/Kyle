package com.lovejazz.kyle;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class CreditCardAdapter extends RecyclerView.Adapter<CreditCardAdapter.ViewHolder> {
    private String[] names;
    private String[] logoNames;
    private int[] gradientId;


    public CreditCardAdapter(String[] names, String[] logoNames, int[] gradientId) {
        this.names = names;
        this.logoNames = logoNames;
        this.gradientId = gradientId;
    }

    //This methods is used, when RecycleView requires new ViewHolder object.
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ConstraintLayout cl = (ConstraintLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.credit_card, parent, false);
        return new ViewHolder(cl);
    }

    //This method is used, when RecyclerView shows our data.
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ConstraintLayout creditCardView = holder.cardView;
        ImageView imageView = creditCardView.findViewById(R.id.credit_card_background);
        Drawable backgroundGradient = ContextCompat.getDrawable(creditCardView.getContext(), gradientId[position]);
        imageView.setImageDrawable(backgroundGradient);
        imageView.setContentDescription(names[position]);
        TextView cardName = creditCardView.findViewById(R.id.credit_cards_name);
        cardName.setText(names[position]);
        TextView cardLogoName = creditCardView.findViewById(R.id.logo_card_name);
        cardLogoName.setText(logoNames[position]);
    }

    @Override
    public int getItemCount() {
        return names.length;
    }

    //ViewHolder class, which connect cardView with RecyclerView.
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ConstraintLayout cardView;

        public ViewHolder(@NonNull ConstraintLayout card) {
            super(card);
            cardView = card;
        }
    }
}
