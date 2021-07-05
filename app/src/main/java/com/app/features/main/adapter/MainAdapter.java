package com.app.features.main.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.R;
import com.app.databinding.ItemShowCrediteDataBinding;
import com.app.network.firebase.model.CreditCard;
import com.google.firebase.database.annotations.NotNull;



import java.util.ArrayList;

import static android.view.View.VISIBLE;
import static com.app.utils.ConstantApp.MASTER_CARD;
import static com.app.utils.ConstantApp.VISA_CARD;

public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private ArrayList<CreditCard> list = new ArrayList<>();
    private final OnClickListener listener;

    public MainAdapter(Context context, OnClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public MainAdapter(Context context, ArrayList<CreditCard> list, OnClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    public void removeAll() {
        this.list.clear();
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ItemShowCrediteDataBinding binding;

        public MyViewHolder(@NonNull @NotNull View itemView, ItemShowCrediteDataBinding binding) {
            super(itemView);
            this.binding = binding;
        }

        @SuppressLint("SetTextI18n")
        public void bind(final CreditCard creditCard, ItemShowCrediteDataBinding binding) {
            if (creditCard.getCardType() == MASTER_CARD) {
                binding.crediteCart.setBackgroundResource(R.drawable.ic_master_card);
                binding.ivMasterCardLogo.setVisibility(VISIBLE);
            } else if (creditCard.getCardType() == VISA_CARD){
                binding.crediteCart.setBackgroundResource(R.drawable.ic_visa);
                binding.ivMasterCardLogo.setVisibility(View.GONE);
            }
            binding.tvCardHolderName.setText(creditCard.getCardHolderName());
            binding.tvCvv.setText(creditCard.getCvv());
            binding.tvCardNumber.setText("**** **** ****" + creditCard.getCardNumber().substring(12) + "");
        }
    }

    public void addList(ArrayList<CreditCard> list) {
        this.list = list;
    }

    public CreditCard getItem(int pos) {
        return list.get(pos);
    }


    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        View view;
        ItemShowCrediteDataBinding binding = ItemShowCrediteDataBinding.inflate(((Activity) context).getLayoutInflater());
        view = binding.getRoot();
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 10, 0, 5);
        view.setLayoutParams(lp);
        viewHolder = new MyViewHolder(view, binding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.bind(getItem(position), myViewHolder.binding);
        holder.itemView.setOnClickListener(view -> {
            if (listener != null) {
                listener.onItemClick(getItem(position), holder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnClickListener {
        void onItemClick(CreditCard item, int position);
    }
}
