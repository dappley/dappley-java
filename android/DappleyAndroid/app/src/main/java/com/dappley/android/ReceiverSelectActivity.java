package com.dappley.android;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.dappley.android.adapter.ReceiverSelectListAdapter;
import com.dappley.android.bean.Receiver;
import com.dappley.android.listener.BtnBackListener;
import com.dappley.android.util.Constant;
import com.dappley.android.util.StorageUtil;
import com.dappley.android.widget.EmptyView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import deadline.swiperecyclerview.SwipeRecyclerView;

public class ReceiverSelectActivity extends AppCompatActivity {
    private static final String TAG = "ReceiverSelectActivity";
    private static final int LIST_HOLD_SIZE = 20;

    @BindView(R.id.btn_back)
    ImageButton btnBack;
    @BindView(R.id.txt_title)
    TextView tvTitle;

    @BindView(R.id.swipe_fresh)
    SwipeRecyclerView swipeRecyclerView;

    ReceiverSelectListAdapter adapter;
    List<Receiver> receivers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver_select);


        ButterKnife.bind(this);

        initView();

        loadData();
    }

    private void initView() {
        tvTitle.setText(R.string.title_receiver_select);
        btnBack.setOnClickListener(new BtnBackListener(this));

        adapter = new ReceiverSelectListAdapter(this);
        adapter.addOnItemClickListener(itemClickListener);
        swipeRecyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        swipeRecyclerView.getRecyclerView().setLayoutManager(layoutManager);
        swipeRecyclerView.setOnLoadListener(new SwipeRecyclerView.OnLoadListener() {
            @Override
            public void onRefresh() {
                loadData();
            }

            @Override
            public void onLoadMore() {

            }
        });
        swipeRecyclerView.setLoadMoreEnable(false);
        swipeRecyclerView.setEmptyView(EmptyView.get(this));
    }

    public void loadData() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission
                    .WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this, R.string.note_permittion_read, Toast.LENGTH_SHORT).show();
            }
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constant.REQ_PERM_STORAGE);
            return;
        }
        readData();
    }

    public void readData() {
        List<Receiver> receivers = null;
        try {
            receivers = StorageUtil.getReceivers(this);
        } catch (IOException e) {
            Toast.makeText(this, R.string.note_read_failed, Toast.LENGTH_SHORT).show();
            Log.e(TAG, "readData: ", e);
        }
        if (receivers == null) {
            receivers = new ArrayList<>(1);
        }
        Collections.sort(receivers);

        receivers = cutReceiverList(receivers);

        adapter.setList(receivers);

        this.receivers = receivers;

        swipeRecyclerView.setRefreshing(false);
    }

    private List<Receiver> cutReceiverList(List<Receiver> receivers) {
        if (receivers != null && receivers.size() > LIST_HOLD_SIZE) {
            receivers = receivers.subList(0, LIST_HOLD_SIZE);
        }
        return receivers;
    }

    private ReceiverSelectListAdapter.OnItemClickListener itemClickListener = new ReceiverSelectListAdapter.OnItemClickListener() {
        @Override
        public void onClick(View view, int position) {
            Receiver receiver = receivers.get(position);
            if (receiver == null || receiver.getAddress() == null) {
                return;
            }
            Intent intent = new Intent();
            intent.putExtra("address", receiver.getAddress());
            setResult(RESULT_OK, intent);
            finish();
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Constant.REQ_PERM_STORAGE:
                readData();
                break;
            default:
                break;
        }
    }
}
