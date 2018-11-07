package com.dappley.android;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dappley.android.listener.BtnBackListener;
import com.dappley.android.sdk.po.Wallet;
import com.dappley.android.util.CommonUtil;
import com.dappley.android.util.Constant;
import com.dappley.android.util.QrCodeUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WalletReceiveCodeActivity extends AppCompatActivity {
    @BindView(R.id.btn_back)
    ImageButton btnBack;
    @BindView(R.id.txt_title)
    TextView tvTitle;

    @BindView(R.id.img_code)
    ImageView imgCode;
    @BindView(R.id.tv_address)
    TextView tvAddress;

    private Wallet wallet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_receive_code);
        ButterKnife.bind(this);

        initView();

        initData();
    }

    private void initView() {
        tvTitle.setText("收款");
        btnBack.setOnClickListener(new BtnBackListener(this));
    }

    private void initData() {
        Intent intent = getIntent();
        wallet = (Wallet) intent.getSerializableExtra("wallet");

        tvAddress.setText(wallet.getAddress());

        showQrCode();
    }

    @OnClick(R.id.btn_copy)
    void copy() {
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("data", wallet.getAddress());
        clipboardManager.setPrimaryClip(clipData);
        Toast.makeText(this, "wallet address is copied", Toast.LENGTH_SHORT).show();
    }

    private void showQrCode() {
        Bitmap bitmap = QrCodeUtil.getQrCodeImage(wallet.getAddress(), Constant.QR_WIDTH, Constant.QR_HEIGHT);
        if (bitmap == null) {
            imgCode.setImageResource(R.drawable.empty);
            return;
        }
        imgCode.setImageBitmap(bitmap);
    }
}
