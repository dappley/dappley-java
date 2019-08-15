package com.dappley.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.TextView;

import com.dappley.android.listener.BtnBackListener;
import com.dappley.android.util.DuplicateUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WalletAddActivity extends AppCompatActivity {
    @BindView(R.id.btn_back)
    ImageButton btnBack;
    @BindView(R.id.txt_title)
    TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_add);

        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        tvTitle.setText(R.string.title_add_wallet);
        btnBack.setOnClickListener(new BtnBackListener(this));
    }

    @OnClick(R.id.btn_create)
    void createWallet() {
        if (DuplicateUtil.dupClickCheck()) {
            return;
        }
        startActivity(new Intent(this, WalletCreateActivity.class));
        finish();
    }

    @OnClick(R.id.btn_import)
    void importWallet() {
        if (DuplicateUtil.dupClickCheck()) {
            return;
        }
        startActivity(new Intent(this, WalletImportActivity.class));
        finish();
    }
}
