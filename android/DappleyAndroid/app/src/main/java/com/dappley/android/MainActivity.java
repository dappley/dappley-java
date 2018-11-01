package com.dappley.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.dappley.android.adapter.WalletListAdapter;
import com.dappley.android.widget.EmptyView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import deadline.swiperecyclerview.SwipeRecyclerView;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.swipe_fresh)
    SwipeRecyclerView swipeRecyclerView;
    WalletListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        initView();
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
                swipeRecyclerView.complete();
            }

            @Override
            public void onLoadMore() {

            }
        });
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

    private WalletListAdapter.OnItemClickListener itemClickListener = new WalletListAdapter.OnItemClickListener() {
        @Override
        public void onClick(View view, int position) {

        }
    };
}
