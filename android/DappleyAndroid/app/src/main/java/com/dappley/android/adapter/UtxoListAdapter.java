package com.dappley.android.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dappley.android.R;
import com.dappley.android.sdk.Dappley;
import com.dappley.android.sdk.po.Utxo;
import com.dappley.android.sdk.po.Wallet;
import com.dappley.android.sdk.util.HexUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UtxoListAdapter extends RecyclerView.Adapter<UtxoListAdapter.ViewHolder> {
    private Context context;
    private List<Utxo> utxos;
    private OnItemClickListener onItemClickListener;

    public UtxoListAdapter(Context context) {
        this.context = context;
        utxos = new ArrayList<>();
    }

    public void setList(List<Utxo> wallets) {
        this.utxos.clear();
        this.utxos.addAll(wallets);
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
        View viewItem = LayoutInflater.from(context).inflate(R.layout.item_utxo, viewGroup, false);
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

        viewHolder.tvTxid.setText("");
        viewHolder.tvAddress.setText("");
        viewHolder.tvValue.setText("-");

        Utxo utxo = utxos.get(position);
        if (utxo == null) {
            return;
        }
        if (utxo.getTxId() != null) {
            viewHolder.tvTxid.setText("tx hash: " + HexUtil.toHex(utxo.getTxId()));
        }
        if (utxo.getPublicKeyHash() != null) {
            viewHolder.tvAddress.setText("to: " + Dappley.formatAddress(utxo.getPublicKeyHash()));
        }
        if (utxo.getAmount() != null) {
            viewHolder.tvValue.setText(utxo.getAmount().toString());
        }
    }

    @Override
    public int getItemCount() {
        return utxos.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_txid)
        TextView tvTxid;
        @BindView(R.id.txt_address)
        TextView tvAddress;
        @BindView(R.id.txt_value)
        TextView tvValue;


        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClickListener {
        void onClick(View view, int position);
    }
}
