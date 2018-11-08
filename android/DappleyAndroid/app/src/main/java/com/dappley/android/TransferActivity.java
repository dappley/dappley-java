package com.dappley.android;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.dappley.android.adapter.WalletPagerAdapter;
import com.dappley.android.listener.BtnBackListener;
import com.dappley.android.sdk.Dappley;
import com.dappley.android.sdk.po.Wallet;
import com.dappley.android.util.CommonUtil;
import com.dappley.android.util.Constant;
import com.dappley.android.util.StorageUtil;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TransferActivity extends AppCompatActivity {
    @BindView(R.id.btn_back)
    ImageButton btnBack;
    @BindView(R.id.txt_title)
    TextView tvTitle;

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @BindView(R.id.et_to_address)
    EditText etToAddress;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_value)
    EditText etValue;

    WalletPagerAdapter walletPagerAdapter;
    List<Wallet> wallets;
    Wallet wallet;
    String toAddress;
    BigInteger balance;
    int selectedIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);

        ButterKnife.bind(this);

        initView();

        initData();

        loadData();
    }

    private void initView() {
        tvTitle.setText("钱包转账");
        btnBack.setOnClickListener(new BtnBackListener(this));

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadBalance();
            }
        });

        walletPagerAdapter = new WalletPagerAdapter(this);
        viewPager.setAdapter(walletPagerAdapter);
        viewPager.addOnPageChangeListener(pageChangeListener);
    }

    private void initData() {
        Intent intent = getIntent();
        wallet = (Wallet) intent.getSerializableExtra("wallet");
        toAddress = intent.getStringExtra("toAddress");

        if (toAddress != null && toAddress.length() > 0) {
            if (Dappley.validateAddress(toAddress)) {
                etToAddress.setText(toAddress);
            } else {
                Toast.makeText(this, "The code is not a valid wallet address", Toast.LENGTH_LONG).show();
            }
        }
    }

    @OnClick(R.id.btn_transfer)
    void tranfer() {
        if (wallet == null) {
            Toast.makeText(this, "no valid wallet", Toast.LENGTH_SHORT).show();
            return;
        }
        if (checkNull()) {
            return;
        }
        BigInteger amount = new BigInteger(etValue.getText().toString().trim());
        if (amount.compareTo(balance) > 0) {
            Toast.makeText(this, "balance is not enough", Toast.LENGTH_SHORT).show();
            return;
        }
        String toAddress = etToAddress.getText().toString().trim();
        boolean isSuccess = Dappley.sendTransaction(wallet.getAddress(), toAddress, amount, wallet.getPrivateKey());
        if (isSuccess) {
            Toast.makeText(this, "transfer success", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            finish();
        } else {
            Toast.makeText(this, "transfer failed, please check your inputs", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadData() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission
                    .WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "please allow read/write storage authority！", Toast.LENGTH_SHORT).show();
            }
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constant.REQ_PERM_STORAGE);
            return;
        }
        readWallets();

        checkDefault();

        refreshLayout.setRefreshing(true);
        loadBalance();
    }

    private void readWallets() {
        List<Wallet> wallets = new ArrayList<>();
        try {
            List<String> addresses = StorageUtil.getAddresses();
            if (addresses == null) {
                addresses = new ArrayList<>(1);
            }
            Wallet wallet;
            for (String address : addresses) {
                wallet = new Wallet();
                wallet.setAddress(address);
                wallets.add(wallet);
            }
        } catch (IOException e) {
            Toast.makeText(this, "read data failed", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        walletPagerAdapter.setList(wallets, -1);

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
        viewPager.setCurrentItem(selectedIndex, false);
    }

    private void loadBalance() {
        if (wallet == null) {
            Toast.makeText(this, "no valid wallet", Toast.LENGTH_SHORT).show();
            refreshLayout.setRefreshing(false);
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                balance = Dappley.getWalletBalance(wallet.getAddress());
                handler.sendEmptyMessage(Constant.MSG_TRANSFER_BALANCE);
            }
        }).start();
    }

    private boolean checkNull() {
        if (CommonUtil.isNull(etToAddress)) {
            Toast.makeText(this, "input receiver address", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (!Dappley.validateAddress(etToAddress.getText().toString().trim())) {
            Toast.makeText(this, "receiver address is illegal", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (CommonUtil.isNull(etPassword)) {
            Toast.makeText(this, "input wallet password", Toast.LENGTH_SHORT).show();
            return true;
        }
        try {
            String walletString = StorageUtil.getWallet(wallet.getAddress());
            Wallet wallet = Dappley.decryptWallet(walletString, etPassword.getText().toString());
            if (wallet == null || wallet.getPrivateKey() == null) {
                Toast.makeText(this, "password is not correct", Toast.LENGTH_SHORT).show();
                return true;
            }
            this.wallet = wallet;
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "password is not correct", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (CommonUtil.isNull(etValue)) {
            Toast.makeText(this, "input your transfer amount", Toast.LENGTH_SHORT).show();
            return true;
        }
        BigInteger value = null;
        try {
            value = new BigInteger(etValue.getText().toString().trim());
        } catch (Exception e) {
        }
        if (value == null || value.compareTo(BigInteger.ZERO) <= 0) {
            Toast.makeText(this, "input a correct transfer amount", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int position) {
            if (selectedIndex == position) {
                return;
            }
            selectedIndex = position;
            wallet = wallets.get(position);
            refreshLayout.setRefreshing(true);
            loadBalance();
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == Constant.MSG_TRANSFER_BALANCE) {
                wallet.setBalance(balance);
                wallets.set(selectedIndex, wallet);
                walletPagerAdapter.setList(wallets, selectedIndex);
                refreshLayout.setRefreshing(false);
            }
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Constant.REQ_PERM_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    readWallets();
                } else {
                    Toast.makeText(this, "please allow read storage authority！", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

}
