package com.dappley.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.dappley.android.listener.BtnBackListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WalletCreateActivity extends AppCompatActivity {
    @BindView(R.id.btn_back)
    ImageButton btnBack;
    @BindView(R.id.txt_title)
    TextView tvTitle;

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
}
