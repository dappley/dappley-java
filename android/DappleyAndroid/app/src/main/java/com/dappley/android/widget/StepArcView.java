package com.dappley.android.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

import com.dappley.android.R;

/**
 * Arc View in Step page
 */
public class StepArcView extends View {
    private Paint paint;
    private int roundStartColor;
    private int roundMiddleColor;
    private int roundEndColor;
    private float roundWidth;
    private int startAngle;

    public StepArcView(Context context) {
        this(context, null);
    }

    public StepArcView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StepArcView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        paint = new Paint();

        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.StepArcView);

        // read attributes
        roundStartColor = mTypedArray.getColor(R.styleable.StepArcView_srp_roundStartColor, Color.RED);
        roundMiddleColor = mTypedArray.getColor(R.styleable.StepArcView_srp_roundMiddleColor, Color.RED);
        roundEndColor = mTypedArray.getColor(R.styleable.StepArcView_srp_roundEndColor, Color.RED);
        roundWidth = mTypedArray.getDimension(R.styleable.StepArcView_srp_roundWidth, 5);
        startAngle = mTypedArray.getInt(R.styleable.StepArcView_srp_startAngle, 90);

        mTypedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int centerX = getWidth() / 2;
        int radius = (int) (centerX - roundWidth / 2);

        paint.setStrokeWidth(roundWidth);
        paint.setColor(roundStartColor);
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(Paint.Style.STROKE);

        RectF oval = new RectF(centerX - radius, centerX - radius, centerX + radius, centerX + radius);

        int sweepAngle = 360 - (startAngle - 90) * 2;

        // set shader
        SweepGradient sweepGradient = new SweepGradient(centerX, centerX, new int[]{roundStartColor, roundMiddleColor, roundEndColor}, null);
        paint.setShader(sweepGradient);
        canvas.save();

        canvas.rotate(90, centerX, centerX);
        canvas.drawArc(oval, startAngle - 90, sweepAngle, false, paint);
        canvas.restore();
    }

}
