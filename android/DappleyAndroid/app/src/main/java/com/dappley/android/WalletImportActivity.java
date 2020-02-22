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

import java.math.BigInteger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WalletImportActivity extends AppCompatActivity {
    private static final String TAG = "WalletImportActivity";

    @BindView(R.id.btn_back)
    ImageButton btnBack;
    @BindView(R.id.txt_title)
    TextView tvTitle;

    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_pv_mnemonic)
    EditText etPvMnemonic;

    WalletPasswordDialog walletPasswordDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_import);

        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        tvTitle.setText(R.string.title_import_wallet);
        btnBack.setOnClickListener(new BtnBackListener(this));
    }

    @OnClick(R.id.btn_import)
    void importWallet() {
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
        if (CommonUtil.isNull(etPvMnemonic)) {
            Toast.makeText(this, R.string.note_no_pv_mnemonic, Toast.LENGTH_SHORT).show();
            etPvMnemonic.requestFocus();
            return true;
        }
        if (isRightPrivateKey(etPvMnemonic.getText().toString())) {
            return false;
        }
        if (isRightMnemonics(etPvMnemonic.getText().toString())) {
            return false;
        }
        Toast.makeText(this, R.string.note_error_pv_mnemonic, Toast.LENGTH_SHORT).show();
        etPvMnemonic.requestFocus();
        return true;
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
            Wallet wallet = null;
            try {
                if (isRightMnemonics(etPvMnemonic.getText().toString())) {
                    wallet = Dappley.importWalletFromMnemonic(etPvMnemonic.getText().toString());
                } else {
                    wallet = Dappley.importWalletFromPrivateKey(etPvMnemonic.getText().toString());
                }
            } catch (Exception e) {
                Log.e(TAG, "importWallet: ", e);
            }
            LoadingDialog.close();
            if (wallet == null || wallet.getPrivateKey() == null) {
                Toast.makeText(this, R.string.note_error_import_wallet, Toast.LENGTH_SHORT).show();
                return;
            }
            wallet.setName(etName.getText().toString());
            Intent intent = new Intent(this, WalletMnemonicActivity.class);
            intent.putExtra("wallet", wallet);
            intent.putExtra("password", password);
            intent.putExtra("type", Constant.REQ_WALLET_IMPORT);
            startActivity(intent);
            finish();
        } catch (Exception e) {
            Log.e(TAG, "onPasswordInput: ", e);
            Toast.makeText(this, R.string.note_error_password, Toast.LENGTH_SHORT).show();
            return;
        }
    }

    private boolean isRightPrivateKey(String text) {
        if (text == null || text.length() == 0) {
            return false;
        }
        BigInteger bigInteger;
        try {
            bigInteger = new BigInteger(etPvMnemonic.getText().toString(), 16);
        } catch (Exception e) {
            return false;
        }
//        int byte32 = 32 * 8;
//        if (bigInteger.toString(2).length() != byte32 && bigInteger.toString(2).length() != byte32 - 1) {
//            Toast.makeText(this, R.string.note_error_private_key, Toast.LENGTH_SHORT).show();
//            etPrivateKey.requestFocus();
//            return false;
//        }
        return true;
    }

    private boolean isRightMnemonics(String text) {
        if (text == null || text.length() == 0) {
            return false;
        }
        return text.split("\\s+").length == 12;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        EventUtil.handleSoftInput(ev, this);
        return super.dispatchTouchEvent(ev);
    }
}
