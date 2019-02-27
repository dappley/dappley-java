package com.dappley.android;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dappley.android.adapter.MainFragmentAdapter;
import com.dappley.android.fragment.MeFragment;
import com.dappley.android.fragment.StepFragment;
import com.dappley.android.fragment.WalletFragment;
import com.dappley.android.sdk.Dappley;
import com.dappley.google.step.GoogleStep;
import com.dappley.java.core.po.Wallet;
import com.dappley.android.util.Constant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private final int[] TAB_TITLES = new int[]{R.string.menu_wallet, R.string.menu_step, R.string.menu_me};
    private final int[] TAB_IMGS = new int[]{R.drawable.tab_main_wallet_selector, R.drawable.tab_main_step_selector, R.drawable.tab_main_me_selector};

    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    private PagerAdapter adapter;
    private long exitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        // 初始化页卡
        initPager();

        setTabs(tabLayout, getLayoutInflater(), TAB_TITLES, TAB_IMGS);

        registerBroadcast();
    }

    private void setTabs(TabLayout tabLayout, LayoutInflater inflater, int[] tabTitlees, int[] tabImgs) {
        for (int i = 0; i < tabImgs.length; i++) {
            TabLayout.Tab tab = tabLayout.newTab();
            View view = inflater.inflate(R.layout.item_main_menu, null);
            // 使用自定义视图，目的是为了便于修改，也可使用自带的视图
            tab.setCustomView(view);

            TextView tvTitle = (TextView) view.findViewById(R.id.txt_tab);
            tvTitle.setText(tabTitlees[i]);
            ImageView imgTab = (ImageView) view.findViewById(R.id.img_tab);
            imgTab.setImageResource(tabImgs[i]);
            tabLayout.addTab(tab);
        }
    }

    private void initPager() {
        adapter = new MainFragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        // 关联切换
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == viewPager.getCurrentItem()) {
                    return;
                }
                // 取消平滑切换
                viewPager.setCurrentItem(tab.getPosition(), false);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.setOffscreenPageLimit(TAB_TITLES.length);
    }

    public boolean checkReadPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission
                    .WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this, R.string.note_permittion_read, Toast.LENGTH_SHORT).show();
            }
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constant.REQ_PERM_STORAGE);
            return false;
        }
        return true;
    }

    public boolean checkCameraPermission() {
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

    private void registerBroadcast() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.BROAD_WALLET_LIST_UPDATE);
        registerReceiver(receiver, filter);
    }

    public void loadChangedData() {
        WalletFragment walletFragment = getWalletFragment();
        if (walletFragment != null) {
            walletFragment.loadData();
            walletFragment.loadBalance();
        }
        MeFragment meFragment = getMeFragment();
        if (meFragment != null) {
//            meFragment.loadData();
        }
    }

    public BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Constant.BROAD_WALLET_LIST_UPDATE.equals(intent.getAction())) {
                loadChangedData();
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);

        Dappley.release();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN
                && event.getRepeatCount() == 0) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Snackbar snackbar = Snackbar.make(viewPager, R.string.note_press_again, Snackbar.LENGTH_SHORT);
                snackbar.getView().setBackgroundResource(R.color.colorPrimary);
                snackbar.show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Constant.REQ_PERM_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    WalletFragment walletFragment = getWalletFragment();
                    if (walletFragment == null) {
                        return;
                    }
                    walletFragment.readData();
                } else {
                    Toast.makeText(this, R.string.note_permittion_read, Toast.LENGTH_SHORT).show();
                }
                break;
            case Constant.REQ_PERM_CAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    WalletFragment walletFragment = getWalletFragment();
                    if (walletFragment == null) {
                        return;
                    }
                    walletFragment.startQrCode(null);
                } else {
                    Toast.makeText(MainActivity.this, R.string.note_permittion_camera, Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GoogleStep.REQ_GOOGLE_SIGN_IN) {
            StepFragment stepFragment = getStepFragment();
            if (stepFragment == null) {
                return;
            }
            stepFragment.setGoogleLogined();
            return;
        }
        if (resultCode == RESULT_OK) {
            if (requestCode == Constant.REQ_ACT_QR_CODE) {
                WalletFragment walletFragment = getWalletFragment();
                if (walletFragment == null) {
                    return;
                }
                walletFragment.onScanResult(data);
            } else if (requestCode == Constant.REQ_ACT_CONVERT_SELECT) {
                StepFragment stepFragment = getStepFragment();
                if (stepFragment == null) {
                    return;
                }
                Wallet wallet = (Wallet) data.getSerializableExtra("wallet");
                stepFragment.onAddressSelected(wallet);
            } else if (requestCode == Constant.REQ_ACT_MODIFY_PASSWORD) {
                WalletFragment walletFragment = getWalletFragment();
                if (walletFragment == null) {
                    return;
                }
                walletFragment.loadData();
            } else if (requestCode == GoogleStep.REQ_GOOGLE_FIT_PERMISSIONS) {
                StepFragment stepFragment = getStepFragment();
                if (stepFragment == null) {
                    return;
                }
                stepFragment.setGoogleSupported();
            }
        }
    }

    private WalletFragment getWalletFragment() {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments.size() == 0) {
            return null;
        }
        return (WalletFragment) fragments.get(0);
    }

    private StepFragment getStepFragment() {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments.size() <= 1) {
            return null;
        }
        return (StepFragment) fragments.get(1);
    }

    private MeFragment getMeFragment() {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments.size() <= 2) {
            return null;
        }
        return (MeFragment) fragments.get(2);
    }
}
