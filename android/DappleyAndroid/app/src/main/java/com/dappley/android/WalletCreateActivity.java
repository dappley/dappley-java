package com.dappley.android;

import android.content.Intent;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WalletCreateActivity extends AppCompatActivity {
    @BindView(R.id.btn_back)
    ImageButton btnBack;
    @BindView(R.id.txt_title)
    TextView tvTitle;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_password)
    EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_create);

        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        tvTitle.setText("创建钱包");
        btnBack.setOnClickListener(new BtnBackListener(this));
    }

    @OnClick(R.id.btn_create)
    void create() {
        if (checkNull()) {
            return;
        }
        Wallet wallet = Dappley.createWallet();
        if (wallet == null) {
            Toast.makeText(this, "create wallet failed, please try later", Toast.LENGTH_SHORT).show();
            return;
        }
        wallet.setName(etName.getText().toString());
        Intent intent = new Intent(this, WalletMnemonicActivity.class);
        intent.putExtra("wallet", wallet);
        intent.putExtra("password", etPassword.getText().toString());
        startActivity(intent);
        finish();
    }

    private boolean checkNull() {
        if (CommonUtil.isNull(etName)) {
            Toast.makeText(this, "input a wallet name", Toast.LENGTH_SHORT).show();
            etName.requestFocus();
            return true;
        }
        if (CommonUtil.isNull(etPassword)) {
            Toast.makeText(this, "input a password", Toast.LENGTH_SHORT).show();
            etPassword.requestFocus();
            return true;
        }
        return false;
    }
}
