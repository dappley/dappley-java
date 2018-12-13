package com.dappley.android.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dappley.android.ModifyPasswordActivity;
import com.dappley.android.R;
import com.dappley.android.sdk.po.Wallet;
import com.dappley.android.util.Constant;
import com.dappley.android.util.StorageUtil;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MeFragment extends Fragment {
    private static final String TAG = "MeFragment";

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

    public void loadData() {
        try {
            List<Wallet> wallets = StorageUtil.getWallets(getActivity());
            if (wallets == null) {
                tvWalletNum.setText("0");
            } else {
                tvWalletNum.setText("" + wallets.size());
            }
        } catch (IOException e) {
            e.printStackTrace();
            tvWalletNum.setText("0");
        }
    }

    @OnClick(R.id.linear_modify_password)
    void modifyPassword() {
        Intent intent = new Intent(getActivity(), ModifyPasswordActivity.class);
        getActivity().startActivityForResult(intent, Constant.REQ_ACT_MODIFY_PASSWORD);
    }
}
