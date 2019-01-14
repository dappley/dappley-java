package com.dappley.android.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dappley.android.R;
import com.dappley.android.util.Constant;
import com.dappley.android.util.QrCodeUtil;
import com.dappley.java.core.po.Wallet;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WalletReceivePagerAdapter extends PagerAdapter {
    private Context context;
    private List<Wallet> wallets;

    public WalletReceivePagerAdapter(Context context) {
        this.context = context;
        this.wallets = new ArrayList<>();
    }

    public void setList(List<Wallet> wallets) {
        this.wallets.clear();
        this.wallets.addAll(wallets);
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
        View view = LayoutInflater.from(context).inflate(R.layout.item_wallet_code, container, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.tvAddress.setText("");

        Wallet wallet = wallets.get(position);
        if (wallet != null) {
            if (wallet.getName() != null) {
                viewHolder.tvName.setText(wallet.getName());
            }
            if (wallet.getAddress() != null) {
                viewHolder.tvAddress.setText(wallet.getAddress());
            }
        }
        Bitmap bitmap = QrCodeUtil.getQrCodeImage(wallet.getAddress(), Constant.QR_WIDTH, Constant.QR_HEIGHT);
        if (bitmap == null) {
            viewHolder.imgCode.setImageResource(R.drawable.empty);
        } else {
            viewHolder.imgCode.setImageBitmap(bitmap);
        }

        view.setTag(viewHolder);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(final ViewGroup container, final int position, final Object object) {
        container.removeView((View) object);
    }

    static class ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_address)
        TextView tvAddress;
        @BindView(R.id.img_code)
        ImageView imgCode;

        public ViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }
    }
}
