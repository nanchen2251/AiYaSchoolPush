package com.example.nanchen.aiyaschoolpush.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.example.nanchen.aiyaschoolpush.R;

/**
 * 波浪线
 *
 * @author nanchen
 * @fileName AiYaSchoolPush
 * @packageName com.example.nanchen.aiyaschoolpush.view
 * @date 2016/10/12  10:23
 */

public class WavyLineView extends View {
    public static final int   DEF_DRAW_SIZE = 50;
    public static final int   DEF_COLOR = Color.BLACK;
    public static final int   DEF_X_GAP = 1;
    public static final int   DEF_AMPLITUDE = 20;
    public static final float DEF_STROKE_WIDTH = 2.0f;
    public static final float DEF_PERIOD = (float) (2 * Math.PI / 180);

    private Path mPath;
    private Paint mPaint;
    private int mAmplitude;
    private int mColor = DEF_COLOR;
    private float mPeriod;
    private float mStrokeWidth = DEF_STROKE_WIDTH;


    public WavyLineView(Context context) {
        this(context, null);
    }

    public WavyLineView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WavyLineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public WavyLineView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.WavyLineView);
        mColor = ta.getColor(R.styleable.WavyLineView_strokeColor, DEF_COLOR);
        mPeriod = ta.getFloat(R.styleable.WavyLineView_period, DEF_PERIOD);
        mAmplitude = ta.getDimensionPixelOffset(R.styleable.WavyLineView_amplitude, DEF_AMPLITUDE);
        mStrokeWidth = ta.getDimensionPixelOffset(R.styleable.WavyLineView_strokeWidth, dp2px(getContext(), DEF_STROKE_WIDTH));
        ta.recycle();

        mPath = new Path();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setColor(mColor);
        mPaint.setStrokeWidth(dp2px(getContext(), mStrokeWidth));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureSize(widthMeasureSpec), measureSize(heightMeasureSpec));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        calculatePath();
        canvas.drawPath(mPath, mPaint);
    }

    private int measureSize(int measureSpec) {
        int defSize = dp2px(getContext(), DEF_DRAW_SIZE);
        int specSize = MeasureSpec.getSize(measureSpec);
        int specMode = MeasureSpec.getMode(measureSpec);

        int result = 0;
        switch (specMode) {
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.AT_MOST:
                result = Math.min(defSize, specSize);
                break;
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;
        }
        return result;
    }

    private void calculatePath() {
        mPath.reset();

        float y;
        float left = getPaddingLeft();
        float right = getMeasuredWidth() - getPaddingRight();
        float top = getPaddingTop();
        float bottom = getMeasuredHeight() - getPaddingBottom();
        mPath.moveTo(left, (top + bottom) / 2);

        for (float x = 0; x <= right; x += DEF_X_GAP) {
            y = (float) (mAmplitude * Math.sin(mPeriod * x) + (top + bottom) / 2);
            mPath.lineTo(x + left, y);
        }
    }

    public void setAmplitude(int amplitude) {
        this.mAmplitude = amplitude;
        invalidate();
    }

    public void setPeriod(float T) {
        this.mPeriod = T;
        invalidate();
    }

    public void setColor(int color) {
        this.mColor = color;
        mPaint.setColor(mColor);
        invalidate();
    }

    public void setStrokeWidth(float strokeWidth) {
        this.mStrokeWidth = strokeWidth;
        mPaint.setStrokeWidth(mStrokeWidth);
        invalidate();
    }

    public static int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
