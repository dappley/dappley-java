package com.dappley.android;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.dappley.android.listener.BtnBackListener;
import com.dappley.android.sdk.Dappley;
import com.dappley.android.sdk.po.Wallet;
import com.dappley.android.util.CommonUtil;
import com.dappley.android.util.Constant;
import com.dappley.android.util.StorageUtil;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;

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

    @BindView(R.id.txt_address)
    TextView tvAddress;
    @BindView(R.id.txt_value)
    TextView tvValue;

    @BindView(R.id.et_to_address)
    EditText etToAddress;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_value)
    EditText etValue;

    Wallet wallet;
    BigInteger balance;

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
        tvTitle.setText("钱包");
        btnBack.setOnClickListener(new BtnBackListener(this));

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
    }

    private void initData() {
        Intent intent = getIntent();
        wallet = (Wallet) intent.getSerializableExtra("wallet");

        tvAddress.setText(wallet.getAddress());
    }

    @OnClick(R.id.btn_transfer)
    void tranfer() {
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
            Toast.makeText(this, "transfer failed, please your inputs", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                loadBalance();
                handler.sendEmptyMessage(Constant.MSG_TRANSFER_BALANCE);
            }
        }).start();
    }

    private void loadBalance() {
        balance = Dappley.getWalletBalance(wallet.getAddress());
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

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == Constant.MSG_TRANSFER_BALANCE) {
                tvValue.setText(balance.toString());
            }
        }
    };
}
