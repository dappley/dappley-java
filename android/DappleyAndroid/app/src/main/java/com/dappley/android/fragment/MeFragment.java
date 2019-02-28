package com.dappley.android.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dappley.android.MainActivity;
import com.dappley.android.ModifyPasswordActivity;
import com.dappley.android.R;
import com.dappley.android.dialog.LoadingDialog;
import com.dappley.android.dialog.SelectStepCounterDialog;
import com.dappley.android.util.PreferenceUtil;
import com.dappley.google.step.GoogleStep;
import com.dappley.java.core.po.Wallet;
import com.dappley.android.util.Constant;
import com.dappley.android.util.StorageUtil;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MeFragment extends Fragment {
    private static final String TAG = "MeFragment";
    GoogleStep googleStep;
    int unLoginCounter;

    MeHandler handler;

    public MeFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new MeHandler(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

//        loadData();
    }

//    public void loadData() {
//        try {
//            List<Wallet> wallets = StorageUtil.getWallets(getActivity());
//            if (wallets == null) {
//                tvWalletNum.setText("0");
//            } else {
//                tvWalletNum.setText("" + wallets.size());
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            tvWalletNum.setText("0");
//        }
//    }

    @OnClick(R.id.linear_modify_password)
    void modifyPassword() {
        Intent intent = new Intent(getActivity(), ModifyPasswordActivity.class);
        getActivity().startActivityForResult(intent, Constant.REQ_ACT_MODIFY_PASSWORD);
    }

    @OnClick(R.id.linear_step_counter)
    void stepCounter() {
        boolean isNativeStep = PreferenceUtil.getBoolean(getContext(), Constant.PREF_NATIVE_STEP, true);
        SelectStepCounterDialog dialog = new SelectStepCounterDialog(getActivity(), new SelectStepCounterDialog.OnClickListener() {
            @Override
            public void onConfirm(int type) {
                onCounterSelected(type);
            }
        });
        dialog.setType(isNativeStep ? Constant.TYPE_APP_COUNTER : Constant.TYPE_GOOGLE_COUNTER);
        dialog.show();
        unLoginCounter = 0;
    }

    private void onCounterSelected(int type) {
        if (type == Constant.TYPE_APP_COUNTER) {
            updateStepState(true);
            return;
        }
        if (googleStep == null) {
            googleStep = new GoogleStep(getActivity());
        }
        refreshGoogleApi();
    }

    private void updateStepState(boolean isNative) {
        PreferenceUtil.setBoolean(getContext(), Constant.PREF_NATIVE_STEP, isNative);
        // udate step fragment
        ((MainActivity) getActivity()).loadNewStepCounter();
    }

    private void refreshGoogleApi() {
        new GoogleThread().start();
    }

    public void setGoogleLogined() {
        if (unLoginCounter > 1) {
            // Return if user doesn't login his google account
            Toast.makeText(getActivity(), R.string.note_google_fit_failed, Toast.LENGTH_SHORT).show();
            return;
        }
        refreshGoogleApi();
        LoadingDialog.close();
    }

    public void setGoogleSupported() {
        updateStepState(true);
    }

    class GoogleThread extends Thread {
        @Override
        public void run() {
            int googleState = googleStep.isSupported();
            if (googleState == GoogleStep.STATE_SUCCESS) {
                updateStepState(false);
            } else if (googleState == GoogleStep.STATE_PLAY_UNAVAIABLE) {
                Message msg = handler.obtainMessage(Constant.MSG_GOOGLE_UNAVAIABLE);
                handler.sendMessage(msg);
                updateStepState(true);
            } else if (googleState == GoogleStep.STATE_NEED_LOGIN) {
                if (unLoginCounter > 0) {
                    // if user rejects google login request, user native step counter
                    Message msg = handler.obtainMessage(Constant.MSG_GOOGLE_FAILED);
                    handler.sendMessage(msg);
                    updateStepState(true);
                    return;
                }
                unLoginCounter++;
                Message msg = handler.obtainMessage(Constant.MSG_GOOGLE_LOGIN);
                handler.sendMessage(msg);
            } else if (googleState == GoogleStep.STATE_NEED_PERMISSION) {
                Message msg = handler.obtainMessage(Constant.MSG_GOOGLE_PERMISSION);
                handler.sendMessage(msg);
            }
        }
    }

    static class MeHandler extends Handler {
        WeakReference<MeFragment> weakReference;

        public MeHandler(MeFragment meFragment) {
            weakReference = new WeakReference<>(meFragment);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            MeFragment meFragment = weakReference.get();
            if (meFragment == null) {
                return;
            }
            if (msg.what == Constant.MSG_GOOGLE_LOGIN) {
                LoadingDialog.show(meFragment.getActivity());
                meFragment.googleStep.startSignInIntent();
            } else if (msg.what == Constant.MSG_GOOGLE_PERMISSION) {
                meFragment.googleStep.requestPermissions();
            } else if (msg.what == Constant.MSG_GOOGLE_FAILED) {
                Toast.makeText(meFragment.getActivity(), R.string.note_google_fit_failed, Toast.LENGTH_SHORT).show();
            } else if (msg.what == Constant.MSG_GOOGLE_UNAVAIABLE) {
                Toast.makeText(meFragment.getActivity(), R.string.note_google_fit_unavaiable, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
