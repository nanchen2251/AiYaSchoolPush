package com.example.nanchen.aiyaschoolpush.ui.view;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.example.nanchen.aiyaschoolpush.R;
import com.example.nanchen.aiyaschoolpush.utils.DisplayMetricsUtil;


//level0 和普通imageview没区别
//level1 把图片切成圆
//level2 把图片切成圆，画布加个圆边，圆边的宽度有padding值决定
//level3 倒角对图片
//level4 切圆，画布加边，右下角45度加阴影

public class RoundImageView extends ImageView {

    /**
     * 圆形ImageView，可设置最多两个宽度不同且颜色不同的圆形边框。
     *
     * @author Alan
     */

    private static class imageview_level {
        public final static int level0 = 0;
        public final static int level1 = 1;
        public final static int level2 = 2;
        public final static int level3 = 3;
        public final static int level4 = 4;
    }


    private Context mContext;
    private int circleColor = Color.WHITE;
    private int circleWidth = 0;
    private int mLevel = imageview_level.level1;

    public void setLevel(int level) {
    	mLevel = level;
    }
    public RoundImageView(Context context) {
        super(context);
        mContext = context;
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        setCustomAttributes(attrs);
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        setCustomAttributes(attrs);
    }

    private void setCustomAttributes(AttributeSet attrs) {
        TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.roundedimageview);
        int width = a.getDimensionPixelSize(R.styleable.roundedimageview_border_thickness, 0);
        setPadding(width, width, width, width);
        mLevel = a.getInt(R.styleable.roundedimageview_image_mode, imageview_level.level1);
        circleColor = a.getColor(R.styleable.roundedimageview_border_color, circleColor);
    }

    
    @Override
	public void setImageBitmap(Bitmap bm) {
    	switch (this.mLevel) {
	    	case imageview_level.level1 :
	    		bm = RoundBitmap(bm);
	    	case imageview_level.level2 :
	    		if ((getPaddingLeft() == getPaddingRight()) && (getPaddingLeft() == getPaddingBottom()) 
	    				&& (getPaddingLeft() == getPaddingTop())) {
	    			this.circleWidth = getPaddingLeft();
	    			bm = RoundBitmap(bm);
	    		}
	    		break;
	    	case imageview_level.level3 :
	    		bm = ChamferBitmap(bm);
	    		break;
	    	case imageview_level.level4:
	    		if ((getPaddingLeft() == getPaddingRight()) && (getPaddingLeft() == getPaddingBottom()) 
	    				&& (getPaddingLeft() == getPaddingTop())) {
	    			this.circleWidth = getPaddingLeft();
	    			bm = RoundBitmap(bm);
	    		}
	    		break;
    		default :
    			break;
    	}
    	super.setImageBitmap(bm);
	}

	@Override
    protected void onDraw(Canvas canvas) {
        switch (this.mLevel) {
	        case imageview_level.level2:
	            if (circleWidth > 0) {
	                drawCircleBorder(canvas, (getWidth() - this.circleWidth*2 + circleWidth) / 2, this.circleColor, getWidth(),
	                        getHeight(), this.circleWidth);
	            }
	            break;
	        case imageview_level.level4:
	        	if (circleWidth > 0){
	        		int paddingwidth = circleWidth;
	
	                drawCircleBorder(canvas, (getWidth()-paddingwidth*2 +circleWidth /2) / 2, this.circleColor, getWidth(),
	                        getHeight(), this.circleWidth /2,Color.DKGRAY);
	
	        		int tempwidth = circleWidth /2;
	                drawCircleBorder(canvas, (getWidth()-paddingwidth*2 +tempwidth) / 2, this.circleColor, getWidth(),
	                        getHeight(), tempwidth,Color.DKGRAY);
	
	        	}
	        	break;
	        default:
	            break;
        }
        super.onDraw(canvas);
    }

	/**
     * bitmap切成圆形
     *
     * @param bitmap 传入Bitmap对象
     * @return
     */
    private Bitmap RoundBitmap(Bitmap bitmap) {
        Bitmap resultBitmap = null;
        Canvas canvas = null;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
        if (width <= height) {
            roundPx = width / 2;
            top = 0;
            bottom = width;
            left = 0;
            right = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }
        if (width <= 0) {
            width = 1;
        }
        if (height <= 0) {
            height = 1;
        }

        try {
            resultBitmap = Bitmap.createBitmap(width, height, Config.ARGB_4444);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        try {
            canvas = new Canvas(resultBitmap);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        final int color = Color.RED;
        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top, (int) dst_right, (int) dst_bottom);
        final RectF rectF = new RectF(dst);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        // paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);
        return resultBitmap;
    }

    /**
     * bitmap倒角
     *
     * @param bitmap 传入Bitmap对象
     * @return
     */
    private Bitmap ChamferBitmap(Bitmap bitmap) {
        Bitmap resultBitmap = null;
        Canvas canvas = null;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
        if (width <= height) {
            roundPx = DisplayMetricsUtil.dip2px(this.mContext, 4); // 8像素倒角 4是dp值
            top = 0;
            bottom = width;
            left = 0;
            right = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = DisplayMetricsUtil.dip2px(this.mContext, 4); // 8像素倒角 4是dp值
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }
        if (width <= 0) {
            width = 1;
        }
        if (height <= 0) {
            height = 1;
        }

        try {
            resultBitmap = Bitmap.createBitmap(width, height, Config.ARGB_4444);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        try {
            canvas = new Canvas(resultBitmap);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        final int color = Color.RED;
        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top, (int) dst_right, (int) dst_bottom);
        final RectF rectF = new RectF(dst);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        // paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);
        return resultBitmap;
    }
    
    /**
     * 画布画圆
     */
    private void drawCircleBorder(Canvas canvas, int radius, int color, int width, int height, int circleWidth) {
        Paint paint = new Paint();
        /* 去锯齿 */
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        paint.setColor(color);
        /* 设置paint的　style　为STROKE：空心 */
        paint.setStyle(Paint.Style.STROKE);
        /* 设置paint的外框宽度 */
        paint.setStrokeWidth(circleWidth);
        canvas.drawCircle(width / 2, height / 2, radius, paint);
    }
    
    private void drawCircleBorder(Canvas canvas, int radius, int color, int width, int height, int circleWidth,int shadowcolor){
    	
		canvas.save();  //保存画布当前状态
		canvas.rotate(45,width / 2, height / 2);  //右下角45度阴影投射
		Paint paint = new Paint();
		paint.setColor(0x09ffffff & shadowcolor ); //设置alpha值
		for(int i=0;i<circleWidth*2;i++)  //向下角角偏移投射多少次阴影层
		{
			canvas.drawCircle(width/2+i, height / 2, radius+2, paint);
		}
		canvas.restore();
		
        paint = new Paint();
        /* 去锯齿 */
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        paint.setColor(color);
        /* 设置paint的　style　为STROKE：空心 */
        paint.setStyle(Paint.Style.STROKE);
        /* 设置paint的外框宽度 */
        paint.setStrokeWidth(circleWidth); //二分之一实体
        canvas.drawCircle(width / 2, height / 2, radius, paint);
    }
    
    public void setCircleWidth(int padding) {
        setPadding(padding, padding, padding, padding);
    }

    public int getCircleColor() {
        return circleColor;
    }

    public void setCircleColor(int circleColor) {
        this.circleColor = circleColor;
    }

    // 执行完setImageBitmap后才能获得;
    public int getCircleWidth() {
        return this.circleWidth;
    }
    
    public OnTouchListener onTouchListener = new OnTouchListener() {
		@Override
		public boolean onTouch(View view, MotionEvent event) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_UP:
				changeLight((ImageView) view, 0);
				// onclick
				break;
			case MotionEvent.ACTION_DOWN:
				changeLight((ImageView) view, -60);
				break;
			case MotionEvent.ACTION_MOVE:
//				 changeLight((ImageView) view, 0);
				break;
			case MotionEvent.ACTION_CANCEL:
				changeLight((ImageView) view, 0);
				break;
			default:
				break;
			}
			return false;
		}
	};
	
	public void setColorFilter(boolean value){
		if(value){
			setOnTouchListener(onTouchListener);
		}else{
			setOnTouchListener(null);
		}
	}

	private void changeLight(ImageView imageview, int brightness) {
		ColorMatrix matrix = new ColorMatrix();
		matrix.set(new float[] { 1, 0, 0, 0, brightness, 0, 1, 0, 0, brightness, 0, 0, 1, 0, brightness, 0, 0, 0, 1, 0 });
		imageview.setColorFilter(new ColorMatrixColorFilter(matrix));
	}


}