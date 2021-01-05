package com.lovejazz.kyle;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.ViewHolder> {
    private String[] names;
    private String[] login;
    private int[] imageId;

    public AccountAdapter(String[] names, String[] login, int[] imageId) {
        this.names = names;
        this.login = login;
        this.imageId = imageId;
    }

    //This methods is used, when RecycleView requires new ViewHolder object.
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.account_card, parent, false);
        return new ViewHolder(cardView);
    }

    //This method is used, when RecyclerView shows our data.
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CardView  accountCardView = holder.cardView;
        ImageView imageView = accountCardView.findViewById(R.id.account_logo);
        Drawable accountLogo = ContextCompat.getDrawable(accountCardView.getContext(),
                imageId[position]);
        imageView.setImageDrawable(accountLogo);
        imageView.setContentDescription(names[position]);
        TextView accountName = accountCardView.findViewById(R.id.account_name);
        accountName.setText(names[position]);
        TextView accountLogin = accountCardView.findViewById(R.id.account_login);
        accountLogin.setText(login[position]);
    }

    @Override
    public int getItemCount() {
        return Account.accounts.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;

        public ViewHolder(@NonNull CardView card) {
            super(card);
            cardView = card;
        }
    }
}
