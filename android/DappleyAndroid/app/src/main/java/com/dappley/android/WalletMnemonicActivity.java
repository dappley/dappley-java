package com.dappley.android;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dappley.android.dialog.LoadingDialog;
import com.dappley.android.listener.BtnBackListener;
import com.dappley.android.sdk.Dappley;
import com.dappley.android.util.CommonUtil;
import com.dappley.android.util.Constant;
import com.dappley.android.util.StorageUtil;
import com.dappley.java.core.po.Wallet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WalletMnemonicActivity extends AppCompatActivity {
    private static final String TAG = "WalletMnemonicActivity";

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
        tvTitle.setText(R.string.title_create_wallet);
        btnBack.setOnClickListener(new BtnBackListener(this));
    }

    private void initData() {
        Intent intent = getIntent();
        wallet = (Wallet) intent.getSerializableExtra("wallet");
        password = intent.getStringExtra("password");
        type = intent.getIntExtra("type", Constant.REQ_WALLET_CREATE);
        if (type == Constant.REQ_WALLET_IMPORT) {
            tvTitle.setText(R.string.title_import_wallet);
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
                Toast.makeText(this, R.string.note_permittion_read, Toast.LENGTH_SHORT).show();
            }
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constant.REQ_PERM_STORAGE);
            return;
        }
        readWriteLocal();
    }

    private void readWriteLocal() {
        LoadingDialog.show(this);
        try {
            List<Wallet> wallets = StorageUtil.getWallets(this);
            if (wallets == null) {
                wallets = new ArrayList<>(1);
            }
            for (Wallet w : wallets) {
                if (w == null) {
                    continue;
                }
                if (w.getAddress() != null && w.getAddress().equals(wallet.getAddress())) {
                    Toast.makeText(this, R.string.note_wallet_exists, Toast.LENGTH_SHORT).show();
                    LoadingDialog.close();
                    return;
                }
            }
            Wallet wallet = Dappley.encryptWallet(this.wallet, password);
            // write to files
            StorageUtil.saveWallet(this, wallet);

            Dappley.addAddress(wallet.getAddress());

            Intent intent = new Intent(Constant.BROAD_WALLET_LIST_UPDATE);
            intent.putExtra("type", type);
            intent.putExtra("address", wallet.getAddress());
            sendBroadcast(intent);
            finish();
        } catch (IOException e) {
            Toast.makeText(this, R.string.note_read_failed, Toast.LENGTH_SHORT).show();
            Log.e(TAG, "readWriteLocal: ", e);
        }
        LoadingDialog.close();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Constant.REQ_PERM_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    readWriteLocal();
                } else {
                    Toast.makeText(this, R.string.note_permittion_read, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
