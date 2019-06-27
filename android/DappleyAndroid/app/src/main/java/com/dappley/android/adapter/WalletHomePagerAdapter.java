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
import com.dappley.java.core.util.CoinUtil;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WalletHomePagerAdapter extends PagerAdapter {
    private Context context;
    private List<Wallet> wallets;
    private ViewPager viewPager;
    private Map<Integer, Set<View>> viewsMap;

    public WalletHomePagerAdapter(Context context, ViewPager viewPager) {
        this.context = context;
        this.wallets = new ArrayList<>();
        this.viewPager = viewPager;
        this.viewsMap = new HashMap<>();
    }

    public void setList(List<Wallet> wallets) {
        this.wallets.clear();
        this.wallets.addAll(wallets);
        this.viewsMap.clear();
    }

    public void updateCurrent(int currentPage, BigInteger balance) {
        Set<View> views = viewsMap.get(currentPage);
        if (views == null) {
            return;
        }
        for (View view : views) {
            ((TextView) view).setText(CoinUtil.getDwWith2Points(balance));
        }
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
                viewHolder.tvValue.setText(CoinUtil.getDwWith2Points(wallet.getBalance()));
            }
        }
        Set<View> valueViews = viewsMap.get(position);
        if (valueViews == null) {
            valueViews = new HashSet<>();
        }
        valueViews.add(viewHolder.tvValue);
        viewsMap.put(position, valueViews);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        View view = (View) object;
        TextView tvValue = view.findViewById(R.id.txt_value);
        Set<View> valueViews = viewsMap.get(position);
        if (valueViews != null && tvValue != null) {
            valueViews.remove(tvValue);
        }
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
