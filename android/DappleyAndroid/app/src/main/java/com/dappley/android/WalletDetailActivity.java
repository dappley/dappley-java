package com.dappley.android;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.dappley.android.adapter.UtxoListAdapter;
import com.dappley.android.adapter.WalletListAdapter;
import com.dappley.android.listener.BtnBackListener;
import com.dappley.android.sdk.Dappley;
import com.dappley.android.sdk.po.Utxo;
import com.dappley.android.sdk.po.Wallet;
import com.dappley.android.util.Constant;
import com.dappley.android.widget.EmptyView;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import deadline.swiperecyclerview.SwipeRecyclerView;

public class WalletDetailActivity extends AppCompatActivity {
    @BindView(R.id.btn_back)
    ImageButton btnBack;
    @BindView(R.id.txt_title)
    TextView tvTitle;
    @BindView(R.id.txt_address)
    TextView tvAddress;
    @BindView(R.id.txt_value)
    TextView tvValue;

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.swipe_fresh)
    SwipeRecyclerView swipeRecyclerView;

    UtxoListAdapter adapter;
    List<Utxo> utxos;
    Utxo utxo;
    Wallet wallet;
    BigInteger balance;
    int pageIndex = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_detail);

        ButterKnife.bind(this);

        initView();

        initData();

        loadData();
    }

    private void initView() {
        tvTitle.setText("钱包");
        btnBack.setOnClickListener(new BtnBackListener(this));

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageIndex = 1;
                loadData();
            }
        });

        adapter = new UtxoListAdapter(this);
        adapter.addOnItemClickListener(itemClickListener);
        swipeRecyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        swipeRecyclerView.getRecyclerView().setLayoutManager(layoutManager);

        swipeRecyclerView.getSwipeRefreshLayout().setColorSchemeResources(R.color.colorPrimary);
        swipeRecyclerView.setOnLoadListener(new SwipeRecyclerView.OnLoadListener() {
            @Override
            public void onRefresh() {
            }

            @Override
            public void onLoadMore() {
                pageIndex++;
                loadData();
            }
        });
        swipeRecyclerView.setRefreshEnable(false);
        swipeRecyclerView.setEmptyView(EmptyView.get(this));

        refreshLayout.setRefreshing(true);
    }

    private void initData() {
        Intent intent = getIntent();
        wallet = (Wallet) intent.getSerializableExtra("wallet");
        utxos = new ArrayList<>();

        tvAddress.setText(wallet.getAddress());
    }

    private void loadData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                loadBalance();
                loadUtxoList();
                handler.sendEmptyMessage(Constant.MSG_WALLET_DETAIL);
            }
        }).start();
    }

    private void loadBalance() {
        balance = Dappley.getWalletBalance(wallet.getAddress());
    }

    private void loadUtxoList() {
        List<Utxo> utxos = Dappley.getUtxos(wallet.getAddress(), pageIndex, Constant.PAGE_SIZE);
        if (pageIndex == 1) {
            if (utxos == null || utxos.size() == 0) {
                this.utxos.clear();
            } else {
                this.utxos = utxos;
            }
        } else {
            if (utxos == null || utxos.size() == 0) {
                pageIndex--;
            } else {
                this.utxos.addAll(utxos);
            }
        }
    }

    private UtxoListAdapter.OnItemClickListener itemClickListener = new UtxoListAdapter.OnItemClickListener() {
        @Override
        public void onClick(View view, int position) {

        }
    };

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == Constant.MSG_WALLET_DETAIL) {
                tvValue.setText(balance.toString());
                adapter.setList(utxos);

                refreshLayout.setRefreshing(false);
                swipeRecyclerView.complete();
            }
        }
    };
}
