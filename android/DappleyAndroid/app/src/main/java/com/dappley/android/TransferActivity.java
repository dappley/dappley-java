package com.dappley.android;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dappley.android.adapter.WalletPagerAdapter;
import com.dappley.android.bean.Receiver;
import com.dappley.android.dialog.LoadingDialog;
import com.dappley.android.dialog.WalletPasswordDialog;
import com.dappley.android.listener.BtnBackListener;
import com.dappley.android.sdk.Dappley;
import com.dappley.android.util.CommonUtil;
import com.dappley.android.util.Constant;
import com.dappley.android.util.DuplicateUtil;
import com.dappley.android.util.EventUtil;
import com.dappley.android.util.StorageUtil;
import com.dappley.android.widget.AutoHeightViewPager;
import com.dappley.java.core.po.SendTxResult;
import com.dappley.java.core.po.Wallet;
import com.google.zxing.activity.CaptureActivity;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TransferActivity extends AppCompatActivity {
    private static final String TAG = "TransferActivity";
    private static final int SEEK_BAR_OFFSET = 1;

    @BindView(R.id.btn_back)
    ImageButton btnBack;
    @BindView(R.id.txt_title)
    TextView tvTitle;

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.view_pager)
    AutoHeightViewPager viewPager;

    @BindView(R.id.et_to_address)
    EditText etToAddress;
    @BindView(R.id.et_value)
    EditText etValue;

    @BindView(R.id.txt_tip_value)
    TextView tvTipValue;
    @BindView(R.id.bar_tip)
    SeekBar barTip;

    WalletPasswordDialog walletPasswordDialog;
    WalletPagerAdapter walletPagerAdapter;
    List<Wallet> wallets;
    Wallet wallet;
    String toAddress;
    BigInteger balance;
    int selectedIndex;

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
        tvTitle.setText(R.string.title_transfer);
        btnBack.setOnClickListener(new BtnBackListener(this));

        barTip.setOnSeekBarChangeListener(tipChangeListener);

        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadBalance();
            }
        });

        walletPagerAdapter = new WalletPagerAdapter(this);
        viewPager.setAdapter(walletPagerAdapter);
        viewPager.addOnPageChangeListener(pageChangeListener);
        // handle conflict with SwipeRefreshLayout
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        refreshLayout.setEnabled(false);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        refreshLayout.setEnabled(true);
                        break;
                }
                return false;
            }
        });

    }

    private void initData() {
        Intent intent = getIntent();
        wallet = (Wallet) intent.getSerializableExtra("wallet");
        toAddress = intent.getStringExtra("toAddress");

        if (toAddress != null && toAddress.length() > 0) {
            if (Dappley.validateAddress(toAddress)) {
                etToAddress.setText(toAddress);
            } else {
                Toast.makeText(this, R.string.note_not_valid_wallet, Toast.LENGTH_LONG).show();
            }
        }
    }

    @OnClick(R.id.btn_transfer)
    void transfer() {
        if (DuplicateUtil.dupClickCheck()) {
            return;
        }
        if (wallet == null) {
            Toast.makeText(this, R.string.note_no_valid_wallet, Toast.LENGTH_SHORT).show();
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

    @OnClick(R.id.btn_qrcode)
    void qrCode() {
        if (DuplicateUtil.dupClickCheck()) {
            return;
        }
        startQrCode();
    }

    @OnClick(R.id.btn_select_receiver)
    void selectReceiver() {
        if (DuplicateUtil.dupClickCheck()) {
            return;
        }
        Intent intent = new Intent(TransferActivity.this, ReceiverSelectActivity.class);
        startActivityForResult(intent, Constant.REQ_ACT_RECEVIER_SELECT);
    }

    private void loadData() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission
                    .WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this, R.string.note_permittion_read, Toast.LENGTH_SHORT).show();
            }
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constant.REQ_PERM_STORAGE);
            return;
        }
        readWallets();

        checkDefault();

        refreshLayout.setRefreshing(true);
        loadBalance();
    }

    private void readWallets() {
        List<Wallet> wallets = null;
        try {
            wallets = StorageUtil.getWallets(this);
            if (wallets == null) {
                wallets = new ArrayList<>(1);
            }
        } catch (IOException e) {
            Toast.makeText(this, R.string.note_read_failed, Toast.LENGTH_SHORT).show();
            Log.e(TAG, "readWallets: ", e);
        }
        walletPagerAdapter.setList(wallets, -1);

        this.wallets = wallets;
    }

    private void checkDefault() {
        if (wallets == null || wallets.size() == 0) {
            return;
        }
        if (this.wallet == null) {
            this.wallet = wallets.get(0);
            selectedIndex = 0;
            return;
        }
        Wallet wallet;
        for (int i = 0; i < wallets.size(); i++) {
            wallet = wallets.get(i);
            if (wallet == null) {
                continue;
            }
            if (wallet.getAddress() == null) {
                continue;
            }
            if (wallet.getAddress().equals(this.wallet.getAddress())) {
                selectedIndex = i;
                break;
            }
        }
        viewPager.setCurrentItem(selectedIndex, false);
    }

    private void loadBalance() {
        if (wallet == null) {
            Toast.makeText(this, R.string.note_no_valid_wallet, Toast.LENGTH_SHORT).show();
            refreshLayout.setRefreshing(false);
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    balance = Dappley.getWalletBalance(wallet.getAddress());
                    handler.sendEmptyMessage(Constant.MSG_TRANSFER_BALANCE);
                } catch (Exception e) {
                    handler.sendEmptyMessage(Constant.MSG_TRANSFER_BALANCE_ERROR);
                    Log.e(TAG, "run: ", e);
                }
            }
        }).start();
    }

    private boolean checkNull() {
        if (CommonUtil.isNull(etValue)) {
            Toast.makeText(this, R.string.note_no_amount, Toast.LENGTH_SHORT).show();
            return true;
        }
        BigDecimal value = null;
        try {
            value = new BigDecimal(etValue.getText().toString().trim());
            value = value.stripTrailingZeros();
        } catch (Exception e) {
        }
        if (value == null || value.compareTo(BigDecimal.ZERO) <= 0 || value.scale() > com.dappley.java.core.util.Constant.COIN_SCALE) {
            Toast.makeText(this, R.string.note_error_amount, Toast.LENGTH_SHORT).show();
            return true;
        }
        if (CommonUtil.isNull(etToAddress)) {
            Toast.makeText(this, R.string.note_no_receiver, Toast.LENGTH_SHORT).show();
            return true;
        }
        if (!Dappley.validateAddress(etToAddress.getText().toString().trim())) {
            Toast.makeText(this, R.string.note_receiver_illegal, Toast.LENGTH_SHORT).show();
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
            Wallet walletTemp = Dappley.decryptWallet(this.wallet, password);
            if (walletTemp == null || walletTemp.getPrivateKey() == null) {
                Toast.makeText(this, R.string.note_error_password, Toast.LENGTH_SHORT).show();
                return;
            }
            this.wallet = walletTemp;
            walletPasswordDialog.close();

            BigDecimal inputValue = new BigDecimal(etValue.getText().toString().trim());
            final BigInteger amount = inputValue.multiply(com.dappley.java.core.util.Constant.COIN_DW).toBigInteger();
            if (balance == null || amount.compareTo(balance) > 0) {
                Toast.makeText(this, R.string.note_balance_not_enough, Toast.LENGTH_SHORT).show();
                return;
            }
            LoadingDialog.show(this);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    String toAddress = etToAddress.getText().toString().trim();
                    boolean isSuccess = false;
                    try {
                        BigInteger tip = new BigInteger(String.valueOf(barTip.getProgress() + SEEK_BAR_OFFSET));
                        SendTxResult sendTxResult = Dappley.sendTransaction(wallet.getAddress(), toAddress, amount, wallet.getPrivateKey(), tip);
                        if (sendTxResult != null) {
                            isSuccess = sendTxResult.isSuccess();
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "transfer: ", e);
                    }
                    Message msg = new Message();
                    msg.obj = isSuccess;
                    msg.what = Constant.MSG_TRANSFER_FINISH;
                    handler.sendMessage(msg);
                }
            }).start();
        } catch (Exception e) {
            Log.e(TAG, "onPasswordInput: ", e);
            Toast.makeText(this, R.string.note_error_password, Toast.LENGTH_SHORT).show();
            return;
        }
    }

    private void onTransferFinish(boolean isSuccess) {
        LoadingDialog.close();
        if (isSuccess) {
            // record receiver name
            Receiver receiver = new Receiver(etToAddress.getText().toString(), new Date());
            try {
                StorageUtil.saveReceiver(this, receiver);
            } catch (IOException e) {
                Log.e(TAG, "onTransferFinish: ", e);
            }
            Toast.makeText(this, R.string.note_transfer_success, Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, R.string.note_transfer_failed, Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission
                    .CAMERA)) {
                Toast.makeText(this, R.string.note_permittion_camera, Toast.LENGTH_SHORT).show();
            }
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, Constant.REQ_PERM_CAMERA);
            return false;
        }
        return true;
    }

    private void startQrCode() {
        if (!checkCameraPermission()) {
            return;
        }
        Intent intent = new Intent(this, CaptureActivity.class);
        startActivityForResult(intent, Constant.REQ_ACT_QR_CODE);
    }

    public void onScanResult(Intent data) {
        Bundle bundle = data.getExtras();
        String scanResult = bundle.getString(com.google.zxing.util.Constant.INTENT_EXTRA_KEY_QR_SCAN);

        if (scanResult != null && scanResult.length() > 0) {
            etToAddress.setText(scanResult);
        }
    }

    private SeekBar.OnSeekBarChangeListener tipChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            BigDecimal dwValue = new BigDecimal(progress + SEEK_BAR_OFFSET).divide(com.dappley.java.core.util.Constant.COIN_DW,
                    com.dappley.java.core.util.Constant.COIN_SCALE, RoundingMode.UP);
            tvTipValue.setText(dwValue.toPlainString());
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int position) {
            if (selectedIndex == position) {
                return;
            }
            selectedIndex = position;
            wallet = wallets.get(position);
            refreshLayout.setRefreshing(true);
            loadBalance();
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == Constant.MSG_TRANSFER_BALANCE) {
                wallet.setBalance(balance);
                wallets.set(selectedIndex, wallet);
                walletPagerAdapter.setList(wallets, selectedIndex);
                refreshLayout.setRefreshing(false);
            } else if (msg.what == Constant.MSG_TRANSFER_BALANCE_ERROR) {
                Toast.makeText(TransferActivity.this, R.string.note_node_error, Toast.LENGTH_SHORT).show();
                refreshLayout.setRefreshing(false);
            } else if (msg.what == Constant.MSG_TRANSFER_FINISH) {
                boolean isSuccess = (boolean) msg.obj;
                onTransferFinish(isSuccess);
            }
        }
    };

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        EventUtil.handleSoftInput(ev, this);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Constant.REQ_PERM_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    readWallets();
                } else {
                    Toast.makeText(this, R.string.note_permittion_read, Toast.LENGTH_SHORT).show();
                }
                break;
            case Constant.REQ_PERM_CAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startQrCode();
                } else {
                    Toast.makeText(TransferActivity.this, R.string.note_permittion_camera, Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constant.REQ_ACT_RECEVIER_SELECT:
                    String address = data.getStringExtra("address");
                    if (address == null) {
                        return;
                    }
                    etToAddress.setText(address);
                    break;
                case Constant.REQ_ACT_QR_CODE:
                    onScanResult(data);
                    break;
                default:
                    break;
            }
        }
    }
}
