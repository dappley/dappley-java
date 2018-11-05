package com.dappley.android;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dappley.android.listener.BtnBackListener;
import com.dappley.android.sdk.Dappley;
import com.dappley.android.sdk.po.Wallet;
import com.dappley.android.util.CommonUtil;
import com.dappley.android.util.Constant;
import com.dappley.android.util.StorageUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WalletMnemonicActivity extends AppCompatActivity {
    @BindView(R.id.btn_back)
    ImageButton btnBack;
    @BindView(R.id.txt_title)
    TextView tvTitle;
    @BindView(R.id.txt_name)
    TextView tvName;
    @BindView(R.id.txt_address)
    TextView tvAddress;
    @BindView(R.id.txt_private_key)
    TextView tvPrivateKey;
    @BindView(R.id.txt_mnemonic)
    TextView tvMneonic;
    @BindView(R.id.linear_mnemonic)
    LinearLayout linearMnemonic;

    private Wallet wallet;
    private String password;
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_mnemonic);

        ButterKnife.bind(this);

        initView();

        initData();
    }

    private void initView() {
        tvTitle.setText("创建钱包");
        btnBack.setOnClickListener(new BtnBackListener(this));
    }

    private void initData() {
        Intent intent = getIntent();
        wallet = (Wallet) intent.getSerializableExtra("wallet");
        password = intent.getStringExtra("password");
        type = intent.getIntExtra("type", 1);
        if (type == Constant.REQ_WALLET_IMPORT) {
            tvTitle.setText("导入钱包");
        }

        tvName.setText(CommonUtil.getNotNullString(wallet.getName()));
        tvAddress.setText(CommonUtil.getNotNullString(wallet.getAddress()));
        tvPrivateKey.setText(wallet.getPrivateKey().toString(16));
        if (wallet.getMnemonic() == null) {
            linearMnemonic.setVisibility(View.GONE);
        } else {
            linearMnemonic.setVisibility(View.VISIBLE);
            tvMneonic.setText(CommonUtil.getNotNullString(wallet.getMnemonic()));
        }
    }

    @OnClick(R.id.btn_save)
    void saveToLocal() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission
                    .WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "please allow read/write storage authority！", Toast.LENGTH_SHORT).show();
            }
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constant.REQ_PERM_STORAGE);
            return;
        }
        readWriteLocal();
    }

    private void readWriteLocal() {
        try {
            List<String> addresses = StorageUtil.getAddresses();
            if (addresses == null) {
                addresses = new ArrayList<>(1);
            }
            if (addresses.contains(wallet.getAddress())) {
                Toast.makeText(this, "wallet address has been existed", Toast.LENGTH_SHORT).show();
                return;
            }
            addresses.add(wallet.getAddress());
            Map<String, byte[]> walletMap = StorageUtil.getWalletMap();
            if (walletMap == null) {
                walletMap = new HashMap<>(1);
            }
            byte[] encryptWallet = Dappley.encryptWallet(wallet, password);
            walletMap.put(wallet.getAddress(), encryptWallet);
            System.out.println(wallet.getMnemonic());
            // write to files
            StorageUtil.saveAddresses(addresses);
            StorageUtil.saveWalletMap(walletMap);

            Dappley.addAddress(wallet.getAddress());

            Intent intent = new Intent(Constant.BROAD_WALLET_LIST_UPDATE);
            sendBroadcast(intent);
            finish();
        } catch (IOException e) {
            Toast.makeText(this, "read/write data failed", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Constant.REQ_PERM_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    readWriteLocal();
                } else {
                    Toast.makeText(this, "please allow read/write storage authority！", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
