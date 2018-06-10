package com.oztaking.www.customviewclipimageborderview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * @function:
 */

public class ClipImageBorderView extends View {
    //水平方向与View的边距
    private int mHorizontalPadding = 20;
    //垂直方向与View的边距
    private int mVerticalPadding;
    //绘制的矩形的宽度
    private int mWidth;
    //边框的颜色，默认为白色
    private int mBorderColor = Color.parseColor("#FFFFFF");
    //边框的宽度 单位dp
    private int mBorderWidth = 1;

    private Paint mPaint;



    public ClipImageBorderView(Context context) {
        this(context, null);
    }

    public ClipImageBorderView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }



    public ClipImageBorderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //计算padding的px
        mHorizontalPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,mHorizontalPadding,getResources().getDisplayMetrics());

//        mVerticalPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,mVerticalPadding,getResources().getDisplayMetrics());
        mPaint= new Paint();
        mPaint.setAntiAlias(true); //抗锯齿

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //计算矩形区域的宽度；
        mWidth = getWidth() - mHorizontalPadding * 2;

        mVerticalPadding = (getHeight() -mWidth )/2;

        mPaint.setColor(Color.parseColor("#aa000000"));
        mPaint.setStyle(Paint.Style.FILL);

        //绘制左边1
        canvas.drawRect(0,0,mHorizontalPadding,getHeight(),mPaint);
//        右边2
        canvas.drawRect(getWidth() - mHorizontalPadding,0,getWidth(),getHeight(),mPaint);

        //绘制上边3
        canvas.drawRect(mHorizontalPadding,0,getWidth() - mHorizontalPadding,mVerticalPadding,mPaint);

        //绘制下面矩形4
        canvas.drawRect(mHorizontalPadding,getHeight()-mVerticalPadding,getWidth()-mHorizontalPadding,getHeight(),mPaint);

//        绘制外框；
        mPaint.setColor(mBorderColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mBorderWidth);
        canvas.drawRect(mHorizontalPadding,mVerticalPadding,getWidth()-mHorizontalPadding,getHeight()-mVerticalPadding,mPaint);
    }

    //对外公布设置mHorizontalPadding的方法；
    public void setHorizontalPadding(int horizontalPadding){
        this.mHorizontalPadding = horizontalPadding;
    }
}
