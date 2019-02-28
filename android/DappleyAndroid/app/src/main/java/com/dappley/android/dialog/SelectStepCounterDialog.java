package com.dappley.android.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import com.dappley.android.R;
import com.dappley.android.util.Constant;

public class SelectStepCounterDialog {
    private Context context;
    private OnClickListener listener;

    private Dialog dialog;
    private Button btnCancel;
    private Button btnConfirm;
    private RadioGroup rgType;

    public SelectStepCounterDialog(Context context, OnClickListener listener) {
        this.context = context;
        this.listener = listener;
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.dialog_step_counter, null);

        initView(layout);

        dialog = new AlertDialog.Builder(context).create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        dialog.getWindow().setContentView(layout);
    }

    private void initView(View layout) {
        rgType = layout.findViewById(R.id.rg_type);
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
                    int type = rgType.getCheckedRadioButtonId() == R.id.rb_app ? Constant.TYPE_APP_COUNTER : Constant.TYPE_GOOGLE_COUNTER;
                    listener.onConfirm(type);
                }
                dialog.dismiss();
            }
        });
    }

    public void close() {
        dialog.dismiss();
    }

    public void show() {
        dialog.show();
    }

    public void setType(int type) {
        if (type == Constant.TYPE_APP_COUNTER) {
            rgType.check(R.id.rb_app);
        } else {
            rgType.check(R.id.rb_google);
        }
    }

    public interface OnClickListener {
        void onConfirm(int type);
    }
}
