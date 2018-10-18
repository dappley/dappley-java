package com.dappley.android;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.dappley.android.sdk.DappleyClient;
import com.dappley.android.sdk.DappleyTest;
import com.dappley.android.sdk.net.ProtocalProvider;
import com.dappley.android.sdk.protobuf.TransactionProto;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.txt_content)
    TextView tvContent;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == 1) {
                tvContent.setText(msg.obj.toString());
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        DappleyClient.init(this, ProtocalProvider.ProviderType.RPC);
    }

    @OnClick(R.id.btn_wallet)
    void createWallet() {
//        String address = DappleyClient.createWalletAddress();
//        tvContent.setText(address);
//        String result = DappleyTest.testEncrypt();
//        tvContent.setText(result);
//        DappleyTest.testRecovery();
//        DappleyTest.testDB(this);
        DappleyTest.testAes();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                DappleyTest.testWord();
//            }
//        }).start();
    }

    @OnClick(R.id.btn_version)
    void getVersion() {
        try {
            String version = DappleyClient.getVersion();
            tvContent.setText(version);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btn_balance)
    void getBalance() {
        try {
            String address = "1BpXBb3uunLa9PL8MmkMtKNd3jzb5DHFkG";
            String message = DappleyClient.getBalance(address);
            tvContent.setText(message);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btn_add_balance)
    void addBalance() {
        try {
            String address = "1BpXBb3uunLa9PL8MmkMtKNd3jzb5DHFkG";
            String message = DappleyClient.addBalance(address);
            tvContent.setText(message);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btn_get_blockchain_info)
    void getBlockchainInfo() {
        try {
            DappleyClient.getBlockchainInfo();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btn_get_utxo)
    void getUtxo() {
        try {
            String address = "1BpXBb3uunLa9PL8MmkMtKNd3jzb5DHFkG";
            DappleyClient.getUtxo(address);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btn_get_blocks)
    void getBlocks() {
        try {
            DappleyClient.getBlocks();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btn_send_transaction)
    void sendTransaction() {
        try {
            TransactionProto.Transaction transaction = TransactionProto.Transaction.newBuilder().build();
            DappleyClient.sendTransaction(transaction);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void test() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    String message = null;
                    try {
                        message = DappleyClient.getBalance("1BpXBb3uunLa9PL8MmkMtKNd3jzb5DHFkG");
                        updateText(message);
                        Thread.sleep(400);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void updateText(String text) {
        Message message = new Message();
        message.what = 1;
        message.obj = text;
        handler.sendMessageDelayed(message, 10);
    }
}
