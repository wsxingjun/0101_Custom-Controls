package com.codingman.www.customcontrol;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;


/**
 * @function
 */

public class CustomTitleView extends View{
    private String mTitleText;
    private int mTitleTextColor;
    private int mTitleTextSize;

    //绘制时控制文本的绘制范围；
    private Rect mBound;
    private Paint mPaint;

    public CustomTitleView(Context context, AttributeSet attr) {
        this(context,attr,0);
    }

    public CustomTitleView(Context context) {
        this(context,null);
    }

    /**
     * 获取自定义的样式属性
     * @param context
     * @param attrs
     * @param defStyle
     */
    public CustomTitleView(Context context,AttributeSet attrs,int defStyle) {
        super(context,attrs,defStyle);

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs,R.styleable., defStyle, 0);
        int n = typedArray.getIndexCount();
        for (int i=0; i < n; i++){
            int attr = typedArray.getIndex(i);
            switch(attr){
                case R.styleable.CustomTitleView_titleText:
                    mTitleText = typedArray.getString(attr);
                break;

                case R.styleable.CustomTitleView_titleTextColor:
                    //default color is Back;
                    mTitleTextColor = typedArray.getColor(attr, Color.BLACK);
                    break;

                case R.styleable.CustomTitleView_titleTextSize:
                    mTitleTextSize = typedArray.getDimensionPixelSize(attr,
                            (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,16,getResources().getDisplayMetrics()));
                    break;
            }
        }
        typedArray.recycle();

        //获得绘制文本的宽和高；
        mPaint = new Paint();
        mPaint.setTextSize(mTitleTextSize);
        mBound = new Rect();
        mPaint.getTextBounds(mTitleText,0,mTitleText.length(),mBound);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(0,0,getMeasuredWidth(),getMeasuredHeight(),mPaint);

        mPaint.setColor(mTitleTextColor);
        canvas.drawText(mTitleText,getWidth()/2-mBound.width()/2,(getHeight() + mBound.height())/2,mPaint);

        super.onDraw(canvas);
    }
}
