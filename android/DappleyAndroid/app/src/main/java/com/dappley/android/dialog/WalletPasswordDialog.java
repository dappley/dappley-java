package com.dappley.android.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.dappley.android.R;

public class WalletPasswordDialog {
    private Context context;
    private OnClickListener listener;

    private Dialog dialog;
    private EditText etPassword;
    private Button btnCancel;
    private Button btnConfirm;

    public WalletPasswordDialog(Context context, OnClickListener listener) {
        this.context = context;
        this.listener = listener;
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.dialog_wallet_password, null);

        initView(layout);

        dialog = new Dialog(context, android.R.style.Theme_Material_Dialog_Alert);
        dialog.setCanceledOnTouchOutside(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.show();
        dialog.getWindow().setContentView(layout);
    }

    private void initView(View layout) {
        etPassword = (EditText) layout.findViewById(R.id.et_password);

        btnCancel = (Button) layout.findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnConfirm = (Button) layout.findViewById(R.id.btn_confirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    hideKeyBoard();
                    listener.onConfirm(etPassword.getText().toString());
                }
            }
        });
    }

    public void close() {
        dialog.dismiss();
    }

    public void show() {
        etPassword.setText("");
        dialog.show();
    }

    private void hideKeyBoard() {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etPassword.getWindowToken(), 0);
    }

    public interface OnClickListener {
        void onConfirm(String password);
    }
}
