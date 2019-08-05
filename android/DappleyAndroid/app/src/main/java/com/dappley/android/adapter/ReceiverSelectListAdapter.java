package com.dappley.android.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dappley.android.R;
import com.dappley.android.bean.Receiver;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReceiverSelectListAdapter extends RecyclerView.Adapter<ReceiverSelectListAdapter.ViewHolder> {
    private static int SHORT_ADDR_LENGTH = 4;
    private Context context;
    private List<Receiver> receivers;
    private OnItemClickListener onItemClickListener;

    public ReceiverSelectListAdapter(Context context) {
        this.context = context;
        this.receivers = new ArrayList<>();
    }

    public void setList(List<Receiver> receivers) {
        this.receivers.clear();
        this.receivers.addAll(receivers);
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
        View viewItem = LayoutInflater.from(context).inflate(R.layout.item_receiver_select, viewGroup, false);
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

        Receiver receiver = receivers.get(position);
        if (receiver == null) {
            return;
        }

        if (receiver.getAddress() != null) {
            viewHolder.tvAddressLetter.setText(getShortAddr(receiver.getAddress(), 1));
            viewHolder.tvAddressShort.setText(getShortAddr(receiver.getAddress(), SHORT_ADDR_LENGTH));
            viewHolder.tvAddress.setText(receiver.getAddress());
        }
    }

    @Override
    public int getItemCount() {
        return receivers.size();
    }

    private String getShortAddr(String address, int num) {
        if (address == null || address.length() == 0) {
            return address;
        }
        if (address.length() <= num) {
            return address;
        }
        return address.substring(address.length() - num, address.length());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_address_letter)
        TextView tvAddressLetter;
        @BindView(R.id.txt_address_short)
        TextView tvAddressShort;
        @BindView(R.id.txt_address)
        TextView tvAddress;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClickListener {
        void onClick(View view, int position);
    }

}
