package com.dappley.android;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.dappley.android.adapter.WalletListAdapter;
import com.dappley.android.sdk.Dappley;
import com.dappley.android.sdk.po.Wallet;
import com.dappley.android.util.Constant;
import com.dappley.android.util.StorageUtil;
import com.dappley.android.widget.EmptyView;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import deadline.swiperecyclerview.SwipeRecyclerView;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.swipe_fresh)
    SwipeRecyclerView swipeRecyclerView;

    WalletListAdapter adapter;
    List<Wallet> wallets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        initView();

        loadData();
    }

    private void initView() {
        adapter = new WalletListAdapter(this);
        adapter.addOnItemClickListener(itemClickListener);
        swipeRecyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        swipeRecyclerView.getRecyclerView().setLayoutManager(layoutManager);

        swipeRecyclerView.getSwipeRefreshLayout().setColorSchemeResources(R.color.colorPrimary);
        swipeRecyclerView.setOnLoadListener(new SwipeRecyclerView.OnLoadListener() {
            @Override
            public void onRefresh() {
                loadData();
                swipeRecyclerView.complete();
            }

            @Override
            public void onLoadMore() {

            }
        });
        swipeRecyclerView.setLoadMoreEnable(false);
        swipeRecyclerView.setEmptyView(EmptyView.get(this));
    }

    @OnClick(R.id.btn_test)
    void testPage() {
        startActivity(new Intent(this, TestActivity.class));
    }

    @OnClick(R.id.btn_add)
    void addWallet() {
        startActivity(new Intent(this, WalletAddActivity.class));
    }

    private void loadData() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission
                    .WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "please allow read/write storage authority！", Toast.LENGTH_SHORT).show();
            }
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constant.REQ_PERM_STORAGE);
            return;
        }
        readData();
    }

    private void readData() {
        List<Wallet> wallets = new ArrayList<>();
        try {
            List<String> addresses = StorageUtil.getAddresses();
            if (addresses == null) {
                addresses = new ArrayList<>(1);
            }
            Wallet wallet;
            for (String address : addresses) {
                wallet = new Wallet();
                wallet.setAddress(address);
                wallets.add(wallet);
            }
        } catch (IOException e) {
            Toast.makeText(this, "read data failed", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        adapter.setList(wallets);

        this.wallets = wallets;

        loadBalance();
    }

    private void loadBalance() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                wallets = Dappley.getWalletBalances(wallets);
                adapter.setList(wallets);
            }
        }).start();
    }

    private WalletListAdapter.OnItemClickListener itemClickListener = new WalletListAdapter.OnItemClickListener() {
        @Override
        public void onClick(View view, int position) {

        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Constant.REQ_PERM_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    readData();
                } else {
                    Toast.makeText(this, "please allow read storage authority！", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
