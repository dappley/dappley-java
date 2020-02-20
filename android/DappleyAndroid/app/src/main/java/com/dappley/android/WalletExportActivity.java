package com.dappley.android;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dappley.android.listener.BtnBackListener;
import com.dappley.android.util.CommonUtil;
import com.dappley.android.util.DuplicateUtil;
import com.dappley.java.core.po.Wallet;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WalletExportActivity extends AppCompatActivity {
    @BindView(R.id.btn_back)
    ImageButton btnBack;
    @BindView(R.id.txt_title)
    TextView tvTitle;

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_private_key)
    TextView tvPrivateKey;
    @BindView(R.id.tv_mnemonic)
    TextView tvMnemonic;
    @BindView(R.id.linear_private_key)
    LinearLayout linearPrivateKey;
    @BindView(R.id.linear_mnemonic)
    LinearLayout linearMnemonic;

    private Wallet wallet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_export);

        ButterKnife.bind(this);

        initView();

        initData();
    }

    private void initView() {
        tvTitle.setText(R.string.title_export_wallet);
        btnBack.setOnClickListener(new BtnBackListener(this));
    }

    private void initData() {
        Intent intent = getIntent();
        wallet = (Wallet) intent.getSerializableExtra("wallet");

        tvName.setText(CommonUtil.getNotNullString(wallet.getName()));
        tvAddress.setText(CommonUtil.getNotNullString(wallet.getAddress()));
        tvPrivateKey.setText(wallet.getPrivateKey().toString(16));
        if (wallet.getMnemonic() == null || wallet.getMnemonic().length() == 0) {
            linearMnemonic.setVisibility(View.GONE);
            linearPrivateKey.setVisibility(View.VISIBLE);
        } else {
            linearMnemonic.setVisibility(View.VISIBLE);
            linearPrivateKey.setVisibility(View.GONE);
            tvMnemonic.setText(CommonUtil.getNotNullString(wallet.getMnemonic()));
        }
    }

    @OnClick(R.id.btn_copy_address)
    void copyAddress() {
        if (DuplicateUtil.dupClickCheck()) {
            return;
        }
        copy2Clipboard(wallet.getAddress());
        Toast.makeText(this, R.string.note_wallet_copied, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btn_copy_private_key)
    void copyPrivateKey() {
        if (DuplicateUtil.dupClickCheck()) {
            return;
        }
        copy2Clipboard(wallet.getPrivateKey().toString(16));
        Toast.makeText(this, R.string.note_wallet_private_key_copied, Toast.LENGTH_SHORT).show();
    }

    private void copy2Clipboard(String string) {
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("data", string);
        clipboardManager.setPrimaryClip(clipData);
    }
}
