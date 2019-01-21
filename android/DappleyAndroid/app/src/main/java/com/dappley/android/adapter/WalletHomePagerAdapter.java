package com.dappley.android.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dappley.android.R;
import com.dappley.java.core.po.Wallet;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WalletHomePagerAdapter extends PagerAdapter {
    private Context context;
    private List<Wallet> wallets;
    private ViewPager viewPager;

    public WalletHomePagerAdapter(Context context, ViewPager viewPager) {
        this.context = context;
        this.wallets = new ArrayList<>();
        this.viewPager = viewPager;
    }

    public void setList(List<Wallet> wallets) {
        this.wallets.clear();
        this.wallets.addAll(wallets);
        notifyDataSetChanged();
    }

    public void updateCurrent(int currentPage, BigInteger balance) {
        TextView tvBalance = viewPager.findViewWithTag("balance" + currentPage);
        if (tvBalance == null || balance == null) {
            return;
        }
        tvBalance.setText(balance.toString());
    }

    @Override
    public int getCount() {
        return this.wallets.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_wallet_card, container, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.tvAddress.setText("");
        viewHolder.tvValue.setText("-");
        viewHolder.tvValue.setTag("balance" + position);

        Wallet wallet = wallets.get(position);
        if (wallet != null) {
            if (wallet.getName() != null) {
                viewHolder.tvName.setText(wallet.getName());
            }
            if (wallet.getAddress() != null) {
                viewHolder.tvAddress.setText(wallet.getAddress());
            }
            if (wallet.getBalance() != null) {
                viewHolder.tvValue.setText(wallet.getBalance().toString());
            }
        }

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    static class ViewHolder {
        @BindView(R.id.txt_name)
        TextView tvName;
        @BindView(R.id.txt_address)
        TextView tvAddress;
        @BindView(R.id.txt_value)
        TextView tvValue;

        public ViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }
    }
}
