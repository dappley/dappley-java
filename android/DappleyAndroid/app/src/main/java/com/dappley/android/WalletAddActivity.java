package com.dappley.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.dappley.android.listener.BtnBackListener;

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
        tvTitle.setText("添加钱包");
        btnBack.setOnClickListener(new BtnBackListener(this));
    }

    @OnClick(R.id.btn_create)
    void createWallet() {
        startActivity(new Intent(this, WalletCreateActivity.class));
        finish();
    }

    @OnClick(R.id.btn_import)
    void importWallet() {
        startActivity(new Intent(this, WalletImportActivity.class));
        finish();
    }
}
