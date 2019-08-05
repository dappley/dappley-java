package com.dappley.android.widget;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

public class FlowRadioGroup extends RadioGroup {
    List<List<View>> mAllViews;
    List<Integer> mLineHeight;

    public FlowRadioGroup(Context context) {
        this(context, null);
    }

    public FlowRadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setOrientation(LinearLayout.HORIZONTAL);
    }

    public int getCheckedRadioButtonIndex() {
        return indexOfChild(findViewById(getCheckedRadioButtonId()));
    }

    public String getCheckedRadioButtonText() {
        if (getCheckedRadioButtonId() == -1) {
            return "";
        }
        return ((RadioButton) findViewById(getCheckedRadioButtonId())).getText().toString();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        int width = 0, height = 0;
        int lineWidth = 0, lineHeight = 0;
        int childWidth = 0, childHeight = 0;

        mAllViews = new ArrayList<>();
        mLineHeight = new ArrayList<>();

        List<View> lineViews = new ArrayList<>();
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == View.GONE) {
                continue;
            }

            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) child.getLayoutParams();

            childWidth = child.getMeasuredWidth() + params.leftMargin + params.rightMargin;
            childHeight = child.getMeasuredHeight() + params.topMargin + params.bottomMargin;

            if (lineWidth + childWidth > sizeWidth - getPaddingLeft() - getPaddingRight()) {
                width = Math.max(width, lineWidth);
                height += lineHeight;
                mLineHeight.add(lineHeight);
                mAllViews.add(lineViews);

                lineWidth = childWidth;
                lineHeight = childHeight;
                lineViews = new ArrayList<>();
            } else {
                lineWidth += childWidth;
                lineHeight = Math.max(childHeight, lineHeight);
            }
            lineViews.add(child);

            if (i == (count - 1)) {
                width = Math.max(width, lineWidth);
                height += lineHeight;
            }
        }
        mLineHeight.add(lineHeight);
        mAllViews.add(lineViews);
        width += getPaddingLeft() + getPaddingRight();
        height += getPaddingTop() + getPaddingBottom();
        setMeasuredDimension(modeWidth == MeasureSpec.AT_MOST ? width : sizeWidth, modeHeight == MeasureSpec.AT_MOST ? height : sizeHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int top = getPaddingTop();
        int left = getPaddingLeft();

        int lineNum = mAllViews.size();
        List<View> lineView;
        int lineHeight;
        for (int i = 0; i < lineNum; i++) {
            lineView = mAllViews.get(i);
            lineHeight = mLineHeight.get(i);

            for (int j = 0; j < lineView.size(); j++) {
                View child = lineView.get(j);
                if (child.getVisibility() == View.GONE) {
                    continue;
                }

                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) child.getLayoutParams();
                int ld = left + params.leftMargin;
                int td = top + params.topMargin;
                int rd = ld + child.getMeasuredWidth();
                int bd = td + child.getMeasuredHeight();
                child.layout(ld, td, rd, bd);

                left += child.getMeasuredWidth() + params.leftMargin + params.rightMargin;
            }

            left = getPaddingLeft();
            top += lineHeight;
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }


    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        return new SavedState(superState, getCheckedRadioButtonIndex());
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        setCheckedStateForView(ss.checkIndex, true);
    }

    private void setCheckedStateForView(int checkIndex, boolean checked) {
        View checkedView = getChildAt(checkIndex);
        if (checkedView != null && checkedView instanceof RadioButton) {
            ((RadioButton) checkedView).setChecked(checked);
        }
    }

    public static class SavedState extends BaseSavedState {

        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {

            @Override
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
        private int checkIndex;

        public SavedState(Parcelable parcel, int checkIndex) {
            super(parcel);
            this.checkIndex = checkIndex;
        }

        private SavedState(Parcel in) {
            super(in);
            checkIndex = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(checkIndex);
        }
    }
}
