package com.dappley.android.window;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.dappley.android.R;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DeleteMenuWindow extends PopupWindow {
    @BindView(R.id.txt_del)
    TextView tvDel;
    @BindView(R.id.txt_cancel)
    TextView tvCancel;
    @BindView(R.id.linear_content)
    LinearLayout linearContent;
    @BindDrawable(R.color.window_half_transparent)
    Drawable drawableHalf;

    private Activity activity;
    private OnWindowClickListener listener;
    private View rootView;

    public DeleteMenuWindow(Activity activity, OnWindowClickListener listener) {
        this.activity = activity;
        this.listener = listener;

        initWindow();
        initPopSetting();
    }

    public void showPopupWindow(View parent) {
        if (this.isShowing()) {
            this.dismiss();
        } else {
            this.showAtLocation(parent, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        }
    }

    private void initWindow() {
        rootView = LayoutInflater.from(activity).inflate(R.layout.pop_delete_menu, null);

        ButterKnife.bind(this, rootView);

        if (listener != null) {
            tvDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onDelClick();
                    dismiss();
                }
            });
        }
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    private void initPopSetting() {
        this.setContentView(rootView);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);

        this.setAnimationStyle(R.style.AnimationPopBottom);

        this.setFocusable(true);
        this.setOutsideTouchable(false);

        this.setBackgroundDrawable(drawableHalf);

        rootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int height = linearContent.getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
    }

    public interface OnWindowClickListener {
        public void onDelClick();
    }
}
