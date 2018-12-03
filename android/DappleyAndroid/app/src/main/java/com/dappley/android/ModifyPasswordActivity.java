package com.dappley.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.dappley.android.dialog.LoadingDialog;
import com.dappley.android.listener.BtnBackListener;
import com.dappley.android.util.CommonUtil;
import com.dappley.android.util.StorageUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ModifyPasswordActivity extends AppCompatActivity {
    @BindView(R.id.btn_back)
    ImageButton btnBack;
    @BindView(R.id.txt_title)
    TextView tvTitle;
    @BindView(R.id.et_old_password)
    EditText etOldPassword;
    @BindView(R.id.et_new_password)
    EditText etNewPassword;
    @BindView(R.id.et_confirm_password)
    EditText etConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_password);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        tvTitle.setText(R.string.title_modify_password);
        btnBack.setOnClickListener(new BtnBackListener(this));
    }

    @OnClick(R.id.btn_modify)
    void modify() {
        if (checkNull()) {
            return;
        }
        LoadingDialog.show(this);
        boolean isSuccess = StorageUtil.modifyPassword(this, etOldPassword.getText().toString(), etNewPassword.getText().toString());
        LoadingDialog.close();
        if (isSuccess) {
            Toast.makeText(this, R.string.note_modify_password_success, Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            finish();
        } else {
            Toast.makeText(this, R.string.note_modify_password_failed, Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkNull() {
        if (CommonUtil.isNull(etOldPassword)) {
            Toast.makeText(this, R.string.note_old_password, Toast.LENGTH_SHORT).show();
            etOldPassword.requestFocus();
            return true;
        }
        if (CommonUtil.isNull(etNewPassword)) {
            Toast.makeText(this, R.string.note_new_password, Toast.LENGTH_SHORT).show();
            etNewPassword.requestFocus();
            return true;
        }
        if (CommonUtil.isNull(etConfirmPassword)) {
            Toast.makeText(this, R.string.note_confirm_password, Toast.LENGTH_SHORT).show();
            etConfirmPassword.requestFocus();
            return true;
        }
        if (!etConfirmPassword.getText().toString().equals(etNewPassword.getText().toString())) {
            Toast.makeText(this, R.string.note_modify_password_diff, Toast.LENGTH_SHORT).show();
            etConfirmPassword.requestFocus();
            return true;
        }
        boolean isPassCorrect = StorageUtil.checkPassword(this, etOldPassword.getText().toString());
        if (!isPassCorrect) {
            Toast.makeText(this, R.string.note_old_password_error, Toast.LENGTH_SHORT).show();
            etOldPassword.requestFocus();
            return true;
        }
        return false;
    }

    private boolean checkPassword(String password) {
        return true;
    }
}
