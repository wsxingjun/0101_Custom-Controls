package com.oztaking.www.customcircleprogress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * @function:
 */

public class CustomProgressBar extends View {

    //第一圈的颜色；
    private int mFirstColor;
    //第二圈的颜色；
    private int mSecondColor;
    //圈的宽度
    private int mCircleWidth;
    //画笔
    private Paint mPaint;
    //当前的进度；
    private int mProgress;
    //速度；
    private int mSpeed;
    //是否开始下一个
    private boolean isNext = false;

    public CustomProgressBar(Context context) {
        this(context, null);
    }

    public CustomProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }



    /**
     * @function:初始化必要的值;
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public CustomProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable
                .CustomProgressBar, defStyleAttr, 0);
        int indexCount = typedArray.getIndexCount();
        for (int i= 0; i<indexCount; i++){
            int attr = typedArray.getIndex(i);
            switch(attr){
                 case R.styleable.CustomProgressBar_circleWidth:
                     mCircleWidth = typedArray.getDimensionPixelOffset(attr,
                             (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_PX,20,getResources().getDisplayMetrics()));
                      break;

                case R.styleable.CustomProgressBar_firstColor:
                    mFirstColor = typedArray.getColor(attr, Color.GREEN);
                    break;

                case R.styleable.CustomProgressBar_secondColor:
                    mSecondColor = typedArray.getColor(attr,Color.RED);
                    break;

                case R.styleable.CustomProgressBar_speed:
                    mSpeed = typedArray.getInt(attr,20);//defalt speed 20
                    break;

                 default:
                      break;
            }
        }

        typedArray.recycle();
        mPaint = new Paint();
        //draw Thread
        new Thread(){
            @Override
            public void run() {
                while(true){
                    mProgress++;
                    if (mProgress == 360){
                        mProgress = 0;
                        if (!isNext){
                            isNext = true;
                        }else {
                            isNext = false;
                        }
                    }
                    postInvalidate();
                    try {
                        Thread.sleep(mSpeed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }


            }
        }.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int center = getWidth()/2; //圆环中心的x坐标;
        int radius = center - mCircleWidth /2; //圆环的半径;
        mPaint.setStrokeWidth(mCircleWidth);//设置圆环的宽度;
        mPaint.setAntiAlias(true);//消除锯齿;
        mPaint.setStyle(Paint.Style.STROKE);//设置为空心；
        //用于定义的圆弧的形状和大小的界限
        RectF oval = new RectF(center - radius,center - radius, center + radius,center + radius);
        if (!isNext){
            //第一圈的颜色不变，第二圈的颜色改变
            mPaint.setColor(mFirstColor); //设置圆环的颜色
            canvas.drawCircle(center,center,radius,mPaint); //画出圆环；

            mPaint.setColor(mSecondColor); //设置内圆环的颜色；
            canvas.drawArc(oval,-90,mProgress,false,mPaint); //根据弧度画出圆弧；

        }else {
            //第一圈的颜色不变，第二圈的颜色改变
            mPaint.setColor(mSecondColor); //设置圆环的颜色
            canvas.drawCircle(center,center,radius,mPaint); //画出圆环；

            mPaint.setColor(mFirstColor); //设置内圆环的颜色；
            canvas.drawArc(oval,-90,mProgress,false,mPaint); //根据弧度画出圆弧；

        }

    }


}
