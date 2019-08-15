package com.dappley.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.dappley.android.dialog.LoadingDialog;
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
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_mnemonic)
    EditText etMnemonic;
    @BindView(R.id.et_private_key)
    EditText etPrivateKey;

    @BindView(R.id.linear_mnemonic)
    LinearLayout linearMnemonic;
    @BindView(R.id.linear_private_key)
    LinearLayout linearPrivateKey;
    @BindView(R.id.rb_mnemoic)
    RadioButton rbMnemonic;
    @BindView(R.id.rb_private_key)
    RadioButton rbPrivateKey;

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

    @OnClick({R.id.rb_mnemoic, R.id.rb_private_key})
    void typeClicked(View view) {
        if (view.getId() == R.id.rb_mnemoic) {
            linearMnemonic.setVisibility(View.VISIBLE);
            linearPrivateKey.setVisibility(View.GONE);
        } else {
            linearPrivateKey.setVisibility(View.VISIBLE);
            linearMnemonic.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.btn_import)
    void importWallet() {
        if (DuplicateUtil.dupClickCheck()) {
            return;
        }
        if (checkNull()) {
            return;
        }
        LoadingDialog.show(this);
        Wallet wallet = null;
        try {
            if (rbMnemonic.isChecked()) {
                wallet = Dappley.importWalletFromMnemonic(etMnemonic.getText().toString());
            } else {
                wallet = Dappley.importWalletFromPrivateKey(etPrivateKey.getText().toString());
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
        intent.putExtra("password", etPassword.getText().toString());
        intent.putExtra("type", Constant.REQ_WALLET_IMPORT);
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
            Toast.makeText(this, R.string.note_no_password, Toast.LENGTH_SHORT).show();
            etPassword.requestFocus();
            return true;
        }
        if (rbMnemonic.isChecked() && CommonUtil.isNull(etMnemonic)) {
            Toast.makeText(this, R.string.note_no_mnemonic, Toast.LENGTH_SHORT).show();
            etMnemonic.requestFocus();
            return true;
        }
        if (rbPrivateKey.isChecked() && CommonUtil.isNull(etPrivateKey)) {
            Toast.makeText(this, R.string.note_no_private_key, Toast.LENGTH_SHORT).show();
            etPrivateKey.requestFocus();
            return true;
        }
        boolean isPassCorrect = StorageUtil.checkPassword(this, etPassword.getText().toString());
        if (!isPassCorrect) {
            Toast.makeText(this, R.string.note_error_password, Toast.LENGTH_SHORT).show();
            etPassword.requestFocus();
            return true;
        }
        if (rbMnemonic.isChecked() && etMnemonic.getText().toString().split("\\s+").length != 12) {
            Toast.makeText(this, R.string.note_error_mnemonic, Toast.LENGTH_SHORT).show();
            etMnemonic.requestFocus();
            return true;
        }
        if (rbPrivateKey.isChecked()) {
            BigInteger bigInteger;
            try {
                bigInteger = new BigInteger(etPrivateKey.getText().toString(), 16);
            } catch (Exception e) {
                Toast.makeText(this, R.string.note_error_private_key, Toast.LENGTH_SHORT).show();
                etPrivateKey.requestFocus();
                return true;
            }
//            int byte32 = 32 * 8;
//            if (bigInteger.toString(2).length() != byte32 && bigInteger.toString(2).length() != byte32 - 1) {
//                Toast.makeText(this, R.string.note_error_private_key, Toast.LENGTH_SHORT).show();
//                etPrivateKey.requestFocus();
//                return true;
//            }
        }
        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        EventUtil.handleSoftInput(ev, this);
        return super.dispatchTouchEvent(ev);
    }
}
