package com.lovejazz.kyle.adapters;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.lovejazz.kyle.Account;
import com.lovejazz.kyle.R;
import com.lovejazz.kyle.activities.AccountActivity;
import com.lovejazz.kyle.activities.CategoryActivity;

import java.util.List;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.ViewHolder> {
    private List<Account> accountsArrayList;
    private Activity activity;
    private static final String TAG = "AccountAdapter";

    public AccountAdapter(List<Account> accountsArrayList, CategoryActivity activity) {
        this.accountsArrayList = accountsArrayList;
        this.activity = activity;
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
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
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
        accountCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, AccountActivity.class);
                intent.putExtra("ID", accountsArrayList.get(position).getId());
                Log.d(TAG, accountsArrayList.get(position).getId() + " - accountId");
                activity.startActivity(intent);
            }
        });
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
