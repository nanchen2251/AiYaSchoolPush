package com.example.nanchen.aiyaschoolpush.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.nanchen.aiyaschoolpush.R;
import com.example.nanchen.aiyaschoolpush.utils.DisplayMetricsUtil;

/**
 * 支持点击变色的ImageButton 通过过滤实现按下和抬起切换背景色
 *
 * @author nanchen
 * @fileName AiYaSchoolPush
 * @packageName com.example.nanchen.aiyaschoolpush.view
 * @date 2016/09/12  15:40
 */
public class ColorButton extends View {
    private Context mContext;
    private String mTitleText;

    private int mTitleFontSize;
    private int mTitleFontColor;

    public final float[] BT_SELECTED = new float[] { 1, 0, 0, 0, -50, 0, 1, 0, 0, -50, 0, 0, 1, 0, -50, 0, 0, 0, 1, 0 };
    public final float[] BT_NOT_SELECTED = new float[] { 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0 };

    public ColorButton(Context context) {
        super(context);
        mContext = context;
        setOnTouchListener(onTouchListener);
    }

    public ColorButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView(attrs);
        setOnTouchListener(onTouchListener);
    }

    public ColorButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        initView(attrs);
        setOnTouchListener(onTouchListener);
    }

    private void initView(AttributeSet attrs) {
        TypedArray array = mContext.obtainStyledAttributes(attrs, R.styleable.colorButtonStyle);
        mTitleText = array.getString(R.styleable.colorButtonStyle_text_title);
        mTitleFontColor = array.getColor(R.styleable.colorButtonStyle_fontcolor_title, -1);
        mTitleFontSize = array.getInt(R.styleable.colorButtonStyle_fontsize_title, -1);

        /*
         * 在TypedArray后调用recycle主要是为了缓存。
         * 当recycle被调用后，这就说明这个对象从现在可以被重用了。
         * TypedArray 内部持有部分数组，它们缓存在Resources类中的静态字段中，
         * 这样就不用每次使用前都需要分配内存。
         */
        array.recycle();
    }

    public OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_UP:
                    view.getBackground().setColorFilter(new ColorMatrixColorFilter(BT_NOT_SELECTED));
                    view.setBackgroundDrawable(view.getBackground());
                    break;
                case MotionEvent.ACTION_DOWN:
                    view.getBackground().setColorFilter(new ColorMatrixColorFilter(BT_SELECTED));
                    view.setBackgroundDrawable(view.getBackground());
                    break;
                case MotionEvent.ACTION_MOVE:
                    break;
                case MotionEvent.ACTION_CANCEL:
                    view.getBackground().setColorFilter(new ColorMatrixColorFilter(BT_NOT_SELECTED));
                    view.setBackgroundDrawable(view.getBackground());
                    break;
                default:
                    break;
            }
            return false;
        }
    };

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (TextUtils.isEmpty(mTitleText)) {
            return;
        }

        Paint mPaint = new Paint();
        mPaint.setStrokeWidth(3);

        if (mTitleFontSize != -1) {
            mPaint.setTextSize(DisplayMetricsUtil.dip2px(mContext, mTitleFontSize));
        } else {
            mPaint.setTextSize(DisplayMetricsUtil.dip2px(mContext, 18));
        }

        if (mTitleFontColor != -1) {
            mPaint.setColor(mTitleFontColor);
        } else {
            mPaint.setColor(Color.WHITE);
        }

        mPaint.setTextAlign(Align.LEFT);
        Rect bounds = new Rect();
        mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), bounds);
        FontMetricsInt fontMetrics = mPaint.getFontMetricsInt();
        int baseline = (getMeasuredHeight() - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
        canvas.drawText(mTitleText, getMeasuredWidth() / 2 - bounds.width() / 2, baseline, mPaint);
    }

    public void setText(String text){
        if(!TextUtils.isEmpty(text)){
            mTitleText = text;
            invalidate();
        }
    }
}
