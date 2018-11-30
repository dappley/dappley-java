package com.dappley.android.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dappley.android.R;
import com.dappley.android.util.StorageUtil;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MeFragment extends Fragment {
    @BindView(R.id.txt_wallet_num)
    TextView tvWalletNum;

    public MeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        loadData();
    }

    private void loadData() {
        try {
            List<String> walletAddresses = StorageUtil.getAddresses();
            if (walletAddresses == null) {
                tvWalletNum.setText("0");
            } else {
                tvWalletNum.setText("" + walletAddresses.size());
            }
        } catch (IOException e) {
            e.printStackTrace();
            tvWalletNum.setText("0");
        }
    }

    @OnClick(R.id.linear_modify_password)
    void modifyPassword() {

    }
}
