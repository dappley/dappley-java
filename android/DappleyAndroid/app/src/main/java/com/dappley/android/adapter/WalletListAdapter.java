package com.dappley.android.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dappley.android.R;
import com.dappley.java.core.po.Wallet;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WalletListAdapter extends RecyclerView.Adapter<WalletListAdapter.ViewHolder> {
    private static int[] ICONS = new int[]{R.drawable.icon_wallet_1, R.drawable.icon_wallet_2, R.drawable.icon_wallet_3, R.drawable.icon_wallet_4};
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_NORMAL = 1;

    private Context context;
    private List<Wallet> wallets;
    private OnItemClickListener onItemClickListener;

    public WalletListAdapter(Context context) {
        this.context = context;
        wallets = new ArrayList<>();
    }

    public void setList(List<Wallet> wallets) {
        this.wallets.clear();
        this.wallets.addAll(wallets);
        notifyDataSetChanged();
    }

    public void addOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        }
        return TYPE_NORMAL;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (context == null) {
            context = viewGroup.getContext();
        }
        View viewItem = null;
        if (viewType == TYPE_HEADER) {
            viewItem = LayoutInflater.from(context).inflate(R.layout.item_wallet_header, viewGroup, false);
        } else if (viewType == TYPE_NORMAL) {
            viewItem = LayoutInflater.from(context).inflate(R.layout.item_wallet, viewGroup, false);
        }
        ViewHolder viewHolder = new ViewHolder(viewItem, viewType);
        if (onItemClickListener != null) {
            viewItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onClick(v, (int) v.getTag());
                }
            });
            viewItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemClickListener.onLongClick(v, (int) v.getTag());
                    return false;
                }
            });
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.itemView.setTag(position);

        viewHolder.tvAddress.setText("");
        viewHolder.tvValue.setText("-");

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
            viewHolder.tvValue.setText(wallet.getBalance().toString());
        }
        if (position > 0) {
            viewHolder.iconWallet.setImageResource(ICONS[(position - 1) % ICONS.length]);
        }
    }

    @Override
    public int getItemCount() {
        return wallets.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iconWallet;
        @BindView(R.id.txt_name)
        TextView tvName;
        @BindView(R.id.txt_address)
        TextView tvAddress;
        @BindView(R.id.txt_value)
        TextView tvValue;


        public ViewHolder(View itemView, int viewType) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            if (viewType == TYPE_NORMAL) {
                iconWallet = itemView.findViewById(R.id.icon_wallet);
            }
        }
    }

    public interface OnItemClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }
}
