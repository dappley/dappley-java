package com.dappley.android.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.dappley.android.R;
import com.dappley.java.core.po.Wallet;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

public class WalletSelectListAdapter extends RecyclerView.Adapter<WalletSelectListAdapter.ViewHolder> {
    private Context context;
    private List<Wallet> wallets;
    private OnItemClickListener onItemClickListener;
    private int selectedIndex = -1;
    private BigInteger baseValue;

    public WalletSelectListAdapter(Context context, BigInteger baseValue) {
        if (baseValue == null) {
            throw new NullPointerException("baseValue cannot be null");
        }
        this.context = context;
        this.wallets = new ArrayList<>();
        this.baseValue = baseValue;
    }

    public void setList(List<Wallet> wallets) {
        this.wallets.clear();
        this.wallets.addAll(wallets);
        notifyDataSetChanged();
    }

    public void setSelectedIndex(int selectedIndex) {
        if (selectedIndex < 0 || selectedIndex > this.wallets.size() - 1) {
            return;
        }
        this.selectedIndex = selectedIndex;
        notifyDataSetChanged();
    }

    public void addOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (context == null) {
            context = viewGroup.getContext();
        }
        View viewItem = LayoutInflater.from(context).inflate(R.layout.item_wallet_select, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(viewItem);
        if (onItemClickListener != null) {
            viewItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onClick(v, (int) v.getTag());
                }
            });
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.itemView.setTag(position);

        viewHolder.tvAddress.setText("");

        Wallet wallet = wallets.get(position);
        if (wallet == null) {
            return;
        }
        if (wallet.getName() != null) {
            viewHolder.tvName.setText(wallet.getName());
        }
        if (wallet.getAddress() != null) {
            viewHolder.tvAddress.setText(wallet.getAddress());
        }
        if (wallet.getBalance() != null) {
            viewHolder.tvValue.setText(wallet.getBalanceDW());
        }
        if (position == selectedIndex) {
            viewHolder.chbWallet.setChecked(true);
        } else {
            viewHolder.chbWallet.setChecked(false);
        }
        if (wallet.getBalance() == null || wallet.getBalance().compareTo(baseValue) < 0) {
            viewHolder.itemView.setClickable(false);
        } else {
            viewHolder.itemView.setClickable(true);
        }
    }

    @Override
    public int getItemCount() {
        return wallets.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_name)
        TextView tvName;
        @BindView(R.id.txt_address)
        TextView tvAddress;
        @BindView(R.id.txt_value)
        TextView tvValue;
        @BindView(R.id.chb_wallet)
        CheckBox chbWallet;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClickListener {
        void onClick(View view, int position);
    }
}
