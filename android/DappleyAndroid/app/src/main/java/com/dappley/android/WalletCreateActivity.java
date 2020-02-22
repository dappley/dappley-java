package com.dappley.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.dappley.android.dialog.LoadingDialog;
import com.dappley.android.dialog.WalletPasswordDialog;
import com.dappley.android.listener.BtnBackListener;
import com.dappley.android.sdk.Dappley;
import com.dappley.android.util.CommonUtil;
import com.dappley.android.util.Constant;
import com.dappley.android.util.DuplicateUtil;
import com.dappley.android.util.EventUtil;
import com.dappley.android.util.StorageUtil;
import com.dappley.java.core.po.Wallet;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WalletCreateActivity extends AppCompatActivity {
    private static final String TAG = "WalletCreateActivity";

    @BindView(R.id.btn_back)
    ImageButton btnBack;
    @BindView(R.id.txt_title)
    TextView tvTitle;
    @BindView(R.id.et_name)
    EditText etName;

    WalletPasswordDialog walletPasswordDialog;

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
        if (DuplicateUtil.dupClickCheck()) {
            return;
        }
        if (checkNull()) {
            return;
        }
        if (walletPasswordDialog == null) {
            walletPasswordDialog = new WalletPasswordDialog(this, new WalletPasswordDialog.OnClickListener() {
                @Override
                public void onConfirm(String password) {
                    onPasswordInput(password);
                }
            });
        }
        walletPasswordDialog.show();
    }

    private boolean checkNull() {
        if (CommonUtil.isNull(etName)) {
            Toast.makeText(this, R.string.note_no_wallet_name, Toast.LENGTH_SHORT).show();
            etName.requestFocus();
            return true;
        }
        return false;
    }

    private void onPasswordInput(String password) {
        if (password.length() == 0) {
            Toast.makeText(this, R.string.note_no_password, Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            boolean isPassCorrect = StorageUtil.checkPassword(this, password);
            if (!isPassCorrect) {
                Toast.makeText(this, R.string.note_error_password, Toast.LENGTH_SHORT).show();
                return;
            }

            walletPasswordDialog.close();

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
            intent.putExtra("password", password);
            intent.putExtra("type", Constant.REQ_WALLET_CREATE);
            startActivity(intent);
            finish();
        } catch (Exception e) {
            Log.e(TAG, "onPasswordInput: ", e);
            Toast.makeText(this, R.string.note_error_password, Toast.LENGTH_SHORT).show();
            return;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        EventUtil.handleSoftInput(ev, this);
        return super.dispatchTouchEvent(ev);
    }
}
