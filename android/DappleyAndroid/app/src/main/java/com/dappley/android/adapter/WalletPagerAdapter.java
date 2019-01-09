package com.dappley.android.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
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

public class WalletPagerAdapter extends PagerAdapter {
    private static int[] ICONS = new int[]{R.drawable.icon_wallet_1, R.drawable.icon_wallet_2, R.drawable.icon_wallet_3, R.drawable.icon_wallet_4};
    private Context context;
    private List<Wallet> wallets;
    private List<View> views;
    private int currentPage = -1;

    public WalletPagerAdapter(Context context) {
        this.context = context;
        this.wallets = new ArrayList<>();
        this.views = new ArrayList<>();
    }

    public void setList(List<Wallet> wallets, int currentPage) {
        this.wallets.clear();
        this.wallets.addAll(wallets);
        this.currentPage = currentPage;
        notifyDataSetChanged();
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
        View view = null;
        if (position <= views.size() - 1) {
            view = views.get(position);
        }
        ViewHolder viewHolder = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_wallet, container, false);
            viewHolder = new ViewHolder(view);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tvAddress.setText("");
        viewHolder.tvValue.setText("-");

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
        viewHolder.iconWallet.setImageResource(ICONS[position % 4]);

        view.setTag(viewHolder);

        fillEmpty(views, position);
        views.set(position, view);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        if (position > views.size() - 1) {
            return;
        }
        View view = views.get(position);
        if (view != null) {
            container.removeView(view);
        }
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        if (views.indexOf(object) == currentPage) {
            currentPage = -1;
            return POSITION_NONE;
        }
        return super.getItemPosition(object);
    }

    private void fillEmpty(List<View> views, int position) {
        if (views.size() > position) {
            return;
        }
        int size = views.size();
        for (int i = size; i <= position; i++) {
            views.add(null);
        }
    }

    static class ViewHolder {
        @BindView(R.id.icon_wallet)
        ImageView iconWallet;
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
