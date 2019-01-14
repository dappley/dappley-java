package com.dappley.android;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.dappley.android.adapter.WalletReceivePagerAdapter;
import com.dappley.android.listener.BtnBackListener;
import com.dappley.android.util.CommonUtil;
import com.dappley.android.util.Constant;
import com.dappley.android.util.StorageUtil;
import com.dappley.java.core.po.Wallet;
import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WalletReceiveCodeActivity extends AppCompatActivity {
    private static final String TAG = "WalletReceiveCodeActivi";
    @BindView(R.id.btn_back)
    ImageButton btnBack;
    @BindView(R.id.txt_title)
    TextView tvTitle;
    @BindView(R.id.cycle_viewpager)
    HorizontalInfiniteCycleViewPager cycleViewPager;

    WalletReceivePagerAdapter walletReceivePagerAdapter;
    List<Wallet> wallets;
    Wallet wallet;
    int selectedIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_receive_code);
        ButterKnife.bind(this);

        initView();

        initData();

        loadData();
    }

    private void initView() {
        tvTitle.setText(R.string.title_receive);
        btnBack.setOnClickListener(new BtnBackListener(this));

        walletReceivePagerAdapter = new WalletReceivePagerAdapter(this);
    }

    private void initData() {
        Intent intent = getIntent();
        wallet = (Wallet) intent.getSerializableExtra("wallet");
    }

    private void loadData() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission
                    .WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this, R.string.note_permittion_read, Toast.LENGTH_SHORT).show();
            }
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constant.REQ_PERM_STORAGE);
            return;
        }
        readWallets();

        checkDefault();
    }

    private void readWallets() {
        List<Wallet> wallets = null;
        try {
            wallets = StorageUtil.getWallets(this);
            if (wallets == null) {
                wallets = new ArrayList<>(1);
            }
        } catch (IOException e) {
            Toast.makeText(this, R.string.note_read_failed, Toast.LENGTH_SHORT).show();
            Log.e(TAG, "readWallets: ", e);
        }
        walletReceivePagerAdapter.setList(wallets);

        this.wallets = wallets;
    }

    private void checkDefault() {
        if (wallets == null || wallets.size() == 0) {
            return;
        }
        if (this.wallet == null) {
            this.wallet = wallets.get(0);
            selectedIndex = 0;
            return;
        }
        Wallet wallet;
        for (int i = 0; i < wallets.size(); i++) {
            wallet = wallets.get(i);
            if (wallet == null) {
                continue;
            }
            if (wallet.getAddress() == null) {
                continue;
            }
            if (wallet.getAddress().equals(this.wallet.getAddress())) {
                selectedIndex = i;
                break;
            }
        }
        cycleViewPager.setAdapter(walletReceivePagerAdapter);
        cycleViewPager.setCurrentItem(selectedIndex);
    }

    @OnClick(R.id.btn_copy)
    void copy() {
        int current = cycleViewPager.getRealItem();
        if (current < 0 || current >= wallets.size()) {
            return;
        }
        Wallet wallet = wallets.get(current);
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("data", CommonUtil.getNotNullString(wallet.getAddress()));
        clipboardManager.setPrimaryClip(clipData);
        Toast.makeText(this, R.string.note_wallet_copied, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Constant.REQ_PERM_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    readWallets();
                } else {
                    Toast.makeText(this, R.string.note_permittion_read, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
