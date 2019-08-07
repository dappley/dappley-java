package com.dappley.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.dappley.android.dialog.LoadingDialog;
import com.dappley.android.listener.BtnBackListener;
import com.dappley.android.sdk.Dappley;
import com.dappley.android.util.CommonUtil;
import com.dappley.android.util.Constant;
import com.dappley.android.util.EventUtil;
import com.dappley.android.util.StorageUtil;
import com.dappley.java.core.po.Wallet;

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
        tvTitle.setText(R.string.title_create_wallet);
        btnBack.setOnClickListener(new BtnBackListener(this));
    }

    @OnClick(R.id.btn_create)
    void create() {
        if (checkNull()) {
            return;
        }
        LoadingDialog.show(this);
        Wallet wallet = Dappley.createWallet();
        if (wallet == null) {
            Toast.makeText(this, R.string.note_create_wallet_failed, Toast.LENGTH_SHORT).show();
            LoadingDialog.close();
            return;
        }
        LoadingDialog.close();
        wallet.setName(etName.getText().toString());
        Intent intent = new Intent(this, WalletMnemonicActivity.class);
        intent.putExtra("wallet", wallet);
        intent.putExtra("password", etPassword.getText().toString());
        intent.putExtra("type", Constant.REQ_WALLET_CREATE);
        startActivity(intent);
        finish();
    }

    private boolean checkNull() {
        if (CommonUtil.isNull(etName)) {
            Toast.makeText(this, R.string.note_no_wallet_name, Toast.LENGTH_SHORT).show();
            etName.requestFocus();
            return true;
        }
        if (CommonUtil.isNull(etPassword)) {
            Toast.makeText(this, R.string.note_no_pasword, Toast.LENGTH_SHORT).show();
            etPassword.requestFocus();
            return true;
        }
        boolean isPassCorrect = StorageUtil.checkPassword(this, etPassword.getText().toString());
        if (!isPassCorrect) {
            Toast.makeText(this, R.string.note_error_password, Toast.LENGTH_SHORT).show();
            etPassword.requestFocus();
            return true;
        }
        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        EventUtil.handleSoftInput(ev, this);
        return super.dispatchTouchEvent(ev);
    }
}
