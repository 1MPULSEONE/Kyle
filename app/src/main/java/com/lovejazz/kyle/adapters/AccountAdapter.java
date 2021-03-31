package com.lovejazz.kyle.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.lovejazz.kyle.Account;
import com.lovejazz.kyle.R;

import java.util.List;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.ViewHolder> {
    private List<Account> accountsArrayList;

    public AccountAdapter(List<Account> accountsArrayList) {
        this.accountsArrayList = accountsArrayList;
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
        CardView accountCardView = holder.cardView;
        TextView accountName = accountCardView.findViewById(R.id.account_name);
        TextView accountEmail = accountCardView.findViewById(R.id.account_email);
        accountName.setText(accountsArrayList.get(position).getName());
        accountEmail.setText(accountsArrayList.get(position).getEmail());
        ImageView accountIcon = accountCardView.findViewById(R.id.account_icon);
        Glide.with(accountCardView)
                .load(accountsArrayList.get(position).getIconLink())
                .into(accountIcon);
        accountIcon.setContentDescription(accountsArrayList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return accountsArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;

        public ViewHolder(@NonNull CardView card) {
            super(card);
            cardView = card;
        }
    }
}
